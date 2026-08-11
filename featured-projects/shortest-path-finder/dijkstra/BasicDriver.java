package dijkstra;

import graph.Edge;
import graph.Graph;

// Use this class to test Dijkstra's algorithm on a basic graph (without GUI)
// Use the graph created below or create a different graph,
// then run Dijkstra's on it and check the result
public class BasicDriver {
    public static void main(String[] args) {
        // The same graph as in the lecture slides on Dijkstra's
        Graph g = new Graph(7);
        g.addEdge(0, new Edge(0, 1, 2));
        g.addEdge(0, new Edge(0, 2, 10));
        g.addEdge(0, new Edge(0, 3, 4));
        g.addEdge(1, new Edge(1, 3, 1));
        g.addEdge(1, new Edge(1, 4, 3));
        g.addEdge(2, new Edge(2, 3, 1));
        g.addEdge(3, new Edge(3, 4, 3));
        g.addEdge(3, new Edge(3, 5, 4));
        g.addEdge(3, new Edge(3, 6, 4));
        g.addEdge(4, new Edge(4, 6, 1));
        g.addEdge(5, new Edge(5, 2, 2));
        g.addEdge(6, new Edge(6, 5, 1));

        DijkstraAlgorithm algo = new DijkstraAlgorithm(g);
        System.out.println(algo.computeShortestPath(0, 6)); // [0, 1, 4, 6]

        System.out.println(algo.getShortestDistance(0, 0)); // expect 0.0
        System.out.println(algo.getShortestDistance(0, 1)); // expect 2.0
        System.out.println(algo.getShortestDistance(0, 2)); // expect 9.0
        System.out.println(algo.getShortestDistance(0, 3)); // expect 3.0
        System.out.println(algo.getShortestDistance(0, 4)); // expect 5.0
        System.out.println(algo.getShortestDistance(0, 5)); // expect 7.0
        System.out.println(algo.getShortestDistance(0, 6)); // expect 6.0

    }
}
