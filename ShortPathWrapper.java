/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          P5
// FILE:             ShortPathWrapper.java
//
// TEAM:    p5team 142
// Authors:
// Author1: (Jarrett Benson, jbenson6@wisc.edu, jbenson6, Lec 002)
// Author2: (Cameron Carlson, ccarlson24@wisc.edu, ccarlson, Lec 002) 
// Author3: (Isaac Heinrich, iheinrich@wisc.edu, iheinrich, Lec 002)
// Author4: (Evan Ogren, eogren@wisc.edu, eogren, Lec 002)
// Author4: (Mitchel Abts, abts3@wisc.edu, abts3, Lec 003)
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 * Class that assists with finding the shortest path
 * 
 * @author Isaac Heinrich
 *
 */
class ShortPathWrapper implements Comparable<ShortPathWrapper> {
	private GraphNode<Location, Path> node;
	private Double weight;
	private Double minDist;
	private boolean visited;
	private ShortPathWrapper Previous;

	public ShortPathWrapper(GraphNode<Location, Path> node, Double weight) {
		this.node = node;
		this.weight = weight;
		this.minDist = Double.MAX_VALUE;
		this.visited = false;
	}

	/**
	 * Returns the reference to the node.
	 * 
	 * @return GraphNode<Location, Path>
	 */
	GraphNode<Location, Path> getNode() {
		return node;
	}

	/**
	 * Returns the min distance.
	 * 
	 * @return the minimum distance
	 */
	Double getminDist() {
		return minDist;
	}

	/**
	 * Sets the minimum distance.
	 * 
	 * @param dist
	 *            the distance length to set minDist to,
	 */
	void setminDist(Double dist) {
		minDist = dist;
	}

	/**
	 * Returns the weight.
	 * 
	 * @return the weight
	 */
	Double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 * 
	 * @param num
	 *            the weight to be set
	 */
	void setWeight(Double num) {
		this.weight = num;
	}

	/**
	 * Sets the Previousious vertex.
	 * 
	 * @param Previous
	 *            the vertex to assign to Previous
	 */
	void setPrevious(ShortPathWrapper Previous) {
		this.Previous = Previous;
	}

	/**
	 * Returns the Previousious vertex.
	 * 
	 * @return Previous
	 */
	ShortPathWrapper getPrevious() {
		return Previous;
	}

	/**
	 * Returns true if the vertex is visited, false if not.
	 * 
	 * @return visited
	 */
	boolean visted() {
		return visited;
	}

	/**
	 * Sets the visited status.
	 * 
	 * @param bool
	 *            the boolean value to assign
	 */
	void setVisited(boolean bool) {
		this.visited = bool;
	}

	/**
	 * Returns true if the vertex's ID equals this ID.
	 * 
	 * @param vert
	 *            the vertex to compare to
	 * @return boolean value
	 */
	boolean equals(ShortPathWrapper vert) {
		if (this.node.getId() == vert.getNode().getId()) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * Returns a number depending on how this vertex 
	 * relates to the passed vertex.
	 * 
	 * @param o
	 *            the vertex to compare to
	 * @return int that depends on the comparison
	 */
	public int compareTo(ShortPathWrapper o) {
		if (this.weight > o.getminDist())
			return 1;
		if (this.weight < o.getminDist())
			return -1;
		if (this.weight == o.getminDist())
			return 0;
		return Integer.MAX_VALUE;
	}

}