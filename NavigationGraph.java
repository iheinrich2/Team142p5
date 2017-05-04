import java.util.List;
import java.util.ArrayList;

public class NavigationGraph implements GraphADT<Location, Path> {

	//TODO: Implement all methods of GraphADT
	private List<GraphNode<Location, Path>> graph;
	private String[] edgePropertyNames;
	
	
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
		graph = new ArrayList<GraphNode<Location, Path>>();
	}


	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		Location location = new Location(name);
		return location; //TODO: implement correctly. 
	}


	@Override
	/**
	 * Adds a vertex to the Graph
	 * 
	 * @param vertex
	 *            vertex to be added
	 */
	public void addVertex(Location vertex) {
		GraphNode<Location, Path> newVertex = 
			new GraphNode(vertex, graph.size());
		graph.add(newVertex);
		
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

	}


	@Override
	/**
	 * Getter method for the vertices
	 * 
	 * @return List of vertices of type V
	 */
	public List<Location> getVertices() {
		//New list to store the vertices to be returned
		List<Location> vertices = new ArrayList<Location>();
		
		//Go through the graph and add all the vertices to the list
		for(int i = 0; i < graph.size(); i++){
			vertices.add(graph.get(i).getVertexData());
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
			//Search through graph
		for(int i = 0; i < graph.size(); i++){
			
			//check if it's the correct location
			if(src.equals(graph.get(i).getVertexData())){
				// recursive return call 
				return graph.get(i).getOutEdges();
			}
		}
		//if it's not a match return null
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
		//List of neighbors to be returned
		List<Location> neighbors = new ArrayList<Location>();
		
		//Iterate through graph
		for(int i = 0; i < graph.size(); i++){
			
			//Search for match
			if(vertex.equals(graph.get(i).getVertexData())){
				
				//List to store the values of out edges
				List<Path> outEdges = graph.get(i).getOutEdges();
				
				//Add neighbors to the list to be returned
				for(int x = 0; x < outEdges.size(); x++){
					neighbors.add(outEdges.get(x).getDestination());
				}
				
			}
		}
		//Return the list of neighbors
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
		return null;
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

}
