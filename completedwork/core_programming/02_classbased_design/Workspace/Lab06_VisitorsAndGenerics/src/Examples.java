import tester.Tester;

class Examples {
	IList<Integer> list_int = new ConsList<Integer>(1,
			new ConsList<Integer>(2, new ConsList<Integer>(3, new MtList<Integer>())));

	boolean testFoldr(Tester t) {
		return t.checkExpect(this.list_int.foldr(new SumInteger(), 0), 6);
	}

	IList<JSON> list_json1 = new ConsList<JSON>(new JSONBlank(),
			new ConsList<JSON>(new JSONNumber(1), new ConsList<JSON>(new JSONBool(false),
					new ConsList<JSON>(new JSONString("A"), new MtList<JSON>()))));
	IList<Integer> list_j2n1 = new ConsList<Integer>(0,
			new ConsList<Integer>(1, new ConsList<Integer>(0,
					new ConsList<Integer>(1, new MtList<Integer>()))));
	JSONList jList = new JSONList(list_json1);
	IList<JSON> list_json2 = new ConsList<JSON>(jList, new MtList<JSON>());
	IList<Integer> list_j2n2 = new ConsList<Integer>(2, new MtList<Integer>());
	IList<JSON> list_json3 = new ConsList<JSON>(new JSONBlank(),
			new ConsList<JSON>(new JSONNumber(-1), new ConsList<JSON>(new JSONBool(false),
					new ConsList<JSON>(new JSONString(""), new MtList<JSON>()))));

	boolean testJSONMap(Tester t) {
		return t.checkExpect(this.list_json1.map(new JSONToNumber()), list_j2n1) &&
						t.checkExpect(this.list_json2.map(new JSONToNumber()), list_j2n2);
	}

	boolean testFilter(Tester t) {
		return t.checkExpect(this.list_j2n1.filter(new IsIntPositive()),
				new ConsList<Integer>(1, new ConsList<Integer>(1, new MtList<Integer>())));
	}
	boolean testFindSolution(Tester t) {
		return t.checkExpect(
				this.list_json1.findSolutionOrElse(new JSONToNumber(), new IsIntPositive(), 0), 1) &&
						t.checkExpect(
								this.list_json3.findSolutionOrElse(new JSONToNumber(), new IsIntPositive(), 0), 0);
	}

	Pair<String, JSON> p0 = new Pair<String, JSON>("A", new JSONBlank());
	Pair<String, JSON> p1 = new Pair<String, JSON>("B", new JSONNumber(1));
	Pair<String, JSON> p2 = new Pair<String, JSON>("C", new JSONBool(false));
	Pair<String, JSON> p3 = new Pair<String, JSON>("D", new JSONString("D"));

	JSONObject jObj1 = new JSONObject(new ConsList<Pair<String, JSON>>(p0,
			new ConsList<Pair<String, JSON>>(p1,
					new ConsList<Pair<String, JSON>>(p2,
							new ConsList<Pair<String, JSON>>(p3,
									new MtList<Pair<String, JSON>>())))));

	IList<JSON> list_json4 = new ConsList<JSON>(jObj1, new MtList<JSON>());

	boolean testJObjVisitor(Tester t) {
		return t.checkExpect(this.list_json4.map(new JSONToNumber()), this.list_j2n2);
	}

	boolean testJsonFind(Tester t) {
		return t.checkExpect(this.list_json4.map(new JSONFind("B")),
				new ConsList<JSON>(new JSONNumber(1), new MtList<JSON>())) &&
						t.checkExpect(this.list_json4.map(new JSONFind("E")),
								new ConsList<JSON>(new JSONBlank(), new MtList<JSON>()));
	}
}
