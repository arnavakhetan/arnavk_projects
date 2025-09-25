package dijkstra;

import graph.Edge;
import graph.Graph;
import labeledgraph.GraphWithCityLabels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgorithm {
    private Graph graph;
    private List<Integer> shortestPath = null; // a list of vertices that are part of the shortest path
    private int sourceVertex = -1; // source vertex; will be set in computeShortestPath
    // FILL IN CODE: Add another instance variable: the table used in Dijkstra's algorithm. It will be filled in computeShortestPath
    private DijkstraTableElement[] table;

    private static class DijkstraTableElement {
        double distance;
        int path;

        private DijkstraTableElement() {
            distance = Double.POSITIVE_INFINITY;
            path = -1;
        }
    }

    /**
     * Constructor
     *
     * @param graph graph
     */
    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }
    /**
     * Returns the shortest path between the origin vertex and the destination vertex.
     * The result is stored in the ArrayList called shortestPath.
     * This function is called from GUIAppForDijkstra, after the user clicks on two cities.
     *
     * @param originId source vertex id
     * @param destId destination vertex id
     * @return the List of vertex ids (of vertices on the shortest path)
     */
    public List<Integer> computeShortestPath(int originId, int destId) {
        this.sourceVertex = originId;
        this.shortestPath = new ArrayList<>();
        // Created MinHeap - see class MinHeap and fill in code there
        // The MinHeap should be used to keep track of the vertex with the smallest distance
        // set up a new graph.Dijkstra's-table
        MinHeap minheap = new MinHeap(graph.numVertices());
        // FILL IN CODE:
        // Create and initialize Dijkstra's table and MinHeap
        // Run Dijkstra's algorithm to compute Dijkstra's table to store distances from the source vertex to all other vertices
        // Compute edges on the shortest path from originId to destId using the table.
        // Add each edge to the shortestPath list
        table = new DijkstraTableElement[graph.numVertices()];
        for (int i = 0; i < table.length; i++) {
            table[i] = new DijkstraTableElement();
        }
        table[originId].distance = 0;
        minheap.insert(originId, 0);
        while (!minheap.isEmpty()) {
            int u = minheap.removeMin();
            for (Edge edge = graph.getFirstEdge(u); edge != null; edge = edge.next()) {
                int v = edge.getNeighbor();
                double newDist = table[u].distance + edge.getCost();
                if (newDist < table[v].distance) {
                    table[v].distance = newDist;
                    table[v].path = u;
                    if (minheap.contains(v)) {
                        minheap.reduceKey(v, newDist);
                    } else {
                        minheap.insert(v, newDist);
                    }
                }
            }
        }
        int current = destId;
        while (current != -1) {
            shortestPath.add(0, current);
            current = table[current].path;
        }
        GraphWithCityLabels g = (GraphWithCityLabels) graph;
        String start = g.getVertex(originId).getCity();
        String end = g.getVertex(destId).getCity();
        System.out.println(start + " to " + end + ". The cost of the shortest path is " + (int) table[destId].distance);
        return shortestPath;
    }


    /** Return the shortest distance from originId to destId
     *
     * @param originId source vertex id
     * @param destId destination vertex id
     * @return shortest distance (from Dijkstra's table)
     */
    public double getShortestDistance(int originId, int destId) {
        // FILL IN CODE:
        // If the originId == sourceVertex and table[originId] != null,
        // the values in the Dijkstra's table have already been computed,
        // you just need to return the distance from the table.
        // Otherwise, you need to call computeShortestPath first
        if (originId != sourceVertex || table == null) {
            computeShortestPath(originId, destId);
        }
        return table[destId].distance;
    }

    // These are used by the graphical user interface, do not modify
    /**
     * Return the shortest path as a 2D array of Points.
     * Each element in the array is another array that has 2 Points:
     * these two points define the beginning and end of a line segment.
     * @return 2D array of points
     */
        public Point[][] getPath() {
            if (shortestPath == null) {
                return null;
            }
            return ((GraphWithCityLabels) graph).getPath(shortestPath);
        }
        /** Set the shortestPath to null */
        public void resetPath() {
            shortestPath = null;
        }
}

