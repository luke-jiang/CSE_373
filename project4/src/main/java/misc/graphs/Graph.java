package misc.graphs;

import datastructures.concrete.ArrayDisjointSet;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import datastructures.interfaces.ISet;
import misc.exceptions.NoPathExistsException;

/**
 * Represents an undirected, weighted graph, possibly containing self-loops, parallel edges,
 * and unconnected components.
 *
 * Note: This class is not meant to be a full-featured way of representing a graph.
 * We stick with supporting just a few, core set of operations needed for the
 * remainder of the project.
 */
public class Graph<V, E extends Edge<V> & Comparable<E>> {
    // NOTE 1:
    //
    // Feel free to add as many fields, private helper methods, and private
    // inner classes as you want.
    //
    // And of course, as always, you may also use any of the data structures
    // and algorithms we've implemented so far.
    //
    // Note: If you plan on adding a new class, please be sure to make it a private
    // static inner class contained within this file. Our testing infrastructure
    // works by copying specific files from your project to ours, and if you
    // add new files, they won't be copied and your code will not compile.
    //
    //
    // NOTE 2:
    //
    // You may notice that the generic types of Graph are a little bit more
    // complicated then usual.
    //
    // This class uses two generic parameters: V and E.
    //
    // - 'V' is the type of the vertices in the graph. The vertices can be
    //   any type the client wants -- there are no restrictions.
    //
    // - 'E' is the type of the edges in the graph. We've contrained Graph
    //   so that E *must* always be an instance of Edge<V> AND Comparable<E>.
    //
    //   What this means is that if you have an object of type E, you can use
    //   any of the methods from both the Edge interface and from the Comparable
    //   interface
    //
    // If you have any additional questions about generics, or run into issues while
    // working with them, please ask ASAP either on Piazza or during office hours.
    //
    // Working with generics is really not the focus of this class, so if you
    // get stuck, let us know we'll try and help you get unstuck as best as we can.
    private IDictionary<V, ISet<E>> theGraph;
    private int edgeCount;
    private int vertexCount;
    private IList<E> theEdges;
    private IList<V> theVertices;

    /**
     * Constructs a new graph based on the given vertices and edges.
     *
     * @throws IllegalArgumentException  if any of the edges have a negative weight
     * @throws IllegalArgumentException  if one of the edges connects to a vertex not
     *                                   present in the 'vertices' list
     */
    public Graph(IList<V> vertices, IList<E> edges) {
        theGraph = new ChainedHashDictionary<>();
        for (V vertex : vertices) {
            theGraph.put(vertex, new ChainedHashSet<>());
        }
        for (E edge : edges) {
            V v1 = edge.getVertex1();
            V v2 = edge.getVertex2();
            double w = edge.getWeight();
            if (w < 0 || !theGraph.containsKey(v1) || !theGraph.containsKey(v2)) {
                throw new IllegalArgumentException();
            }
            ISet<E> set1 = theGraph.get(v1);
            ISet<E> set2 = theGraph.get(v2);
            set1.add(edge);
            set2.add(edge);
            theGraph.put(v1, set1);
            theGraph.put(v2, set2);
        }
        edgeCount = edges.size();
        vertexCount = vertices.size();
        theEdges = edges;
        theVertices = vertices;
    }

    /**
     * Sometimes, we store vertices and edges as sets instead of lists, so we
     * provide this extra constructor to make converting between the two more
     * convenient.
     */
    public Graph(ISet<V> vertices, ISet<E> edges) {
        // You do not need to modify this method.
        this(setToList(vertices), setToList(edges));
    }

    // You shouldn't need to call this helper method -- it only needs to be used
    // in the constructor above.
    private static <T> IList<T> setToList(ISet<T> set) {
        IList<T> output = new DoubleLinkedList<>();
        for (T item : set) {
            output.add(item);
        }
        return output;
    }

