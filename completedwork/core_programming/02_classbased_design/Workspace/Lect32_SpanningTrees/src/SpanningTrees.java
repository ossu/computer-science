import java.util.ArrayList;
import java.util.HashMap;

class Util {
	Util() {}

	boolean isOneTree(ArrayList<Vertex> vertices, HashMap<String, String> setMap) {
		// this seems wildly inefficient but it works
		for (Vertex v0 : vertices) {
			for (Vertex v1 : vertices) {
				if (!this.sameTree(v0.name, v1.name, setMap)) {
					return false;
				}
			}
		}
		return true;
	}
	boolean sameTree(String k1, String k2, HashMap<String, String> setMap) {
		return this.getStump(k1, setMap).equals(this.getStump(k2, setMap));
		//		if (setMap.get(k1).equals(setMap.get(k2))) {
		//			// easy answer
		//			return true;
		//		} else if (setMap.get(k1).equals(k1) &&
		//								setMap.get(k2).equals(k2)) {
		//									// if both nodes are part of their own sets they cannot be part
		//									// of the same set
		//									return false;
		//								} else {
		//									// run up the tree to see if it ends in the same tree
		//									// i can climb both sides here because when we get to a stump
		//									// the self reference will by definition create a loop that will
		//									// be broken by conditional above
		//									return sameTree(setMap.get(k1), setMap.get(k2), setMap);
		//								}
	}
	String getStump(String nodeName, HashMap<String, String> setMap) {
		String nodeSet = setMap.get(nodeName);
		if (nodeName.equals(nodeSet)) {
			return nodeSet;
		} else {
			return getStump(nodeSet, setMap);
		}
	}
	void union(HashMap<String, String> setMap, String k1, String k2) {
		if (setMap.get(k2).equals(k2)) {
			// if k2 is stump of tree change to k1
			setMap.put(k2, this.getStump(k1, setMap));
		} else {
			// go up the tree to change stump's set to k1
			this.union(setMap, k1, setMap.get(k2));
		}
	}
}

class Vertex {
	String name;
	ArrayList<Edge> outEdges;
	Vertex(String name, ArrayList<Edge> outEdges) {
		this.name = name;
		this.outEdges = outEdges;
	}
	Vertex(String name) {
		this(name, new ArrayList<Edge>());
	}
	void addEdge(Edge e) {
		this.outEdges.add(e);
	}
}

class Edge {
	String name;
	Vertex from, to;
	int weight;
	Edge(String name, Vertex from, Vertex to, int weight) {
		this.name = name;
		this.from = from;
		this.to = to;
		this.weight = weight;
		to.addEdge(this);
		from.addEdge(this);
	}
	Edge(Vertex from, Vertex to, int weight) {
		this("", from, to, weight);
	}
	Edge(Vertex from, Vertex to) {
		this("", from, to, 0);
	}
}

class Graph {
	ArrayList<Vertex> vertices;
	Graph(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	ArrayList<Edge> makePrimTree() {
		// The final resulting tree
		ArrayList<Edge> tree = new ArrayList<Edge>();
		// The set of connected vertices
		HashMap<Vertex, Boolean> connected = new HashMap<Vertex, Boolean>();
		// The priority queue of candidate edges
		PriorityQueue<Edge> frontier = new PriorityQueue<Edge>(new ParentSmallerEdge());

		if (!this.vertices.equals(null)) {
			for (Vertex v : vertices) {
				connected.put(v, false);
			}
			Vertex first = vertices.get(0);
			for (Edge e : first.outEdges) {
				frontier.add(e);
			}
			connected.put(first, true);
			while (!frontier.isEmpty()) {
				Edge lowestCostEdge = frontier.remove();
				if (connected.get(lowestCostEdge.from) && connected.get(lowestCostEdge.to)) {
					// if both vertices are connected do nothing
				} else if (!connected.get(lowestCostEdge.from)) {
					tree.add(lowestCostEdge);
					connected.put(lowestCostEdge.from, true);
					for (Edge e : lowestCostEdge.from.outEdges) {
						frontier.add(e);
					}
				} else {
					tree.add(lowestCostEdge);
					connected.put(lowestCostEdge.to, true);
					for (Edge e : lowestCostEdge.to.outEdges) {
						frontier.add(e);
					}
				}
			}
		}
		return tree;
	}
	ArrayList<Edge> makeKruskalTree() {
		HashMap<String, String> setMap = new HashMap<String, String>();;
		ArrayList<Edge> tree = new ArrayList<Edge>();
		PriorityQueue<Edge> worklist = new PriorityQueue<Edge>(new ParentSmallerEdge());

		for (Vertex v : this.vertices) {
			// initialize every node's representative to itself
			setMap.put(v.name, v.name);
			for (Edge e : v.outEdges) {
				// all edges in graph, sorted by edge weights
				worklist.add(e);
			}
		}
		Util u = new Util();
		while (!u.isOneTree(this.vertices, setMap)) {
			Edge lowestCostEdge = worklist.remove();
			if (u.sameTree(lowestCostEdge.from.name, lowestCostEdge.to.name, setMap)) {
				// discard this edge  
				// they're already connected
			} else {
				tree.add(lowestCostEdge);
				u.union(setMap, lowestCostEdge.to.name, lowestCostEdge.from.name);
			}
		}
		return tree;
	}
}
