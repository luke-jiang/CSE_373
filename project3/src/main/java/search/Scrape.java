package search;

import search.scraper.Scraper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Scrape {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String[] seeds = new String[] {
                "https://en.wikipedia.org/wiki/Media_in_Seattle",
                "https://en.wikipedia.org/wiki/The_Seattle_Times",
                "https://en.wikipedia.org/wiki/History_of_Seattle_before_1900",
                "https://en.wikipedia.org/wiki/West_Seattle,_Seattle",
                "https://en.wikipedia.org/wiki/Seattle_Daily_Journal_of_Commerce",
        };

        Scraper scraper = new Scraper(Paths.get("data/wikipedia-with-spam"));
        scraper.addDomainToWhitelist("en.wikipedia.org");
        for (String seed : seeds) {
            scraper.addSeedUri(URI.create(seed));
        }

        scraper.run(60 * 5);
    }
}
