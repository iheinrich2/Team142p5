
/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          P5
// FILE:             NavigationGraph.java
//
// TEAM:    p5team 142
// Authors:
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)
// Author4: (Evan Ogren, eogren@wisc.edu, eogren, Lec 002)
// Author4: (Mitchel Abts, abts3@wisc.edu, abts3, Lec 003)
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class handles the NavigationGraph as well as it's methods.
 *
 *
 * @author Team 142
 */
public class NavigationGraph implements GraphADT<Location, Path> {

	// TODO: Implement all methods of GraphADT
	private ArrayList<GraphNode<Location, Path>> graph;
	private String[] edgePropertyNames;
	public int vertices;
	private int orig = 0;

	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
		graph = new ArrayList<GraphNode<Location, Path>>();
		this.vertices = 0;
	}

	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {

		for (int i = 0; i < vertices; i++) {
			// searches the list for the vertex with name, then returns the
			// vertex
			if (graph.get(i).getVertexData().getName().equalsIgnoreCase(name)) {
				return graph.get(i).getVertexData();
			}
		}
		// returns null if the name doesn't exist
		return null;
	}

	@Override
	/**
	 * Adds a vertex to the Graph if it doesn't exist
	 * 
	 * @param vertex
	 *            vertex to be added
	 */
	public void addVertex(Location vertex) {

		// checks if the vertex is already added, and adds it if not
		if (getLocationByName(vertex.getName()) == null) {
			graph.add(new GraphNode<Location, Path>(vertex, ++orig));
			vertices++;
		}
	}

	@Override
	/**
	 * Creates a directed edge from src to dest
	 * 
	 * @param src
	 *            source vertex from where the edge is outgoing
	 * @param dest
	 *            destination vertex where the edge is incoming
	 * @param edge
	 *            edge between src and dest
	 */
	public void addEdge(Location src, Location dest, Path edge) {
		// TODO Auto-generated method stub

		// error handling
		if (getLocationByName(src.getName()) == null || getLocationByName(dest.getName()) == null) {
			throw new IllegalArgumentException();
		}

		if (!edge.getSource().equals(src) || !edge.getDestination().equals(dest)) {
			throw new IllegalArgumentException();
		}

		// adds the src and dest if they weren't already added
		if (getGraphNode(src) == null) {
			addVertex(src);
		}

		if (getGraphNode(dest) == null) {
			addVertex(dest);
		}

		for (int i = 0; i < graph.size(); i++) {

			// looks for a vertex with the same name as src
			// adds the edge as an our edge from src
			if (src.getName().equals(graph.get(i).getVertexData().getName())) {
				GraphNode<Location, Path> tempSrc = graph.get(i);
				tempSrc.addOutEdge(edge);
			}
		}
	}

	@Override
	/**
	 * Getter method for the vertices
	 * 
	 * @return List of vertices of type V
	 */
	public List<Location> getVertices() {
		// TODO Auto-generated method stub
		// New list to store the vertices to be returned
		List<Location> vertices = new ArrayList<Location>();

		// Go through the graph and add all the vertices to the list
		for (int i = 0; i < graph.size(); i++) {
			vertices.add(i, graph.get(i).getVertexData());
		}
		return vertices;
	}

	@Override
	/**
	 * Returns edge if there is one from src to dest vertex else null
	 * 
	 * @param src
	 *            Source vertex
	 * @param dest
	 *            Destination vertex
	 * @return Edge of type E from src to dest
	 */
	public Path getEdgeIfExists(Location src, Location dest) {
		// TODO Auto-generated method stub
		GraphNode<Location, Path> temp = getGraphNode(src);
		List<Path> tempPathList = temp.getOutEdges();

		// checks each out edge's destination name for a match with passed
		// dest's name
		for (int i = 0; i < tempPathList.size(); i++) {

			if (dest.getName().equals(tempPathList.get(i).getDestination().getName())) {
				return tempPathList.get(i);
			}
		}
		return null;
	}

	@Override
	/**
	 * Returns the outgoing edges from a vertex
	 * 
	 * @param src
	 *            Source vertex for which the outgoing edges need to be obtained
	 * @return List of edges of type E
	 */
	public List<Path> getOutEdges(Location src) {
		// Search through graph
		for (int i = 0; i < graph.size(); i++) {

			// check if it's the correct location
			if (src.equals(graph.get(i).getVertexData())) {
				// recursive return call
				return graph.get(i).getOutEdges();
			}
		}
		// if it's not a match return null
		return null;
	}

	@Override
	/**
	 * Returns neighbors of a vertex
	 * 
	 * @param vertex
	 *            vertex for which the neighbors are required
	 * @return List of vertices(neighbors) of type V
	 */
	public List<Location> getNeighbors(Location vertex) {
		// List of neighbors to be returned
		List<Location> neighbors = new ArrayList<Location>();

		// Iterate through graph
		for (int i = 0; i < graph.size(); i++) {

			// Search for match
			if (vertex.equals(graph.get(i).getVertexData())) {

				// List to store the values of out edges
				List<Path> outEdges = graph.get(i).getOutEdges();

				// Add neighbors to the list to be returned
				for (int x = 0; x < outEdges.size(); x++) {
					neighbors.add(outEdges.get(x).getDestination());
				}

			}
		}
		// Return the list of neighbors
		return neighbors;
	}

	@Override
	/**
	 * Calculate the shortest route from src to dest vertex using
	 * edgePropertyName
	 * 
	 * @param src
	 *            Source vertex from which the shortest route is desired
	 * @param dest
	 *            Destination vertex to which the shortest route is desired
	 * @param edgePropertyName
	 *            edge property by which shortest route has to be calculated
	 * @return List of edges that denote the shortest route by edgePropertyName
	 */
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {
		// TODO Auto-generated method stub

		int index = 0;

		for (int i = 0; i < edgePropertyNames.length; i++) {
			//sets which index of the edge properties to use (1 for time, 2 for cost)
			if (edgePropertyName.compareToIgnoreCase(edgePropertyNames[i].toString()) == 0) {
				index = i;
			}
		}

			//gets the index of the source in graph
		int srcIndex = graph.indexOf(getGraphNode(src));
			//creates a wrapper list
		ArrayList<ShortPathWrapper> wrapperList = new ArrayList<ShortPathWrapper>();

		for (int i = 0; i < graph.size(); i++) {	//adds each vertex to the wrapper list
			wrapperList.add(i, new ShortPathWrapper(graph.get(i), Double.MAX_VALUE));
		}
			//creates the queue used in dijkstra's algorithm
		PriorityQueue<ShortPathWrapper> pq = new PriorityQueue<ShortPathWrapper>();
			//sets the min distance to 0 per dijkstra's algorithm
		wrapperList.get(srcIndex).setminDist(0.0);
			//adds the vertex to the priority queue
		pq.add(wrapperList.get(srcIndex));

		while (!pq.isEmpty()) {	//removes and visits the vertex
			ShortPathWrapper current = pq.remove();
			current.setVisited(true);

			//checks if the distance is less than other distances and updates the queue
			for (Path path : current.getNode().getOutEdges()) {
				ShortPathWrapper successor = wrapperList.get(getGraphNode(path.getDestination()).getId() - 1);
				double weight = path.getProperties().get(index);
				double totalDist = current.getminDist() + weight;

				if (totalDist < successor.getminDist()) {
					pq.remove(successor);
					successor.setminDist(totalDist);
					successor.setPrevious(current);
					pq.add(successor);
				}
			}
		}

		//creates the return path and path of wrappers
		List<Path> path = new ArrayList<Path>();
		List<ShortPathWrapper> wrapperPath = new ArrayList<ShortPathWrapper>();
		int wrapperCount = 0;
			//counts the number of wrappers
		for (ShortPathWrapper wrapper = wrapperList.get(getGraphNode(dest).getId() - 1); 
				wrapper != null; wrapper = wrapper.getPrevious()) {
			wrapperPath.add(wrapper);
			wrapperCount++;
		}

			//goes through the wrapper path and retrieves the min distance to be added to tempList
		for (int i = 0; i < wrapperCount - 1; i++) {
			List<Double> tempList = new ArrayList<Double>();
			boolean runOnce = true;

			for (int j = 0; j < edgePropertyNames.length; j++) {

				if (wrapperPath.get(i).getPrevious().getNode().getId() > 0 && runOnce) {
					wrapperPath.get(i).setminDist(
							wrapperPath.get(i).getminDist() - wrapperPath.get(i).getPrevious().getminDist());
					runOnce = false;
				}
				tempList.add(wrapperPath.get(i).getminDist());
			}
			//add the next vertex to the path
			path.add(new Path(wrapperPath.get(i).getPrevious().getNode().getVertexData(),
					wrapperPath.get(i).getNode().getVertexData(), tempList));
		}
		//reverses the path so it goes from start to end
		Collections.reverse(path);

		return path;

	}

	@Override
	/**
	 * Getter method for edge property names
	 * 
	 * @return array of String that denotes the edge property names
	 */
	public String[] getEdgePropertyNames() {
		return edgePropertyNames;
	}

	/**
	 * Returns the data of the node.
	 * 
	 * @param location
	 *            the location for which the data is desired
	 * @return GraphNode<Location, Path>
	 */
	private GraphNode<Location, Path> getGraphNode(Location location) {

		for (int i = 0; i < graph.size(); i++) { // searches for the vertex with
													// locations name and
													// returns it

			if (graph.get(i).getVertexData().getName().equals(location.getName())) {
				return graph.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a string representation of the data.
	 * 
	 */
	@Override
	public String toString() {
		String finalString = "";
		for (int i = 0; i < graph.size(); i++) {
			if (!graph.get(i).getOutEdges().isEmpty()) {
				String tempString = graph.get(i).toString();
				tempString = tempString.substring(1, tempString.length() - 1);
				finalString += tempString + "\n";
			}
		}
		return finalString;
	}
}
