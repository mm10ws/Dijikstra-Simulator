//description: represents a vertex in a graph

class Vertex {
	public int node; // vertex number in graph
	public int cost; // associated cost value

	// getters and setters

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	// constructor
	public Vertex(int node, int cost) {
		this.node = node;
		this.cost = cost;
	}

}