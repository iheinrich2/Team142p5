import java.util.List;

public class NavigationGraph implements GraphADT<Location, Path> {

	//TODO: Implement all methods of GraphADT
	private List<GraphNode<Location, Path>> graph;
	private String[] edgePropertyNames;
	
	
	public NavigationGraph(String[] edgePropertyNames) {
		this.edgePropertyNames = edgePropertyNames;
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
	public void addVertex(Location vertex) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addEdge(Location src, Location dest, Path edge) {
		// TODO Auto-generated method stub

	}


	@Override
	public List<Location> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Path getEdgeIfExists(Location src, Location dest) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Path> getOutEdges(Location src) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Location> getNeighbors(Location vertex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdgePropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
