// a json value
interface JSON {
	<R> R accept(IJsonVisitor<R> visitor);
}

// no value
class JSONBlank implements JSON {
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitBlank(this);
	}
}

// a number
class JSONNumber implements JSON {
	int number;

	JSONNumber(int number) {
		this.number = number;
	}
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitNumber(this);
	}
}

// a boolean
class JSONBool implements JSON {
	boolean bool;

	JSONBool(boolean bool) {
		this.bool = bool;
	}
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitBool(this);
	}
}

// a string
class JSONString implements JSON {
	String str;

	JSONString(String str) {
		this.str = str;
	}
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitString(this);
	}
}

//a list of JSON values
class JSONList implements JSON {
	IList<JSON> values;

	JSONList(IList<JSON> values) {
		this.values = values;
	}
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitList(this);
	}
}

//a list of JSON pairs
class JSONObject implements JSON {
	IList<Pair<String, JSON>> pairs;

	JSONObject(IList<Pair<String, JSON>> pairs) {
		this.pairs = pairs;
	}
	public <R> R accept(IJsonVisitor<R> visitor) {
		return visitor.visitObject(this);
	}

	//	public String find(IPairsVisitor<String> visitor) {
	//		return 
	//	}
}

//generic pairs
class Pair<X, Y> {
	X x;
	Y y;

	Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}
