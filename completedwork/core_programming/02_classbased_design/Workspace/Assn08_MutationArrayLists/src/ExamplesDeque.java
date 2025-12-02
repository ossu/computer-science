import tester.Tester;
class ExamplesDeque {

	Deque<String> dq0;
	Deque<String> dq1;
	Deque<String> dq2;
	Sentinel<String> sent0;
	Sentinel<String> sent1;
	Sentinel<String> sent2;
	Node<String> nabc;
	Node<String> nbcd;
	Node<String> ncde;
	Node<String> ndef;
	Node<String> nHello;
	Node<String> nWorld;
	Node<String> nWelcome;
	Node<String> nTo;
	Node<String> nHTDP2;

	void testConstrutor(Tester t) {
		nabc = new Node<String>("abc");
		t.checkExpect(nabc.next, null);
		t.checkExpect(nabc.prev, null);
		sent1 = new Sentinel<String>();
		t.checkExpect(sent1.next, sent1);
		t.checkExpect(sent1.prev, sent1);
		dq0 = new Deque<String>();
		t.checkExpect(dq0.header, new Sentinel<String>());
		dq1 = new Deque<String>(sent1);
		t.checkExpect(dq1.header, sent1);

		nbcd = new Node<String>("bcd", sent1, sent1);
		t.checkExpect(sent1.prev, nbcd);
		t.checkExpect(sent1.next, nbcd);
		t.checkExpect(nbcd.prev, sent1);
		t.checkExpect(nbcd.next, sent1);

		sent0 = new Sentinel<String>();
		Node<String> null1 = null;	//needed to suppress warning in CheckException()
		t.checkException(
				new IllegalArgumentException("Cannot assign next or prev to null"),
				sent0,
				"insertBetween",
				null1,
				null1);
	}

	void testInsert(Tester t) {
		sent1 = new Sentinel<String>();
		nabc = new Node<String>("abc");
		nbcd = new Node<String>("bcd");
		ncde = new Node<String>("cde");
		ndef = new Node<String>("def");

		nabc.insertBetween(sent1, sent1);
		t.checkExpect(nabc.next, sent1);
		t.checkExpect(nabc.prev, sent1);
		t.checkExpect(sent1.next, nabc);
		t.checkExpect(sent1.prev, nabc);

		nbcd.insertAfter(nabc);
		t.checkExpect(sent1.next, nabc);
		t.checkExpect(nabc.prev, sent1);
		t.checkExpect(nabc.next, nbcd);
		t.checkExpect(nbcd.prev, nabc);
		t.checkExpect(nbcd.next, sent1);
		t.checkExpect(sent1.prev, nbcd);

		ncde.insertBefore(sent1);
		t.checkExpect(sent1.next, nabc);
		t.checkExpect(nabc.prev, sent1);
		t.checkExpect(nabc.next, nbcd);
		t.checkExpect(nbcd.prev, nabc);
		t.checkExpect(nbcd.next, ncde);
		t.checkExpect(ncde.prev, nbcd);
		t.checkExpect(ncde.next, sent1);
		t.checkExpect(sent1.prev, ncde);
	}

