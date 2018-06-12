package search.models;

import java.net.URI;

/**
 * Represents a summarized version of a webpage containing the bare minimum
 * information we might want to show a user.
 *
 * Implementation note: the code we've given you converts the 'Webpage' object
 * to a 'WebpageSummary' object as soon as possible because the 'Webpage' object
 * is expensive to keep in memory: the list of words (and sometimes the list of
 * links) can both be very long.
 *
 * The code we've given you is structured so we have access to those two pieces of
 * information for as little time as possible so we can garbage-collect them at
 * the first opportunity.
 */
public class WebpageSummary {
    private URI uri;
    private String title;
    private String blurb;

    /**
     * Constructs a new instance of this class.
     */
    public WebpageSummary(URI uri, String title, String blurb) {
        this.uri = uri;
        this.title = title;
        this.blurb = blurb;
    }

    /**
     * Returns this webpage's URI.
     */
    public URI getUri() {
        return this.uri;
    }

    /**
     * Returns the title of this webpage.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns a short blurb or description of the webpage.
     */
    public String getBlurb() {
        return this.blurb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        WebpageSummary that = (WebpageSummary) o;

        if (!uri.equals(that.uri)) { return false; }
        if (!title.equals(that.title)) { return false; }
        return blurb.equals(that.blurb);
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + blurb.hashCode();
        return result;
    }
}
