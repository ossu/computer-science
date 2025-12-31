import java.util.ArrayList;
import java.util.Deque;
import java.util.Arrays;
import java.util.ArrayDeque;
import tester.Tester;

class GraphExamples {
	Vertex v0;
	Vertex v1;
	Vertex v2;
	Vertex v3;
	Vertex vAlone;
	ArrayList<Vertex> linearVerts;

	Edge e0;
	Edge e1;
	Edge e2;
	ArrayList<Edge> linearEdges;

	Vertex vA;
	Vertex vB;
	Vertex vC;
	Vertex vD;
	Vertex vE;
	ArrayList<Vertex> circVerts;

	Edge eAB;
	Edge eBD;
	Edge eBC;
	Edge eCA;
	Edge eEB;
	ArrayList<Edge> circEdges;

	Vertex wA;
	Vertex wB;
	Vertex wC;
	Vertex wD;
	Vertex wE;
	Vertex wF;
	ArrayList<Vertex> weightedVerts;

	Edge wAB;
	Edge wBC;
	Edge wCD;
	Edge wDE;
	Edge wEF;
	Edge wAF;
	ArrayList<Edge> weightedEdges;

	Graph gEmpty;
	Graph gLinear;
	Graph gCircular;
	Graph gWeighted;

	void init() {
		v0 = new Vertex("0");
		v1 = new Vertex("1");
		v2 = new Vertex("2");
		v3 = new Vertex("3");
		e0 = new Edge(v0, v1);
		e1 = new Edge(v1, v2);
		e2 = new Edge(v2, v3);
		linearVerts = new ArrayList<Vertex>(Arrays.asList(v0, v1, v2));
		linearEdges = new ArrayList<Edge>(Arrays.asList(e0, e1, e2));

		vA = new Vertex("A");
		vB = new Vertex("B");
		vC = new Vertex("C");
		vD = new Vertex("D");
		vE = new Vertex("E");
		eAB = new Edge(vA, vB);
		eBD = new Edge(vB, vD);
		eBC = new Edge(vB, vC);
		eCA = new Edge(vC, vA);
		eEB = new Edge(vE, vB);
		circVerts = new ArrayList<Vertex>(
				Arrays.asList(vA, vB, vC, vD, vE));
		circEdges = new ArrayList<Edge>(
				Arrays.asList(eAB, eBD, eBC, eCA, eEB));

		wA = new Vertex("A");
		wB = new Vertex("B");
		wC = new Vertex("C");
		wD = new Vertex("D");
		wE = new Vertex("E");
		wF = new Vertex("F");
		wAB = new Edge(wA, wB, 1);
		wBC = new Edge(wB, wC, 1);
		wCD = new Edge(wC, wD, 1);
		wDE = new Edge(wD, wE, 1);
		wEF = new Edge(wE, wF, 1);
		wAF = new Edge(wA, wF, 3);
		weightedVerts = new ArrayList<Vertex>(
				Arrays.asList(wA, wB, wC, wD, wE, wF));
		weightedEdges = new ArrayList<Edge>(
				Arrays.asList(wAB, wBC, wCD, wDE, wEF, wAF));

		vAlone = new Vertex("Alone");
		gEmpty = new Graph();
		gLinear = new Graph(linearVerts);
		gCircular = new Graph(circVerts);
		gWeighted = new Graph(weightedVerts);
	}
	void testGetEdges(Tester t) {
		init();
		t.checkExpect(this.gLinear.getEdges(), linearEdges);
		t.checkExpect(this.gCircular.getEdges(), circEdges);
	}
	void testHasPath(Tester t) {
		init();
		t.checkExpect(this.v0.hasPathTo(v3), true);
		t.checkExpect(this.v0.hasPathTo(vAlone), false);
		t.checkExpect(this.vA.hasPathTo(vC), true);
		t.checkExpect(this.vA.hasPathTo(vE), false);
		t.checkExpect(this.gLinear.hasPathBetween(v0, v3), true);
		t.checkExpect(this.gLinear.hasPathBetween(v0, vAlone), false);
		t.checkExpect(this.gCircular.hasPathBetween(vA, vC), true);
		t.checkExpect(this.gCircular.hasPathBetween(vA, vD), true);
		t.checkExpect(this.gCircular.hasPathBetween(vA, vE), false);
		t.checkExpect(this.gCircular.hasPathBetween(vE, vA), true);
	}
	void testSearch(Tester t) {
		init();
		t.checkExpect(this.gLinear.breadthFirstSearch(v0, v3), true);
		t.checkExpect(this.gLinear.breadthFirstSearch(v0, vAlone), false);
		t.checkExpect(this.gCircular.breadthFirstSearch(vA, vC), true);
		t.checkExpect(this.gCircular.breadthFirstSearch(vA, vD), true);
		t.checkExpect(this.gCircular.breadthFirstSearch(vA, vE), false);
		t.checkExpect(this.gCircular.breadthFirstSearch(vE, vA), true);

		t.checkExpect(this.gLinear.depthFirstSearch(v0, v3), true);
		t.checkExpect(this.gLinear.depthFirstSearch(v0, vAlone), false);
		t.checkExpect(this.gCircular.depthFirstSearch(vA, vC), true);
		t.checkExpect(this.gCircular.depthFirstSearch(vA, vD), true);
		t.checkExpect(this.gCircular.depthFirstSearch(vA, vE), false);
		t.checkExpect(this.gCircular.depthFirstSearch(vE, vA), true);

		IComparator<Vertex> smallerWeight = new SmallerVertexWeight();
		t.checkExpect(this.gWeighted.weightedSearch(wA, wF, smallerWeight), true);
		t.checkExpect(this.gWeighted.weightedSearch(wA, wB, smallerWeight), true);
		t.checkExpect(this.gWeighted.weightedSearch(wF, wA, smallerWeight), false);
	}
	//	void testWeightedSearch(Tester t) {
	//		init();
	//		ArrayList<Vertex> ans = new ArrayList<Vertex>();
	//		ans.add(wA);
	//		ans.add(wF);
	//		t.checkExpect(this.gWeighted.leastCostPath(wA, wF), ans);
	//		wAF.weight = 10;
	//		t.checkExpect(this.gWeighted.leastCostPath(wA, wF),
	//				new ArrayList<Vertex>(Arrays.asList(wA, wB, wC, wD, wE, wF)));
	//	}
}
