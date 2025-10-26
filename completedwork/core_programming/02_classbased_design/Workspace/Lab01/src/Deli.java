/* Problem 3
* 
* A deli menu includes soups, salads, and sandwiches. Every item has a name and a price (in cents - 
* so we have whole numbers only).
* 
* For each soup and salad we note whether it is vegetarian or not.
* 
* Salads also specify the name of any dressing being used.
* 
* For a sandwich we note the kind of bread, and two fillings (e.g peanut butter and jelly; or ham and cheese). 
* Assume that every sandwich will have two fillings, and ignore extras (mayo, mustard, tomatoes, lettuce, etc.)
* 
* Define classes to represent each of these kinds of menu items. Think carefully about what type each field 
* of each class should be. Do you need to define any interfaces? Construct at least two examples each of soups, 
* salads, and sandwiches.
* 
*/

// menu item
interface IMI {}

class Soup implements IMI {
	String name;
	int cost;
	boolean vegetarian;
	
	Soup(String name, int cost, boolean vegetarian){
		this.name = name;
		this.cost = cost;
		this.vegetarian = vegetarian;
	}
}

class Salad implements IMI {
	String name;
	int cost;
	boolean vegetarian;
	String dressing;
	
	Salad(String name, int cost, boolean vegetarian, String dressing){
		this.name = name;
		this.cost = cost;
		this.vegetarian = vegetarian;
		this.dressing = dressing;
	}
	
}

class Sandwich implements IMI {
	String name;
	int cost;
	String bread;
	String fillings;
	
	Sandwich(String name, int cost, String bread, String fillings){
		this.name = name;
		this.cost = cost;
		this.bread = bread;
		this.fillings = fillings;
	}
}

class ExamplesMenu{
	ExamplesMenu() {}
	
	IMI tomatoesoup = new Soup("Tomatoe", 5, true);
	IMI lintalsoup = new Soup("Lintal", 4, true);
	IMI cesearsalad = new Salad("Cesear", 7, false, "Ranch");
	IMI pbjsandwich = new Sandwich("PB & J", 2, "Wheat", "Peanutbutter & Jelly");
	
}