    /**
     * Returns the number of vertices contained within this graph.
     */
    public int numVertices() {
        return vertexCount;
    }

    /**
     * Returns the number of edges contained within this graph.
     */
    public int numEdges() {
        return edgeCount;
    }

    /**
     * Returns the set of all edges that make up the minimum spanning tree of
     * this graph.
     *
     * If there exists multiple valid MSTs, return any one of them.
     *
     * Precondition: the graph does not contain any unconnected components.
     */
    public ISet<E> findMinimumSpanningTree() {
        ISet<E> result = new ChainedHashSet<>();
        IPriorityQueue<E> edgeHeap = new ArrayHeap<>();
        for (E edge : theEdges) {
            edgeHeap.insert(edge);
        }
        IDisjointSet<V> vertices = new ArrayDisjointSet<>();
        for (V vertex : theVertices) {
            vertices.makeSet(vertex);
        }
        int edgesAccepted = 0;
        while (edgesAccepted < theVertices.size() - 1) {
            E minEdge = edgeHeap.removeMin();
            V v1 = minEdge.getVertex1();
            V v2 = minEdge.getVertex2();
            int uSet = vertices.findSet(v1);
            int vSet = vertices.findSet(v2);
            if (uSet != vSet) {
                edgesAccepted++;
                vertices.union(v1, v2);
                result.add(minEdge);
            }
        }
        return result;
    }

    /**
     * Returns the edges that make up the shortest path from the start
     * to the end.
     *
     * The first edge in the output list should be the edge leading out
     * of the starting node; the last edge in the output list should be
     * the edge connecting to the end node.
     *
     * Return an empty list if the start and end vertices are the same.
     *
     * @throws NoPathExistsException  if there does not exist a path from the start to the end
     */
    public IList<E> findShortestPathBetween(V start, V end) {
        IList<E> path = new DoubleLinkedList<E>();
        if (start.equals(end)) {
            return path;
        }
        IPriorityQueue<Node> nodeHeap = new ArrayHeap<Node>();
        IDictionary<V, Node> known = new ChainedHashDictionary<>();
        IDictionary<V, Node> allNodes = new ChainedHashDictionary<>();
        
        for (V vertex : theVertices) {
            Node node = new Node(vertex);
            if (vertex.equals(start)) {
                node.cost = 0.0;
            }
            nodeHeap.insert(node);
            allNodes.put(vertex, node);
        }
        while (known.size() < theVertices.size()) {
            Node min = nodeHeap.removeMin();
            if (known.containsKey(min.vertex)) {
                continue;
            }
            known.put(min.vertex, min);
            if (min.vertex.equals(end)) {
                break;
            }
            for (E neighbor : theGraph.get(min.vertex)) {
                V next = neighbor.getOtherVertex(min.vertex);
                if (!known.containsKey(next)) {
                    double newCost = min.cost + neighbor.getWeight();
                    if (newCost < allNodes.get(next).cost) {
                        Node newPath = new Node(next, newCost, neighbor);
                        nodeHeap.insert(newPath);
                        allNodes.put(next, newPath);
                    }
                }
            }
        }
        if (!known.containsKey(end) || known.get(end).cost >= Double.POSITIVE_INFINITY) {
            throw new NoPathExistsException();
        }
        
        Node curr = known.get(end);
        while (curr.prev != null) {
            E edge = curr.prev;
            path.insert(0, edge);
            curr = known.get(edge.getOtherVertex(curr.vertex));
        }
        return path;
    }
    
    private class Node implements Comparable<Node> {
        public V vertex;
        public double cost;
        public E prev;
        
        public Node(V v, double c, E p) {
            vertex = v;
            cost = c;
            prev = p;
        }
        
        public Node(V v) {
            this(v, Double.POSITIVE_INFINITY, null);
        }

        @Override
        public int compareTo(Graph<V, E>.Node o) {
            if (this.cost < o.cost) {
                return -1;
            } else if (this.cost > o.cost) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
