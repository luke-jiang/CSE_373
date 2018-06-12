package search.scraper;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.ChainedHashSet;
import datastructures.interfaces.IPriorityQueue;
import datastructures.interfaces.ISet;
import search.misc.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

public class Scraper {
    private Path outputPath;
    private IPriorityQueue<PendingUri> pendingUris;
    private ISet<String> domainWhitelist;
    private ISet<URI> alreadyEncountered;
    private HttpRequester requester;

    public Scraper(Path outputPath) {
        this.outputPath = outputPath;
        this.pendingUris = new ArrayHeap<>();
        this.domainWhitelist = new ChainedHashSet<>();
        this.requester = new HttpRequester();
        this.alreadyEncountered = new ChainedHashSet<>();
    }

    public Scraper addSeedUri(URI uri) {
        Optional<URI> normalizedUri = WebUtils.normalize(uri);
        if (!normalizedUri.isPresent()) {
            throw new IllegalArgumentException("URI is malformed: " + uri);
        }
        this.pendingUris.insert(new PendingUri(0, normalizedUri.get()));
        return this;
    }

    public Scraper addDomainToWhitelist(String host) {
        this.domainWhitelist.add(host);
        return this;
    }

    public void run(int limit) throws IOException, URISyntaxException {
        File outputFile = this.outputPath.toFile();
        if (!outputFile.exists() && !outputFile.mkdirs()) {
            throw new IOException("Unable to make folder " + this.outputPath);
        }

        File saveFile = this.outputPath.resolve("list.sav").toFile();
        this.loadAlreadyEncountered(saveFile);
        try (PrintWriter saveWriter = this.makePrintWriter(saveFile, true)) {
            int limitCount = 0;
            while (limitCount < limit && !this.pendingUris.isEmpty()) {
                // Handle page
                PendingUri pending = this.pendingUris.removeMin();
                URI uri = pending.uri;
                this.alreadyEncountered.add(uri);

                // Skip invalid pages
                if (!this.domainWhitelist.contains(uri.getHost())) {
                    System.out.println(String.format("Skipping '%s'; host not in whitelist", uri));
                    continue;
                }

                String body = this.requester.makeRequest(pending.uri);
                if (body == null) {
                    System.out.println(String.format("Unable to fetch content from '%s'", uri));
                    continue;
                }

                // Save loaded page
                limitCount += 1;
                this.saveHtml(uri, body);
                saveWriter.print(uri.toString() + "\n");

                // Add children (if applicable)
                Document document = Jsoup.parse(body);
                for (URI link : WebUtils.extractLinks(uri, document)) {
                    Optional<URI> normalizedUri = WebUtils.normalize(link);

                    if (normalizedUri.isPresent() && !this.alreadyEncountered.contains(link)) {
                        this.pendingUris.insert(new PendingUri(pending.distance + 1, normalizedUri.get()));
                    }
                }

                // Log
                System.out.println(String.format("Fetched %s", uri));
            }
        }
    }

    private void saveHtml(URI uri, String body) throws IOException {
        String hex = DigestUtils.md5Hex(uri.toString());
        Path saveDomainPath = this.outputPath.resolve(uri.getHost());
        Path savePath = saveDomainPath.resolve(hex + ".html");

        if (!saveDomainPath.toFile().exists() && !saveDomainPath.toFile().mkdirs()) {
            throw new IOException("Unable to make folder " + saveDomainPath);
        }

        try (PrintWriter writer = this.makePrintWriter(savePath.toFile(), false)) {
            writer.print("<!-- METADATA\n");
            writer.printf("uri: %s\n", uri.toString());
            writer.print("local: false\n");
            writer.print("-->\n");
            writer.print(body);
        }
    }

    private PrintWriter makePrintWriter(File file, boolean append) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
    }

    private void loadAlreadyEncountered(File saveFile) throws IOException {
        if (saveFile.exists() && !saveFile.isFile()) {
            throw new IOException("list.sav is not a file?");
        }

        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
                String line = reader.readLine();
                while (line != null) {
                    this.alreadyEncountered.add(URI.create(line.trim()));
                    line = reader.readLine();
                }
            }
        }
    }

    private static class PendingUri implements Comparable<PendingUri> {
        public final int distance;
        public final URI uri;

        public PendingUri(int distance, URI uri) {
            if (uri == null) {
                throw new IllegalArgumentException();
            }
            this.distance = distance;
            this.uri = uri;
        }

        public int compareTo(PendingUri other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
