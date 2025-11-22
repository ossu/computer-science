class Dog {
	String name;
	String breed;
	int yob;
	String state; // 2 letter abbreviation
	boolean hypoallergenic;

	Dog(String name, String breed, int yob, String state, boolean hypoallergenic) {
		this.name = name;
		this.breed = breed;
		this.yob = yob;
		this.state = state;
		this.hypoallergenic = hypoallergenic;
	}
}

class ExamplesDogs {
	Dog hufflepuff = new Dog("Hufflepuff", "Wheaton Terrior", 2012, "TX", true);
	Dog pearl = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);
}
