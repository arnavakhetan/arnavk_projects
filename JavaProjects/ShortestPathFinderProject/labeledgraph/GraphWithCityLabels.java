package labeledgraph;

import graph.Edge;
import graph.Graph;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/** A child class of class Graph that is used for GUI. Look at class Graph before writing code in this class.
 * Stores city vertices in the array, and stores the map that maps each city name to an index of the vertex.
 * The adjacency list is stored in the parent class Graph.
 * Methods used by GUI that have been provided to you.
 * Fill in code in the constructor.
 */
public class GraphWithCityLabels extends Graph {
    private VertexWithCityLocation[] cityVertices; // vertices of the graph
    private Map<String, Integer> citiesToIndices = new HashMap<>();
    public final int EPS_DIST = 5;

    /**
     * Constructor. Read graph info from the given file,
     * and create vertices and edges of the graph.
     *
     *  @param filename name of the file that has vertices and edges
     */
    public GraphWithCityLabels(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            line = reader.readLine();
            if (!line.equals("VERTICES")) throw new RuntimeException("Expected VERTICES");

            int numVerts = Integer.parseInt(reader.readLine());
            cityVertices = new VertexWithCityLocation[numVerts];

            java.lang.reflect.Field f1 = Graph.class.getDeclaredField("numVertices");
            java.lang.reflect.Field f2 = Graph.class.getDeclaredField("adjacencyList");
            f1.setAccessible(true);
            f2.setAccessible(true);
            f1.setInt(this, numVerts);
            f2.set(this, new Edge[numVerts]);


            for (int i = 0; i < numVerts; i++) {
                line = reader.readLine();
                String[] parts = line.trim().split("\\s+");
                String name = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                citiesToIndices.put(name, i);
                cityVertices[i] = new VertexWithCityLocation(name, x, y);
            }
            line = reader.readLine();
            if (!line.equals("EDGES")) throw new RuntimeException("Expected EDGES");

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) continue;

                String src = parts[0];
                String dest = parts[1];
                int cost = Integer.parseInt(parts[2]);

                int srcIndex = citiesToIndices.get(src);
                int destIndex = citiesToIndices.get(dest);

                Edge e = new Edge(srcIndex, destIndex, cost);
                addEdge(srcIndex, e);
                addEdge(destIndex, new Edge(destIndex, srcIndex, cost));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        Point[][] edges2D = new Point[numEdges()][2];
        int idx = 0;
        for (int i = 0; i < numVertices(); i++) {
            for (Edge tmp = getFirstEdge(i); tmp != null; tmp = tmp.next(), idx++) {
                edges2D[idx][0] = cityVertices[tmp.getSource()].getLocation();
                edges2D[idx][1] = cityVertices[tmp.getNeighbor()].getLocation();
            }
        }
        return edges2D;
    }

    /**
     * Get the vertices of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getVerticesAsPoints() {
        if (cityVertices == null) {
            System.out.println("Array of nodes is empty. Load the graph first.");
            return null;
        }
        Point[] nodes = new Point[this.cityVertices.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = this.cityVertices[i].getLocation();
        }
        return nodes;
    }

    /**
     * Used in GUIApp to display the names of the cities.
     * @return the list that contains the names of cities (that correspond
     * to the vertices of the graph)
     */
    public String[] getCities() {
        if (cityVertices == null) {
            return null;
        }
        String[] labels = new String[cityVertices.length];
        for (int i = 0; i < cityVertices.length; i++) {
            labels[i] = cityVertices[i].getCity();
        }
        return labels;
    }

    public int getId(String name) {
        return citiesToIndices.get(name);
    }

    /**
     * Return VertexWithCityLocation for the given vertexId
     * @param vertexId id of the vertex
     * @return VertexWithCityLocation that contains the name of the city, location on the image etc.
     */
    public VertexWithCityLocation getVertex(int vertexId) {
        return cityVertices[vertexId];
    }

    /** Take a list of node ids on the path and return an array where each
     * element contains two points (an edge between two consecutive nodes)
     * @param pathOfNodes A list of node ids on the path
     * @return array where each element is an array of 2 points
     */
    public Point[][] getPath(List<Integer> pathOfNodes) {
        int i = 0;
        Point[][] edges2D = new Point[pathOfNodes.size()-1][2];
        Integer vPrev = pathOfNodes.get(0); // node id

        for (int k = 1; k < pathOfNodes.size(); k++) {
            Integer vCurr = pathOfNodes.get(k); // node id
            // Need to add an edge between vPrev and vCurr
            edges2D[i][0] = (cityVertices[vPrev]).getLocation();
            edges2D[i][1] = (cityVertices[vCurr]).getLocation();
            i++;
            vPrev = vCurr;
        }

        return edges2D;
    }

    /**
     * Take the location of the mouse click as a parameter, and return the node
     * of the graph at this location. Needed in GUIApp class. No need to modify.
     *
     * @param loc the location of the mouse click
     * @return reference to the corresponding CityNode
     */
    public VertexWithCityLocation getNode(Point loc) {
        if (loc == null) {
            System.out.println("No node at this location. ");
            return null;
        }
        for (VertexWithCityLocation v : cityVertices) {
            Point p = v.getLocation();
            if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
                return v;
        }
        return null;
    }


}
