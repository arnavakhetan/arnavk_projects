package labeledgraph;

import graph.Graph;
import dijkstra.DijkstraAlgorithm;

/** The Driver class for the MST project.
 *  Should take the names of two input files (USA.txt and USA.bmp as command line arguments)
 *  */
public class GUIDriver {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No arguments");
			return;
		}
		Graph graph = new GraphWithCityLabels(args[0]); //load graph from the file given in args[0]
		DijkstraAlgorithm da = new DijkstraAlgorithm(graph);
		GUIAppForDijkstra app = new GUIAppForDijkstra(da, graph, args[1]);
		System.out.println(args[1]);
		// Once you fill in the code in GraphWithCityLabels, and specify command line arguments for GUIDriver in Run->EditConfigurations, the image should show up
	}
}