	void reset() {
		dq0 = new Deque<String>(); // empty dq
		sent0 = new Sentinel<String>(); // empty sentinel

		//    +---------------+
		//    | dq1<String> |
		//    +---------------+
		//    | header ----------+
		//    +---------------+  |
		//        +--------------+
		//        |
		//        v
		//  +------------------+
		//+---------->| sent1<String> |<-------------------------------------------+
		//|           +------------------+                                            |
		//|       +---- next             |                                            |
		//|       |   | prev ------------------------------------------------+        |
		//|       |   +------------------+                                   |        |
		//|       |                                                          |        |
		//|       v                                                          v        |
		//| +--------------+   +--------------+   +--------------+   +--------------+ |
		//| | nabc<String> |   | nbcd<String> |   | ncde<String> |   | ndef<String> | |
		//| +--------------+   +--------------+   +--------------+   +--------------+ |
		//| | "abc"        |   | "bcd"        |   | "cde"        |   | "def"        | |
		//| | next ----------->| next ----------->| next ----------->| next ----------+
		//+-- prev         |<--- prev         |<--- prev         |<--- prev         |
		//+--------------+   +--------------+   +--------------+   +--------------+

		sent1 = new Sentinel<String>();
		nabc = new Node<String>("abc");
		nbcd = new Node<String>("bcd");
		ncde = new Node<String>("cde");
		ndef = new Node<String>("def");
		nabc.insertAfter(sent1);
		nbcd.insertAfter(nabc);
		ncde.insertAfter(nbcd);
		ndef.insertAfter(ncde);
		dq1 = new Deque<String>(sent1); // provided dq

		sent2 = new Sentinel<String>();
		nHello = new Node<String>("Hello");
		nWorld = new Node<String>("world");
		nWelcome = new Node<String>("welcome");
		nTo = new Node<String>("to");
		nHTDP2 = new Node<String>("HTDP2");
		dq2 = new Deque<String>(sent2);
	}
	void testAddAt(Tester t) {
		reset();
		t.checkExpect(dq2.header.next, sent2);
		dq2.addAtHead(nHello);
		t.checkExpect(dq2.header.next, nHello);
		t.checkExpect(dq2.header.prev, nHello);
		dq2.addAtTail(nWorld);
		t.checkExpect(dq2.header.next, nHello);
		t.checkExpect(nHello.prev, sent2);
		t.checkExpect(nHello.next, nWorld);
		t.checkExpect(dq2.header.prev, nWorld);
	}
	void testRemoveAt(Tester t) {
		reset();
		t.checkExpect(this.dq1.removeFromHead(), this.nabc);
		t.checkExpect(this.sent1.next, this.nbcd);
		t.checkExpect(this.nbcd.prev, sent1);
		t.checkExpect(this.dq1.removeFromTail(), this.ndef);
		t.checkExpect(this.sent1.prev, this.ncde);
		t.checkExpect(this.ncde.next, this.sent1);

		t.checkException(
				new RuntimeException("Cannot remove item from empty list"),
				sent0,
				"removeNext");
		t.checkException(
				new RuntimeException("Cannot remove item from empty list"),
				sent0,
				"removePrev");
		t.checkException(
				new RuntimeException("Cannot remove sentinel node"),
				sent0,
				"remove");
	}
	void testSize(Tester t) {
		reset();
		t.checkExpect(dq0.size(), 0);
		t.checkExpect(dq1.size(), 4);

		t.checkExpect(sent2.size(), 0);
		t.checkExpect(dq2.size(), 0);
		dq2.addAtHead(nHello);
		t.checkExpect(dq2.size(), 1);
		dq2.addAtHead(nWorld);
		t.checkExpect(dq2.size(), 2);
		dq2.addAtHead(nWelcome);
		t.checkExpect(dq2.size(), 3);
		dq2.addAtHead(nTo);
		t.checkExpect(dq2.size(), 4);
		dq2.addAtHead(nHTDP2);
		t.checkExpect(dq2.size(), 5);
	}
	void testFind(Tester t) {
		reset();
		t.checkExpect(dq0.find(new MatchToData<String>("abc")), sent0);
		t.checkExpect(dq1.find(new MatchToData<String>("abc")), nabc);
		t.checkExpect(dq1.find(new MatchToData<String>("bcd")), nbcd);
		t.checkExpect(dq1.find(new MatchToData<String>("cde")), ncde);
		t.checkExpect(dq1.find(new MatchToData<String>("def")), ndef);
		t.checkExpect(dq1.find(new MatchToData<String>("cannotFind")), sent1);
	}
	void testRemoveNode(Tester t) {
		reset();

		// node not found so nothing happens
		t.checkExpect(dq0.size(), 0);
		dq0.removeNode(nabc);
		t.checkExpect(dq0.header, sent0);
		t.checkExpect(dq0.size(), 0);

		// remove first node
		t.checkExpect(dq1.size(), 4);
		dq1.removeNode(nabc);
		t.checkExpect(dq1.size(), 3);
		t.checkExpect(dq1.header.next, nbcd);

		// remove a middle node
		reset();
		t.checkExpect(dq1.size(), 4);
		dq1.removeNode(nbcd);
		t.checkExpect(dq1.size(), 3);
		t.checkExpect(nabc.next, ncde);

		// remove last node
		reset();
		t.checkExpect(dq1.size(), 4);
		dq1.removeNode(ndef);
		t.checkExpect(dq1.size(), 3);
		t.checkExpect(sent1.prev, ncde);
	}
}
