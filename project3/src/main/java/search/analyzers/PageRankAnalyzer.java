package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing the 'page rank' of all available webpages.
 * If a webpage has many different links to it, it should have a higher page rank.
 * See the spec for more details.
 */
public class PageRankAnalyzer {
    private IDictionary<URI, Double> pageRanks;
    /**
     * Computes a graph representing the internet and computes the page rank of all
     * available webpages.
     *
     * @param webpages  A set of all webpages we have parsed.
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    public PageRankAnalyzer(ISet<Webpage> webpages, double decay, double epsilon, int limit) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        // Step 1: Make a graph representing the 'internet'
        IDictionary<URI, ISet<URI>> graph = this.makeGraph(webpages);

        // Step 2: Use this graph to compute the page rank for each webpage
        this.pageRanks = this.makePageRanks(graph, decay, limit, epsilon);

        // Note: we don't store the graph as a field: once we've computed the
        // page ranks, we no longer need it!
    }

    /**
     * This method converts a set of webpages into an unweighted, directed graph,
     * in adjacency list form.
     *
     * You may assume that each webpage can be uniquely identified by its URI.
     *
     * Note that a webpage may contain links to other webpages that are *not*
     * included within set of webpages you were given. You should omit these
     * links from your graph: we want the final graph we build to be
     * entirely "self-contained".
     */
    private IDictionary<URI, ISet<URI>> makeGraph(ISet<Webpage> webpages) {
        IDictionary<URI, ISet<URI>> graph = new ChainedHashDictionary<URI, ISet<URI>>();
        ISet<URI> inGraph = new ChainedHashSet<URI>();
        for (Webpage web : webpages) {
            inGraph.add(web.getUri());
        }
        for (Webpage web : webpages) {
            URI vertex = web.getUri();
            ISet<URI> toVisits = new ChainedHashSet<URI>();
            IList<URI> links = web.getLinks();
            for (URI link : links) {
                if (!link.equals(vertex) && inGraph.contains(link)) {
                    toVisits.add(link);
                }
            }
            graph.put(vertex, toVisits);
        }
        return graph;
    }

    
    /**
     * Computes the page ranks for all webpages in the graph.
     *
     * Precondition: assumes 'this.graphs' has previously been initialized.
     *
     * @param decay     Represents the "decay" factor when computing page rank (see spec).
     * @param epsilon   When the difference in page ranks is less then or equal to this number,
     *                  stop iterating.
     * @param limit     The maximum number of iterations we spend computing page rank. This value
     *                  is meant as a safety valve to prevent us from infinite looping in case our
     *                  page rank never converges.
     */
    private IDictionary<URI, Double> makePageRanks(IDictionary<URI, ISet<URI>> graph,
                                                   double decay,
                                                   int limit,
                                                   double epsilon) {
        // Step 1: The initialize step should go here
        IDictionary<URI, Double> ranks = new ChainedHashDictionary<URI, Double>(); // store old ranks
        Double iniRank = 1 / (double) graph.size();
        Double newSurfer = (1 - decay) / (double) graph.size();
        for (KVPair<URI, ISet<URI>> vertex : graph) {
            ranks.put(vertex.getKey(), iniRank);
        }
        for (int i = 0; i < limit; i++) {
            // Step 2: The update step should go here
            IDictionary<URI, Double> newRanks = new ChainedHashDictionary<URI, Double>();
            Double addition = 0.0;
            for (KVPair<URI, Double> rank : ranks) {
                URI current = rank.getKey();
                newRanks.put(current, newSurfer);
            }
            // IPriorityQueue<URI> toBeVisited = new ArrayHeap<URI>();
            for (KVPair<URI, Double> rank : ranks) {
                URI current = rank.getKey(); // current URI
                Double oldRank = rank.getValue(); // get the old rank of current URI
                ISet<URI> toVisits = graph.get(current); // adjacent nodes of current URI
                int uniNum = toVisits.size(); //  number of out-links are there for current URI
                if (uniNum == 0) { // the case when there is no out-links
                    addition += (decay * oldRank) / (double) graph.size(); // store the number that will be added
                } else { // normal case
                    for (URI visit : toVisits) { // loop through the vertex connected by out-links
                        Double newRank = newRanks.get(visit); // the new rank for the vertex (so far)
                        // calculate the new rank by increment of (d * oldRank) / number of out-links
                        newRank += (oldRank * decay) / uniNum; 
                        newRanks.put(visit, newRank); // update the rank
                    }
                }
            }
            boolean converged = true;
            for (KVPair<URI, Double> rank : newRanks) {
                URI uri = rank.getKey();
                Double newRank = rank.getValue();
                newRank += addition;
                newRanks.put(uri, newRank); // may not necessary
                Double oldRank = ranks.get(uri);
                if (Math.abs(newRank - oldRank) > epsilon) {
                    converged = false;
                }
            }
            // Step 3: the convergence step should go here.
            // Return early if we've converged.
            if (converged) {
                return newRanks;
            }
            ranks = newRanks;
        }
        return ranks;
    }

    /**
     * Returns the page rank of the given URI.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public double computePageRank(URI pageUri) {
        // Implementation note: this method should be very simple: just one line!
        return pageRanks.get(pageUri);
    }
}
