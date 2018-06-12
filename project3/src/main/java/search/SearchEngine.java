package search;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.Searcher;
import search.analyzers.PageRankAnalyzer;
import search.analyzers.TfIdfAnalyzer;
import search.misc.Bridge;
import search.misc.exceptions.DataExtractionException;
import search.models.Result;
import search.models.Webpage;
import search.models.WebpageSummary;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchEngine {
    public static final double PAGE_RANK_DECAY = 0.85;
    public static final double PAGE_RANK_EPSILON = 0.0001;
    public static final int PAGE_RANK_ITERATION_LIMIT = 200;

    private ISet<WebpageSummary> pages;
    private TfIdfAnalyzer tfIdfAnalyzer;
    private PageRankAnalyzer pageRankAnalyzer;

    public SearchEngine(String dataFolderName) {
        long start = System.currentTimeMillis();
        ISet<Webpage> webpages = this.collectWebpages(Paths.get("data", dataFolderName));
        long end = System.currentTimeMillis() - start;
        System.out.println("Done loading pages (" + (end / 1000.0) + " sec)");

        this.pages = this.extractWebpageSummaries(webpages);
        System.out.println("Done extracting");

        start = System.currentTimeMillis();
        this.tfIdfAnalyzer = new TfIdfAnalyzer(webpages);
        this.pageRankAnalyzer = new PageRankAnalyzer(
                webpages,
                PAGE_RANK_DECAY,
                PAGE_RANK_EPSILON,
                PAGE_RANK_ITERATION_LIMIT);
        end = System.currentTimeMillis() - start;
        System.out.println("Done indexing (" + (end / 1000.0) + " sec)");
    }

    public double computeScore(IList<String> query, URI uri) {
        double tfIdf = this.tfIdfAnalyzer.computeRelevance(query, uri);
        double pageRank = this.pageRankAnalyzer.computePageRank(uri);

        if (pageRank <= 0.0) {
            throw new IllegalStateException(String.format(
                    "Page '%s' had a page rank of '%f'; all page ranks should be positive and non-zero.",
                    uri, pageRank));
        }

        // We are combining these two scores in a fairly arbitrary way.
        // The correct thing to do is to apply machine learning and develop
        // a classifier that combines these two scores.
        //
        // Figuring out the best way to do this is something of a black art
        // and is a part of the "secret sauce" of commercial web engines.
        //
        // However, in the interests of simplicity, we opted not to do that
        // and just experimented with formulas until we found that seemed to
        // work well.
        //
        // The intuition here is that the pageRank for any given page tends
        // to be skewed -- popular pages tend to have abnormally high
        // ranks, other pages have very small ones. So, we take the square
        // root to "normalize" these extremes, then multiply it against
        // the tfIdf score to scale it accordingly.
        //
        // That's the underlying justification for why we chose this formula,
        // but it's still a pretty ad-hoc approach. Feel free to adjust or
        // change this formula: we will be grading your TfIdfAnalyzer and
        // PageRankAnalyzer classes separately, but not this method.
        return tfIdf * Math.sqrt(pageRank);
    }

    public IList<Result> getTopKResults(IList<String> query, int k) {
        IList<Result> results = new DoubleLinkedList<>();

        for (WebpageSummary summary: this.pages) {
            double score = this.computeScore(query, summary.getUri());
            results.add(new Result(summary, score));
        }

        IList<Result> topK = Searcher.topKSort(k, results);

        IList<Result> reversed = new DoubleLinkedList<>();
        for (Result res : topK) {
            reversed.insert(0, res);
        }

        return reversed;
    }

    private ISet<Webpage> collectWebpages(Path root) {
        try {
            return Files.walk(root)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".htm") || path.toString().endsWith(".html"))
                    .map(Path::toUri)
                    .map(Webpage::load)
                    .collect(Bridge.toISet());
        } catch (IOException ex) {
            throw new DataExtractionException("Could not find given root folder", ex);
        }
    }

    private ISet<WebpageSummary> extractWebpageSummaries(ISet<Webpage> webpages) {
        ISet<WebpageSummary> output = new ChainedHashSet<>();
        for (Webpage page : webpages) {
            output.add(page.getSummary());
        }
        return output;
    }
}
