interface IIceCream {

}

class EmptyServing implements IIceCream {
	boolean cone;
	EmptyServing(boolean cone) {
		this.cone = cone;
	}
}

class Scooped implements IIceCream {
	String firstFlavor;
	IIceCream more;
	Scooped(String firstFlavor, IIceCream more) {
		this.firstFlavor = firstFlavor;
		this.more = more;
	}

}

class ExamplesIceCream {
	IIceCream order1 = new Scooped("mint chip", new Scooped("coffee",
			new Scooped("black raspberry", new Scooped("caramel swirl", new EmptyServing(false)))));
	IIceCream order2 = new Scooped("chocolate",
			new Scooped("vanilla", new Scooped("strawberry", new EmptyServing(true))));
}
