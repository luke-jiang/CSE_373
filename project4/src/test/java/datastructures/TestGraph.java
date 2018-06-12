package datastructures;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.BaseTest;
import misc.exceptions.NoPathExistsException;
import misc.graphs.Edge;
import misc.graphs.Graph;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestGraph extends BaseTest {
    public static class SimpleEdge<V> implements Edge<V>, Comparable<SimpleEdge<V>> {
        private V vertex1;
        private V vertex2;
        private double weight;

        public SimpleEdge(V vertex1, V vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public V getVertex1() {
            return this.vertex1;
        }

        @Override
        public V getVertex2() {
            return this.vertex2;
        }

        @Override
        public double getWeight() {
            return this.weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            SimpleEdge<?> that = (SimpleEdge<?>) o;

            if (Double.compare(that.weight, weight) != 0) { return false; }
            if (!vertex1.equals(that.vertex1)) { return false; }
            return vertex2.equals(that.vertex2);
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = vertex1.hashCode();
            result = 31 * result + vertex2.hashCode();
            temp = Double.doubleToLongBits(weight);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public int compareTo(SimpleEdge<V> other) {
            return Double.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("Edge(%s, %s, %s)", this.vertex1, this.vertex2, this.weight);
        }
    }

    /**
     * A convenience method for constructing a new SimpleEdge, since having to
     * type 'new SimpleEdge<>(...)' everywhere would be clunky and annoying.
     */
    public <V> SimpleEdge<V> edge(V v1, V v2, double weight) {
        return new SimpleEdge<>(v1, v2, weight);
    }

    public <V> void checkPathMatches(Graph<V, SimpleEdge<V>> graph, double expectedFinalCost, V[] expectedPath) {
        IList<SimpleEdge<V>> path = graph.findShortestPathBetween(
                expectedPath[0],
                expectedPath[expectedPath.length - 1]);

        double cost = 0.0;
        assertEquals(expectedPath.length - 1, path.size());

        V curr = expectedPath[0];
        for (int i = 0; i < path.size(); i++) {
            Edge<V> edge = path.get(i);

            V next = edge.getOtherVertex(curr);
            cost += edge.getWeight();
            assertEquals(expectedPath[i + 1], next);

            curr = next;
        }

        assertEquals(expectedFinalCost, cost, 0.0001);
    }

    public Graph<String, SimpleEdge<String>> buildSimpleGraph() {
        IList<String> vertices = new DoubleLinkedList<>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");
        vertices.add("d");
        vertices.add("e");
        vertices.add("f");

        IList<SimpleEdge<String>> edges = new DoubleLinkedList<>();
        edges.add(edge("a", "b", 2));
        edges.add(edge("a", "c", 5));
        edges.add(edge("a", "d", 1));
        edges.add(edge("a", "f", 9));

        edges.add(edge("b", "c", 8));
        edges.add(edge("b", "d", 4));
        edges.add(edge("b", "e", 3));

        edges.add(edge("d", "f", 6));

        edges.add(edge("e", "f", 7));

        return new Graph<>(vertices, edges);

    }

    public Graph<String, SimpleEdge<String>> buildComplexGraph() {
        IList<String> vertices = new DoubleLinkedList<>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");
        vertices.add("d");
        vertices.add("e");
        vertices.add("f");
        vertices.add("g");

        IList<SimpleEdge<String>> edges = new DoubleLinkedList<>();
        edges.add(edge("a", "b", 1));
        edges.add(edge("a", "c", 4));
        edges.add(edge("a", "d", 7));
        edges.add(edge("a", "g", 9));

        edges.add(edge("b", "c", 2));

        edges.add(edge("c", "d", 3));
        edges.add(edge("c", "f", 0));

        edges.add(edge("d", "d", 3)); // self-loop
        edges.add(edge("d", "g", 8));

        edges.add(edge("e", "f", 1));
        edges.add(edge("e", "g", 2)); // parallel edge
        edges.add(edge("e", "g", 3)); // parallel edge
        edges.add(edge("e", "g", 3)); // parallel edge

        return new Graph<>(vertices, edges);
    }

    public Graph<String, SimpleEdge<String>> buildDisconnectedGraph() {
        IList<String> vertices = new DoubleLinkedList<>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");
        vertices.add("d");
        vertices.add("e");
        vertices.add("f");
        vertices.add("g");

        vertices.add("h");
        vertices.add("i");
        vertices.add("j");
        vertices.add("k");

        IList<SimpleEdge<String>> edges = new DoubleLinkedList<>();
        edges.add(edge("a", "b", 1));
        edges.add(edge("a", "c", 4));
        edges.add(edge("a", "d", 7));
        edges.add(edge("a", "g", 9));

        edges.add(edge("b", "c", 2));

        edges.add(edge("c", "d", 3));
        edges.add(edge("c", "f", 0));

        edges.add(edge("d", "d", 3)); // self-loop
        edges.add(edge("d", "g", 8));

        edges.add(edge("e", "f", 1));
        edges.add(edge("e", "g", 2)); // parallel edge
        edges.add(edge("e", "g", 3)); // parallel edge
        edges.add(edge("e", "g", 3)); // parallel edge

        edges.add(edge("h", "i", 3));
        edges.add(edge("h", "j", 1));
        edges.add(edge("h", "k", 1));

        edges.add(edge("i", "j", 4));
        edges.add(edge("i", "k", 2)); // parallel edge
        edges.add(edge("i", "k", 6)); // parallel edge

        edges.add(edge("j", "k", 3));

        return new Graph<>(vertices, edges);
    }

    @Test(timeout=SECOND)
    public void testSizeMethods() {
        Graph<String, SimpleEdge<String>> graph1 = this.buildSimpleGraph();
        assertEquals(6, graph1.numVertices());
        assertEquals(9, graph1.numEdges());

        Graph<String, SimpleEdge<String>> graph2 = this.buildComplexGraph();
        assertEquals(7, graph2.numVertices());
        assertEquals(13, graph2.numEdges());

        Graph<String, SimpleEdge<String>> graph3 = this.buildDisconnectedGraph();
        assertEquals(11, graph3.numVertices());
        assertEquals(20, graph3.numEdges());
    }

    @Test(timeout=SECOND)
    public void testGraphWithNegativeEdgesNotPermitted() {
        IList<String> vertices = new DoubleLinkedList<>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");

        IList<SimpleEdge<String>> edges = new DoubleLinkedList<>();
        edges.add(edge("a", "b", 3));
        edges.add(edge("b", "c", -1));
        edges.add(edge("a", "b", 4));

        try {
            new Graph<>(vertices, edges);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All ok -- expected result
        }
    }

    @Test(timeout=SECOND)
    public void testGraphWithBadEdgeNotPermitted() {
        IList<String> vertices = new DoubleLinkedList<>();
        vertices.add("a");
        vertices.add("b");
        vertices.add("c");

        IList<SimpleEdge<String>> edges = new DoubleLinkedList<>();
        edges.add(edge("a", "b", 3));
        edges.add(edge("b", "c", 4));
        edges.add(edge("c", "d", 4)); // 'd' is not a vertex in the above list

        try {
            new Graph<>(vertices, edges);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // All ok -- expected result
        }
    }

    @Test(timeout=SECOND)
    public void testFindingMst() {
        Graph<String, SimpleEdge<String>> graph = this.buildSimpleGraph();
        ISet<SimpleEdge<String>> mst = graph.findMinimumSpanningTree();

        assertEquals(graph.numVertices() - 1, mst.size());
        assertTrue(mst.contains(edge("a", "d", 1)));
        assertTrue(mst.contains(edge("a", "b", 2)));
        assertTrue(mst.contains(edge("b", "e", 3)));
        assertTrue(mst.contains(edge("a", "c", 5)));
        assertTrue(mst.contains(edge("d", "f", 6)));
    }

    @Test//(timeout=SECOND)
    public void testFindingShortestPathSimple() {
        Graph<String, SimpleEdge<String>> graph = this.buildSimpleGraph();

        checkPathMatches(graph, 7, new String[] {"a", "d", "f"});
        checkPathMatches(graph, 7, new String[] {"f", "d", "a"});
        checkPathMatches(graph, 12, new String[] {"c", "a", "d", "f"});
        checkPathMatches(graph, 12, new String[] {"f", "d", "a", "c"});
    }

    @Test(timeout=SECOND)
    public void testFindingShortestPathComplex() {
        Graph<String, SimpleEdge<String>> graph = this.buildComplexGraph();

        checkPathMatches(graph, 6, new String[] {"a", "b", "c", "f", "e", "g"});
        checkPathMatches(graph, 6, new String[] {"g", "e", "f", "c", "b", "a"});
        checkPathMatches(graph, 6, new String[] {"g", "e", "f", "c", "d"});
        checkPathMatches(graph, 6, new String[] {"d", "c", "f", "e", "g"});
        checkPathMatches(graph, 1, new String[] {"a", "b"});
        checkPathMatches(graph, 1, new String[] {"b", "a"});
    }

    @Test(timeout=SECOND)
    public void testFindingShortestPathSameStartAndEnd() {
        Graph<String, SimpleEdge<String>> graph = this.buildComplexGraph();
        IList<SimpleEdge<String>> path = graph.findShortestPathBetween("a", "a");
        assertEquals(0, path.size());
    }

    @Test(timeout=SECOND)
    public void testFindingShortestPathDisconnectedComponents() {
        Graph<String, SimpleEdge<String>> graph = this.buildDisconnectedGraph();

        checkPathMatches(graph, 6, new String[] {"a", "b", "c", "f", "e", "g"});
        checkPathMatches(graph, 2, new String[] {"i", "k"});

        try {
            graph.findShortestPathBetween("a", "i");
            fail("Expected NoPathExistsException");
        } catch (NoPathExistsException ex) {
            // All ok -- expected result
        }

        try {
            graph.findShortestPathBetween("i", "a");
            fail("Expected NoPathExistsException");
        } catch (NoPathExistsException ex) {
            // All ok -- expected result
        }
    }
}
