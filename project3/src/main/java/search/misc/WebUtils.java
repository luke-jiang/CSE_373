package search.misc;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class WebUtils {
    public static IList<URI> extractLinks(URI rootUri, Document doc) {
        IList<URI> out = new DoubleLinkedList<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String rawLink = link.attr("href");

            try {
                URI extractedLink = new URI(rawLink);
                Optional<URI> normalizedUri = WebUtils
                        .normalize(rootUri.resolve(extractedLink));
                normalizedUri.ifPresent(out::add);
            } catch (URISyntaxException ex) {
                System.out.println("Malformed link: " + rawLink);
            }

        }
        return out;
    }

    public static Optional<URI> normalize(URI uri) {
        try {
            return Optional.of(new URI(
                    uri.getScheme(),
                    uri.getUserInfo(),
                    uri.getHost(),
                    uri.getPort(),
                    uri.getPath(),
                    uri.getQuery(),
                    null).normalize());
        } catch (URISyntaxException ex) {
            return Optional.empty();
        }
    }
}
