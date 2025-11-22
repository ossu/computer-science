interface IHousing {

}

class Hut implements IHousing {
	int capacity;
	int population;
	Hut(int capacity, int population) {
		this.capacity = capacity;
		this.population = population;
	}
}

class Inn implements IHousing {
	String name;
	int capacity;
	int population;
	int stalls;
	Inn(String name, int capacity, int population, int stalls) {
		this.name = name;
		this.capacity = capacity;
		this.population = population;
		this.stalls = stalls;
	}
}

class Castle implements IHousing {
	String name;
	String familyName;
	int population;
	int carriageHouse;
	Castle(String name, String familyName, int population, int carriageHouse) {
		this.name = name;
		this.familyName = familyName;
		this.population = population;
		this.carriageHouse = carriageHouse;
	}
}

interface ITransportation {

}

abstract class ATransportation implements ITransportation {
	IHousing from;
	IHousing to;
	ATransportation(IHousing from, IHousing to) {
		this.from = from;
		this.to = to;
	}
}

class Horse extends ATransportation {
	String name;
	String color;
	Horse(IHousing from, IHousing to, String name, String color) {
		super(from, to);
		this.name = name;
		this.color = color;
	}
}

class Carriage extends ATransportation {
	int tonnage;
	Carriage(IHousing from, IHousing to, int tonnage) {
		super(from, to);
		this.tonnage = tonnage;
	}
}

class ExamplesTravel {
	IHousing hovel1 = new Hut(5, 1);
	IHousing hovel2 = new Hut(4, 2);
	IHousing hovel3 = new Hut(6, 6);
	IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
	IHousing crossroads = new Inn("Inn at the Crossroads", 40, 20, 12);
	IHousing pony = new Inn("Prancing Pony", 50, 45, 13);

	ITransportation horse1 = new Horse(this.hovel1, this.pony, "Bill", "Chessnut");
	ITransportation horse2 = new Horse(this.pony, this.hovel2, "Fairy", "White");
	ITransportation carriage1 = new Carriage(this.hovel3, this.winterfell, 100);
	ITransportation carriage2 = new Carriage(this.winterfell, this.crossroads, 150);
}
