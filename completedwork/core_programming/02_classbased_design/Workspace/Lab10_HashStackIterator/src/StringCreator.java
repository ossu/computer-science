import tester.Tester;

class StringCreator {
	String contents;
	Stack<String> actions;
	StringCreator() {
		contents = "";
		actions = new Stack<String>();
	}

	String getString() {
		return contents;
	}
	void add(char item) {
		this.actions.push(this.contents);
		this.contents = this.contents + item;
	}
	void remove() {
		this.actions.push(this.contents);
		this.contents = this.contents.substring(0, this.contents.length() - 1);
	}
	void undo() {
		if (!this.actions.isEmpty()) {
			this.contents = this.actions.pop();
		}
	}
}

class StringExamples {
	void testRun(Tester t) {
		StringCreator creator = new StringCreator();
		t.checkExpect(creator.getString(), "");
		creator.add('c');
		creator.add('d');
		t.checkExpect(creator.getString(), "cd");
		creator.add('e');
		t.checkExpect(creator.getString(), "cde");
		creator.remove();
		creator.remove();
		t.checkExpect(creator.getString(), "c");
		creator.undo(); //undoes the removal of 'd'
		t.checkExpect(creator.getString(), "cd");
		creator.undo(); //undoes the removal of 'e'
		creator.undo(); //undoes the addition of 'e'
		t.checkExpect(creator.getString(), "cd");
		creator.add('a');
		t.checkExpect(creator.getString(), "cda");
		creator.undo(); //undoes the addition of 'a'
		creator.undo(); //undoes the addition of 'd'
		creator.undo(); //undoes the addition of 'c'
		t.checkExpect(creator.getString(), "");
		creator.undo(); //no effect, there is nothing to undo
	}
}
