class IsIntPositive implements IPred<Integer> {
	public boolean apply(Integer i) {
		return i > 0;
	}
}

class FindKey implements IPred<Pair<String, JSON>> {
	String find;
	FindKey(String find) {
		this.find = find;
	}
	public boolean apply(Pair<String, JSON> p) {
		return find.compareTo(p.x) == 0;
	}
}

class MatchString implements IPred<String> {
	String find;
	MatchString(String find) {
		this.find = find;
	}
	public boolean apply(String str) {
		return find.compareTo(str) == 0;
	}
}

class PairsToKey implements IFunc<Pair<String, JSON>, String> {
	public String apply(Pair<String, JSON> p) {
		return p.x;
	}
}

class PairsToJSON implements IFunc<Pair<String, JSON>, JSON> {
	public JSON apply(Pair<String, JSON> p) {
		return p.y;
	}
}

class SumInteger implements IFunc2<Integer, Integer, Integer> {
	public Integer apply(Integer i, Integer sum) {
		return i + sum;
	}
}

interface IJsonVisitor<R> extends IFunc<JSON, R> {
	R visitBlank(JSONBlank blank);
	R visitNumber(JSONNumber number);
	R visitBool(JSONBool bool);
	R visitString(JSONString str);
	R visitList(JSONList list);
	R visitObject(JSONObject obj);
}

class JSONToNumber implements IJsonVisitor<Integer> {
	public Integer apply(JSON j) {
		return j.accept(this);
	}
	public Integer visitBlank(JSONBlank blank) {
		return 0;
	}
	public Integer visitNumber(JSONNumber number) {
		return number.number;
	}
	public Integer visitBool(JSONBool bool) {
		if (bool.bool) {
			return 1;
		} else {
			return 0;
		}
	}
	public Integer visitString(JSONString str) {
		return str.str.length();
	}
	public Integer visitList(JSONList list) {
		return list.values.map(new JSONToNumber()).foldr(new SumInteger(), 0);
	}
	public Integer visitObject(JSONObject obj) {
		return obj.pairs.map(new PairsToJSON()) // List<Pairs<String, JSON> -> JSONList
				.map(new JSONToNumber())            // JSONList -> List<Integer>
				.foldr(new SumInteger(), 0);        // List<Integer> -> Integer
	}
}

interface IPairsVisitor<R> extends IFunc<JSON, R> {}

class JSONFind implements IJsonVisitor<JSON> {
	String find;
	JSONFind(String find) {
		this.find = find;
	}
	public JSON apply(JSON j) {
		return j.accept(this);
	}
	public JSON visitBlank(JSONBlank blank) {
		return new JSONBlank();
	}
	public JSON visitNumber(JSONNumber number) {
		return new JSONBlank();
	}
	public JSON visitBool(JSONBool bool) {
		return new JSONBlank();
	}
	public JSON visitString(JSONString str) {
		if (this.find.compareTo(str.str) == 0) {
			return str;
		} else {
			return new JSONBlank();
		}
	}
	public JSON visitList(JSONList list) {
		return list.values                       //JSONList   -> List<JSON>
				.map(new JSONFind(find))             //List<JSON> -> List<JSON> (containing JSONBlank and value == find)
				.getFirstOrBackup(new JSONBlank());  //List<JSON> -> JSON       (first value of List<JSON>)
	}
	public JSON visitObject(JSONObject obj) {
		return obj.pairs                               // JSONObject                -> List<Pairs<String, JSON>
				.filter(new FindKey(find))                 // List<Pairs<String, JSON>> -> List<Pairs<String, JSON>> (where key == find)
				.map(new PairsToJSON())                    // List<Pairs<String, JSON>> -> List<JSON> 
				.getFirstOrBackup(new JSONBlank());        // List<JSON>                -> JSON       (first value of List<JSON>
	}
}
