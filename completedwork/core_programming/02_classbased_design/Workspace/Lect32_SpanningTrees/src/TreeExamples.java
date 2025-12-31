import java.util.Arrays;
import tester.Tester;
import java.util.ArrayList;

class Examples {
	Vertex a;
	Vertex b;
	Vertex c;
	Vertex d;
	Vertex e;
	Vertex f;
	Edge ab;
	Edge ae;
	Edge bc;
	Edge be;
	Edge bf;
	Edge cd;
	Edge ce;
	Edge df;

	Graph graph0;
	Graph graph1;

	void init() {
		a = new Vertex("A");
		b = new Vertex("B");
		c = new Vertex("C");
		d = new Vertex("D");
		e = new Vertex("E");
		f = new Vertex("F");
		ab = new Edge("AB", a, b, 30);
		ae = new Edge("AE", a, e, 50);
		bc = new Edge("BC", b, c, 40);
		be = new Edge("BE", b, e, 35);
		bf = new Edge("BF", b, f, 50);
		cd = new Edge("CD", c, d, 25);
		ce = new Edge("CE", c, e, 15);
		df = new Edge("DF", d, f, 50);

		graph1 = new Graph(new ArrayList<Vertex>(Arrays.asList(a, b, c, d, e, f)));
	}

	void testMakePrimTree(Tester t) {
		init();
		t.checkExpect(this.graph1.makePrimTree(), new ArrayList<Edge>(Arrays.asList(
				ab, be, ce, cd, bf)));
		ae.weight = 1;
		t.checkExpect(this.graph1.makePrimTree(), new ArrayList<Edge>(Arrays.asList(
				ae, ce, cd, ab, bf)));
	}
	void testMakeKruskalsTree(Tester t) {
		init();
		t.checkExpect(this.graph1.makeKruskalTree(), new ArrayList<Edge>(Arrays.asList(
				ce, cd, ab, be, df)));
		ae.weight = 1;
		t.checkExpect(this.graph1.makeKruskalTree(), new ArrayList<Edge>(Arrays.asList(
				ae, ce, cd, ab, df)));
	}
}
