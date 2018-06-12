package search;

import datastructures.interfaces.IList;
import search.misc.WordTokenizer;
import search.models.Result;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains all code to manage and serve our website.
 */
public class Webapp {
    /**
     * The path to all static, non-changing files we will serve.
     * (See 'src/main/resources')
     */
    private static final String STATIC_FILES = "webapp/static";

    /**
     * The path to all template HTML files. (See 'hw2/main/resources')
     */
    private static final String TEMPLATE_FILES = "webapp/templates";

    private final String siteName;
    private final SearchEngine engine;
    private final Service http;
    private final int port;

    /**
     * Creates a new instance of this class.
     *
     * @param engine    The SearchEngine we will be using to answer user queries
     * @param siteName  The name of our website
     * @param port      The port to serve our website on.
     */
    public Webapp(SearchEngine engine, String siteName, int port) {
        this.engine = engine;
        this.siteName = siteName;
        this.port = port;

        this.http = Service.ignite()
                .staticFileLocation(STATIC_FILES)
                .port(port);
        this.http.initExceptionHandler(this::handleFatalError);
        this.http.get("/", this::handleMain);
        this.http.get("/search", this::handleSearch);
        this.http.exception(Exception.class, this::handleError);
    }

    /**
     * Starts running this website.
     */
    public void launch() {
        this.http.init();
    }

    /**
     * Handles all incoming requests for our home page.
     */
    private String handleMain(Request req, Response res) {
        Map<String, Object> model = new HashMap<>();
        model.put("siteTitle", this.siteName);

        return this.render("main.mustache", model);
    }

    /**
     * Handles all incoming user queries.
     */
    private String handleSearch(Request req, Response res) {
        // Get search query
        String query = req.queryParams("query");
        int numResults = Integer.parseInt(req.queryParamOrDefault("num_results", "20"));

        // Perform core search
        IList<String> queryTerms = WordTokenizer.extract(query);
        IList<Result> results = this.engine.getTopKResults(queryTerms, numResults);

        // Render results
        Map<String, Object> model = new HashMap<>();
        model.put("siteTitle", this.siteName);
        model.put("results", results);
        model.put("initialQuery", query);

        return this.render("search.mustache", model);
    }

    /**
     * Handles fatal errors that causes the webapp to crash before
     * it even has a chance to run.
     */
    private void handleFatalError(Exception ex) {
        System.err.println("ERROR: Encountered fatal exception");
        System.err.println();
        System.err.println("If you are seeing this error message, it means that");
        System.err.println("the webapp was unable to launch.");
        System.err.println();
        System.err.println("The most common reason you might see this is if you are");
        System.err.println("attempting to run the webserver twice (or more specifically,");
        System.err.println("if two services are attempting to use the same port).");
        System.err.println();
        System.err.println("You should:");
        System.err.println();
        System.err.println("a. Make sure you've shut down any other running instances.");
        System.err.println("   If you're not sure how to do this, talk to the course staff.");
        System.err.println("   As a last resort, try restarting Eclipse.");
        System.err.println();
        System.err.println("b. If you're running some other service on port " + this.port + ",");
        System.err.println("   change the PORT constant in Main.java.");
        System.err.println();
        System.err.println("If you are not able to resolve this issue, contact the course");
        System.err.println("staff ASAP.");
        System.err.println();
        System.err.println("The full stack trace:");
        System.err.println();
        ex.printStackTrace();
    }

    /**
     * Handles exceptions raised while the webapp is running.
     */
    private void handleError(Exception ex, Request req, Response res) {
        // Get exception as string
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();

        // Print out console output
        ex.printStackTrace();

        // Render results
        Map<String, Object> model = new HashMap<>();
        model.put("siteTitle", this.siteName);
        model.put("exception", stackTrace);

        res.status(500);
        res.body(this.render("error.mustache", model));
    }

    private String render(String templateName, Map<String, Object> params) {
        return new MustacheTemplateEngine(TEMPLATE_FILES).render(
                new ModelAndView(params, templateName));
    }
}
