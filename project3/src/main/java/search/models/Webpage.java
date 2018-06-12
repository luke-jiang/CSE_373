package search.models;

import com.chimbori.crux.articles.Article;
import com.chimbori.crux.articles.ArticleExtractor;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import search.misc.UnbufferedReader;
import search.misc.WebUtils;
import search.misc.WordTokenizer;
import search.misc.exceptions.DataExtractionException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

/**
 * This class represents a single webpage.
 *
 * You should not modify this class: just use its public methods.
 */
public class Webpage {
    private static final int MAX_DESCRIPTION_LEN = 200;

    // This field is the 'unique key' for the webpage:
    // every Webpage object is guaranteed to have a unique URI.
    private URI pageUri;

    // The remaining fields can be derived by inspecting the above two fields
    // and exist mainly so we can cache data we'd rather not recompute.
    private IList<URI> links;
    private IList<String> words;
    private WebpageSummary summary;

    /**
     * Constructs a new webpage instance.
     */
    public Webpage(URI pageUri, IList<URI> links, IList<String> words, String title, String blurb) {
        this.pageUri = pageUri;
        this.links = links;
        this.words = words;
        this.summary = new WebpageSummary(pageUri, title, blurb);
    }

    /**
     * Returns this webpage's URI -- its web address.
     *
     * Each webpage is guaranteed to have a unique URI.
     */
    public URI getUri() {
        return this.pageUri;
    }

    /**
     * Returns a list of all links contained within this web page.
     */
    public IList<URI> getLinks() {
        return this.links;
    }

    /**
     * Returns a list of all words contained within this web page.
     */
    public IList<String> getWords() {
        return this.words;
    }

    /**
     * Returns a summary of this webpage.
     */
    public WebpageSummary getSummary() {
        return this.summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Webpage webpage = (Webpage) o;

        return pageUri.equals(webpage.pageUri);
    }

    @Override
    public int hashCode() {
        return pageUri.hashCode();
    }

    // Note: You can safely ignore all the methods below this point.

    public static Webpage load(URI localUri) {
        URI cacheURI = Webpage.getCacheURI(localUri);
        File cacheFile = new File(cacheURI);
        if (cacheFile.exists()) {
            return Webpage.loadFromCache(cacheFile);
        } else {
            Webpage out = Webpage.loadOriginal(localUri);
            Webpage.saveToCache(out, cacheFile);
            return out;
        }
    }

    public static Webpage loadOriginal(URI localUri) {
        // Extract some core data
        try (PushbackInputStream stream = Webpage.openLocalStream(localUri)) {
            IDictionary<String, String> metadata = Webpage.extractMetadata(stream);
            Document document = Webpage.extractHtml(stream, metadata);

            // Save canonical fields
            URI pageUri = Webpage.createUri(metadata.get("uri"));

            // Populate cache fields
            IList<URI> links = WebUtils.extractLinks(pageUri, document);
            IList<String> words = WordTokenizer.extract(document.body().text());

            Article article = Webpage.extractReadableArticle(pageUri, document);
            String title = Webpage.extractTitle(article, pageUri);
            String blurb = Webpage.extractBlurb(article);

            return new Webpage(pageUri, links, words, title, blurb);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void saveToCache(Webpage page, File cache) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cache))) {
            WebpageSummary summary = page.getSummary();

            // Line 1: page URI
            writer.write(page.pageUri.toString() + "\n");

            // Line 2: title
            writer.write(summary.getTitle() + "\n");

            // Line 3: blurb
            writer.write(summary.getBlurb() + "\n");

            // Line 4: words
            for (String word : page.words) {
                writer.write(word);
                writer.write(' ');
            }
            writer.write('\n');

            // Remaining lines: links
            for (URI link : page.links) {
                writer.write(link.toString());
                writer.write('\n');
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not create cache", ex);
        }
    }

    private static Webpage loadFromCache(File cache) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cache))) {
            // Line 1: Page URI
            URI pageUri = URI.create(reader.readLine().trim());

            // Line 2: title
            String title = reader.readLine().trim();

            // Line 3: blurb
            String blurb = reader.readLine().trim();

            // Line 4: words
            IList<String> words = new DoubleLinkedList<>();
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine().trim(), " ");
            while (tokenizer.hasMoreTokens()) {
                words.add(tokenizer.nextToken());
            }

            // Rest of lines: links
            IList<URI> links = new DoubleLinkedList<>();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                links.add(URI.create(line));
            }

            return new Webpage(pageUri, links, words, title, blurb);
        } catch (IOException ex) {
            throw new RuntimeException("Could not load cache", ex);
        }
    }

    private static URI getCacheURI(URI localUri) {
        String raw = localUri.toString();
        String piece = raw.substring(0, raw.lastIndexOf("."));
        return URI.create(piece + ".cache");
    }

    private static PushbackInputStream openLocalStream(URI localUri) {
        try {
            return new PushbackInputStream(localUri.toURL().openStream());
        } catch (IOException ex) {
            String msg = String.format("Could not open local file file '%s'", localUri);
            throw new DataExtractionException(msg, ex);
        }
    }

    private static IDictionary<String, String> extractMetadata(PushbackInputStream stream) {
        try {
            UnbufferedReader reader = new UnbufferedReader(stream);
            String line = reader.readLine();
            if (!line.equals("<!-- METADATA")) {
                throw new DataExtractionException("Local webpage does not start with metadata header");
            }

            IDictionary<String, String> output = new ChainedHashDictionary<>();
            while (!line.equals("-->")) {
                line = reader.readLine();

                if (!line.equals("-->")) {
                    String[] parts = line.split(": ", 2);
                    output.put(parts[0], parts[1]);
                }
            }
            return output;
        } catch (IOException ex) {
            throw new DataExtractionException("Error when trying to extract metadata", ex);
        }
    }

    private static Document extractHtml(InputStream stream, IDictionary<String, String> metadata) {
        try {
            return Jsoup.parse(stream, null, metadata.get("uri"));
        } catch (IOException ex) {
            throw new DataExtractionException("Could not extract HTML from document");
        }
    }

    private static Article extractReadableArticle(URI pageUri, Document document) {
        return ArticleExtractor
                .with(pageUri.toString(), document.html())
                .extractMetadata()
                .extractContent()
                .article();
    }

    private static URI createUri(String str) {
        try {
            return new URI(str);
        } catch (URISyntaxException ex){
            throw new DataExtractionException("URL is malformed", ex);
        }
    }

    private static String extractTitle(Article article, URI pageUri) {
        String out = article.title;
        if (out.isEmpty()) {
            // Fallback
            out = pageUri.toString();
        }
        return out;
    }

    private static String extractBlurb(Article article) {
        String description = article.description;
        if (description == null || description.isEmpty()) {
            String text = article.document.text();
            StringBuilder out = new StringBuilder();
            int count = 0;
            for (String word : text.split("\\s+")) {
                out.append(word);
                count += word.length() + 1;
                if (count > MAX_DESCRIPTION_LEN) {
                    out.append("...");
                    break;
                } else {
                    out.append(" ");
                }
            }
            description = out.toString();
        }
        return description;
    }
}
