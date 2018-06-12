package search.scraper;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpRequester implements AutoCloseable {
    public static final String DEFAULT_USER_AGENT = "Cse373Crawler (contact mlee42@cs.washington.edu)";
    public static final long DEFAULT_DELAY = 1000; // 1 second

    private final String userAgent;
    private final long delayMilliseconds;

    // Internal state
    private long lastRequestTimestamp;
    private CloseableHttpClient client;

    private UriMatchRule defaultMatchRule;
    private IDictionary<String, UriMatchRule> matchPattern;
    private ISet<String> allowableContentTypes;

    public HttpRequester() {
        this(DEFAULT_USER_AGENT, DEFAULT_DELAY);
    }

    public HttpRequester(String userAgent, long delayMilliseconds) {
        if (delayMilliseconds < 500) {
            throw new IllegalArgumentException(
                    "Setting your delay to too low a number can cause your bot to be blacklisted"
                    + " and is considered malicious activity by many website administrators."
                    + " If you want to increase the limit, please talk to the course instructor first.");
        }

        this.userAgent = userAgent;
        this.delayMilliseconds = delayMilliseconds;
        this.lastRequestTimestamp = 0L;
        this.matchPattern = new ChainedHashDictionary<>();

        // HTTP config
        this.client = HttpClients.custom()
                .setUserAgent(userAgent)
                .setDefaultRequestConfig(RequestConfig
                        .copy(RequestConfig.DEFAULT)
                        .setConnectionRequestTimeout(5000)
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .build())
                .build();
        this.allowableContentTypes = Constants.contentTypeWhitelist();
        this.defaultMatchRule = Constants.fileExtensionBlacklist();
    }

    public String makeRequest(URI uri) throws IOException, URISyntaxException {
        if (this.isBlacklistedUri(uri)) {
            System.out.println("Is blacklisted uri: " + uri);
            return null;
        } else {
            System.out.println("Is ok uri: " + uri);
        }
        this.respectDelay();
        return this.rawHttpRequest(uri);
    }

    private void respectDelay() {
        long current = System.currentTimeMillis();

        long timeElapsed = current - this.lastRequestTimestamp;
        long timeLeft = this.delayMilliseconds - timeElapsed;

        if (timeLeft > 0) {
            try {
                Thread.sleep(timeLeft);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        this.lastRequestTimestamp = System.currentTimeMillis();
    }

    private String rawHttpRequest(URI uri) throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpResponse response = this.client.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new IOException("Response had no entity");
            }

            ContentType contentType = ContentType.get(entity);
            if (!this.allowableContentTypes.contains(contentType.getMimeType())) {
                System.out.println("Unknown content type for " + uri + ": " + contentType);
                System.out.println(this.allowableContentTypes);
                return null;
            }

            return EntityUtils.toString(entity, "UTF-8");
        }
    }

    private boolean isBlacklistedUri(URI uri) {
        String host = uri.getHost();
        if (!this.defaultMatchRule.matches(uri)) {
            return false;
        }
        if (!this.matchPattern.containsKey(host)) {
            String robotsContents;
            try {
                robotsContents = this.rawHttpRequest(uri.resolve("/robots.txt"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            UriMatchRule newRule;
            if (robotsContents == null) {
                newRule = new UriMatchRule(null, null);
            } else {
                newRule = new RobotsTxt(this.userAgent, robotsContents).getMatchRule();
            }
            this.matchPattern.put(host, newRule);
        }
        return !this.matchPattern.get(host).matches(uri);
    }


    public void close() throws IOException {
        if (this.client != null) {
            this.client.close();
            this.client = null;
        }
    }
}
