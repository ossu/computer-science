import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

class Vertex {
	//... any data about vertices, such as people's names, or place's GPS coordinates ...
	String name;
	ArrayList<Edge> outEdges; // edges from this node

	Vertex(String name, ArrayList<Edge> outEdges) {
		this.name = name;
		this.outEdges = outEdges;
	}
	Vertex(String name, Edge outEdge) {
		this(name, new ArrayList<Edge>(Arrays.asList(outEdge)));
	}
	Vertex(String name) {
		this(name, new ArrayList<Edge>());
	}
	void setEdge(Edge edge) {
		this.outEdges.add(edge);
	}

	boolean hasPathTo(Vertex dest) {
		ArrayList<Edge> checked = new ArrayList<Edge>();

		return this.hasPathToHelp(dest, checked);
	}
	boolean hasPathToHelp(Vertex dest, ArrayList<Edge> checked) {
		for (Edge edge : this.outEdges) {
			if (checked.contains(edge)) {
				continue;
			}
			checked.add(edge);
			if (edge.to == dest ||
					edge.to.hasPathToHelp(dest, checked)) {
				return true;
			}
		}
		return false;
	}
}

class Edge {
	Vertex from;
	Vertex to;
	int weight;

	Edge(Vertex from, Vertex to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		from.setEdge(this);
	}
	Edge(Vertex from, Vertex to) {
		this(from, to, 1);
	}
}

class Graph {
	ArrayList<Vertex> allVertices;

	Graph(ArrayList<Vertex> allVertices) {
		this.allVertices = allVertices;
	}
	Graph() {
		this.allVertices = new ArrayList<Vertex>();
	}

	ArrayList<Edge> getEdges() {
		ArrayList<Edge> response = new ArrayList<Edge>();
		for (Vertex vertex : this.allVertices) {
			for (Edge edge : vertex.outEdges) {
				if (!(response.contains(edge))) {
					response.add(edge);
				}
			}
		}
		return response;
	}

	boolean hasPathBetween(Vertex from, Vertex to) {
		Deque<Vertex> alreadySeen = new ArrayDeque<Vertex>();
		Deque<Vertex> worklist = new ArrayDeque<Vertex>();

		// Initialize the worklist with the from vertex
		worklist.addFirst(from);
		// As long as the worklist isn't empty...
		while (!worklist.isEmpty()) {
			Vertex next = worklist.removeLast();
			if (next.equals(to)) {
				return true; // Success!
			} else if (alreadySeen.contains(next)) {
				// do nothing: we've already seen this one
			} else {
				// add all the neighbors of next to the worklist for further processing
				for (Edge e : next.outEdges) {
					worklist.addFirst(e.to);
				}
				// add next to alreadySeen, since we're done with it
				alreadySeen.addFirst(next);
			}
		}
		// We haven't found the to vertex, and there are no more to try
		return false;
	}
	boolean breadthFistSearch(Vertex from, Vertex to) {
		return searchHelp(from, to, new Queue<Vertex>());
	}
	boolean depthFirstSearch(Vertex from, Vertex to) {
		return searchHelp(from, to, new Stack<Vertex>());
	}
	boolean searchHelp(Vertex from, Vertex to, ICollection<Vertex> worklist) {
		Deque<Vertex> alreadySeen = new ArrayDeque<Vertex>();

		// Initialize the worklist with the from vertex
		worklist.add(from);
		// As long as the worklist isn't empty...
		while (!worklist.isEmpty()) {
			Vertex next = worklist.remove();
			if (next.equals(to)) {
				return true; // Success!
			} else if (alreadySeen.contains(next)) {
				// do nothing: we've already seen this one
			} else {
				// add all the neighbors of next to the worklist for further processing
				for (Edge e : next.outEdges) {
					worklist.add(e.to);
				}
				// add next to alreadySeen, since we're done with it
				alreadySeen.addFirst(next);
			}
		}
		// We haven't found the to vertex, and there are no more to try
		return false;
	}
	Deque<Vertex> findPath(Vertex from, Vertex to, ICollection<Vertex> worklist) {
		Deque<Vertex> alreadySeen = new ArrayDeque<Vertex>();

		// Initialize the worklist with the from vertex
		worklist.add(from);
		// As long as the worklist isn't empty...
		while (!worklist.isEmpty()) {
			Vertex next = worklist.remove();
			if (next.equals(to)) {
				return alreadySeen; // Success!
			} else if (alreadySeen.contains(next)) {
				// do nothing: we've already seen this one
			} else {
				// add all the neighbors of next to the worklist for further processing
				for (Edge e : next.outEdges) {
					worklist.add(e.to);
				}
				// add next to alreadySeen, since we're done with it
				alreadySeen.addFirst(next);
			}
		}
		// We haven't found the to vertex, and there are no more to try
		throw new RuntimeException("Could not find a valid path");
	}
}
