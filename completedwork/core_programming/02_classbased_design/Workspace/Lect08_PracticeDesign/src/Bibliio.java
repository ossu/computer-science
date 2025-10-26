import tester.Tester;

interface IDoc {
	ILoString createBiblio();
}

class Book implements IDoc {
	String title;
	String author; // last, first
	String publisher;
	ILoDoc sources;

	Book(String title, String author, String publisher, ILoDoc sources) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.sources = sources;
	}

	public ILoString createBiblio() {
		return new ConsLoString(author + ". " + title, this.sources.createBiblio()).sort(); // !!! work
																																												// accumulator?
	}
}

class Wiki implements IDoc {
	String title;
	String author; // last, first
	String url;
	ILoDoc sources;

	Wiki(String title, String author, String url, ILoDoc sources) {
		this.title = title;
		this.author = author;
		this.url = url;
		this.sources = sources;
	}
	public ILoString createBiblio() {
		return this.sources.createBiblio().sort(); // wikis not included
	}
}

interface ILoDoc {
	ILoString createBiblio();
}

class MtLoDoc implements ILoDoc {
	MtLoDoc() {}
	public ILoString createBiblio() {
		return new MtLoString();
	}
}

class ConsLoDoc implements ILoDoc {
	IDoc first;
	ILoDoc rest;

	ConsLoDoc(IDoc first, ILoDoc rest) {
		this.first = first;
		this.rest = rest;
	}

	public ILoString createBiblio() {
		return new MtLoString();
	} // !!!
}

interface ILoString {
	ILoString sort();
	ILoString sortHelper(String entry);
}

class MtLoString implements ILoString {
	MtLoString() {}

	public ILoString sort() {
		return this;
	}
	public ILoString sortHelper(String entry) {
		return new ConsLoString(entry, this);
	}
}

class ConsLoString implements ILoString {
	String first;
	ILoString rest;

	ConsLoString(String first, ILoString rest) {
		this.first = first;
		this.rest = rest;
	}
	public ILoString sort() {
		return this.rest.sortHelper(this.first);
	}
	public ILoString sortHelper(String entry) {
		if (this.first.compareTo(entry) > 1) {
			return new ConsLoString(entry, this.sort());
		} else {
			return new ConsLoString(this.first, this.rest.sortHelper(entry));
		}
	}
}

class ExamplesDocs {
	IDoc sicp = new Book("Structure and Interpretation of Computer Programs", "Abelson, Harold",
			"MIT Press", new MtLoDoc());
	IDoc htdp = new Book("How to Design Programs", "Felleisen, Matthias",
			"MIT Press", new ConsLoDoc(this.sicp, new MtLoDoc()));
	IDoc htdpWiki = new Wiki("How to Design Programs", "Felleisen, Mathias",
			"wiki.com/HtDP", new ConsLoDoc(this.htdp, new ConsLoDoc(this.sicp, new MtLoDoc())));

	ILoString unsorted = new ConsLoString(
			"Felleisen, Matthias. How to Design Programs",
			new ConsLoString("Abelson, Harold. Structure and Interpretation of Computer Programs",
					new MtLoString()));
	ILoString sorted = new ConsLoString(
			"Abelson, Harold. Structure and Interpretation of Computer Programs",
			new ConsLoString("Felleisen, Matthias. How to Design Programs",
					new MtLoString()));

	boolean testCreateBiblio(Tester t) {
		return t.checkExpect(this.htdp.createBiblio(),
				new ConsLoString("Felleisen, Matthias. How to Design Programs", new MtLoString())) &&
						t.checkExpect(this.sicp.createBiblio(),
								new ConsLoString(
										"Abelson, Harold. Structure and Interpretation of Computer Programs",
										new ConsLoString("Felleisen, Matthias. How to Design Programs",
												new MtLoString()))) &&
						t.checkExpect(this.htdpWiki.createBiblio(),
								new ConsLoString(
										"Abelson, Harold. Structure and Interpretation of Computer Programs",
										new ConsLoString("Felleisen, Matthias. How to Design Programs",
												new MtLoString())));
	}

	boolean testSort(Tester t) {
		return t.checkExpect(this.unsorted.sort(), this.sorted);
	}
}
