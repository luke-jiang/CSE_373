package search;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.BaseTest;
import org.junit.Test;
import search.analyzers.PageRankAnalyzer;
import search.models.Webpage;

import java.net.URI;

public class TestPageRankAnalyzer extends BaseTest {
    // We say two floating point numbers are equal if they're within
    // this delta apart from each other.
    public static final double DELTA = 0.00001;

    private Webpage buildPage(URI currentPage, URI[] linksTo) {
        IList<URI> links = new DoubleLinkedList<>();
        for (URI uri : linksTo) {
            links.add(uri);
        }
        return new Webpage(
                currentPage,
                links,
                new DoubleLinkedList<>(),
                "title",
                "blurb");
    }

    @Test(timeout=SECOND)
    public void testSpecExample1() {
        URI pageAUri = URI.create("http://example.com/page-a.html");
        URI pageBUri = URI.create("http://example.com/page-b.html");
        URI pageCUri = URI.create("http://example.com/page-c.html");
        URI pageDUri = URI.create("http://example.com/page-d.html");

        ISet<Webpage> pages = new ChainedHashSet<>();
        pages.add(this.buildPage(pageAUri, new URI[] {pageBUri, pageCUri, pageDUri}));
        pages.add(this.buildPage(pageBUri, new URI[] {pageAUri}));
        pages.add(this.buildPage(pageCUri, new URI[] {pageAUri}));
        pages.add(this.buildPage(pageDUri, new URI[] {pageAUri}));

        PageRankAnalyzer analyzer = new PageRankAnalyzer(pages, 0.85, 0.00001, 100);

        assertEquals(0.47973, analyzer.computePageRank(pageAUri), DELTA);
        assertEquals(0.17342, analyzer.computePageRank(pageBUri), DELTA);
        assertEquals(0.17342, analyzer.computePageRank(pageCUri), DELTA);
        assertEquals(0.17342, analyzer.computePageRank(pageDUri), DELTA);
    }

    @Test(timeout=SECOND)
    public void testSpecExample2() {
        URI pageAUri = URI.create("http://example.com/page-a.html");
        URI pageBUri = URI.create("http://example.com/page-b.html");
        URI pageCUri = URI.create("http://example.com/page-c.html");

        ISet<Webpage> pages = new ChainedHashSet<>();
        pages.add(this.buildPage(pageAUri, new URI[] {pageBUri}));
        pages.add(this.buildPage(pageBUri, new URI[] {pageCUri}));
        pages.add(this.buildPage(pageCUri, new URI[] {pageAUri}));

        PageRankAnalyzer analyzer = new PageRankAnalyzer(pages, 0.85, 0.00001, 100);

        assertEquals(0.33333, analyzer.computePageRank(pageAUri), DELTA);
        assertEquals(0.33333, analyzer.computePageRank(pageBUri), DELTA);
        assertEquals(0.33333, analyzer.computePageRank(pageCUri), DELTA);
    }

    @Test(timeout=SECOND)
    public void testSpecExample3() {
        URI pageAUri = URI.create("http://example.com/page-a.html");
        URI pageBUri = URI.create("http://example.com/page-b.html");
        URI pageCUri = URI.create("http://example.com/page-c.html");
        URI pageDUri = URI.create("http://example.com/page-d.html");
        URI pageEUri = URI.create("http://example.com/page-e.html");

        ISet<Webpage> pages = new ChainedHashSet<>();
        pages.add(this.buildPage(pageAUri, new URI[] {pageBUri, pageDUri}));
        pages.add(this.buildPage(pageBUri, new URI[] {pageCUri, pageDUri}));
        pages.add(this.buildPage(pageCUri, new URI[] {}));
        pages.add(this.buildPage(pageDUri, new URI[] {pageAUri}));
        pages.add(this.buildPage(pageEUri, new URI[] {pageDUri}));

        PageRankAnalyzer analyzer = new PageRankAnalyzer(pages, 0.85, 0.00001, 100);

        assertEquals(0.31706, analyzer.computePageRank(pageAUri), DELTA);
        assertEquals(0.18719, analyzer.computePageRank(pageBUri), DELTA);
        assertEquals(0.13199, analyzer.computePageRank(pageCUri), DELTA);
        assertEquals(0.31132, analyzer.computePageRank(pageDUri), DELTA);
        assertEquals(0.05244, analyzer.computePageRank(pageEUri), DELTA);
    }

}
