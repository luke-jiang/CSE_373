package search.models;

import java.net.URI;

/**
 * Represents a search result, containing both information about
 * the webpage and the final relevance score.
 */
public class Result implements Comparable<Result> {
    private WebpageSummary webpageSummary;
    private double score;

    public Result(WebpageSummary summary, double score) {
        this.webpageSummary = summary;
        this.score = score;
    }

    /**
     * Returns the link to the webpage.
     */
    public URI getUri() {
        return this.webpageSummary.getUri();
    }

    /**
     * Returns the relevance score for this webpage.
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns this webpage's title
     */
    public String getTitle() {
        return this.webpageSummary.getTitle();
    }

    /**
     * Returns a short description or summary of this webpage.
     */
    public String getBlurb() {
        return this.webpageSummary.getBlurb();
    }

    /**
     * Compares to Result objects by their score.
     *
     * If the this result has a lower score then the other one, returns a negative number.
     * If the this result has the same score as this one, returns 0.
     * If the this result has a higher score then the other one, returns a positive number.
     */
    @Override
    public int compareTo(Result other) {
        return Double.compare(this.score, other.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Result result = (Result) o;

        if (Double.compare(result.score, score) != 0) { return false; }
        return webpageSummary.equals(result.webpageSummary);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = webpageSummary.hashCode();
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
