import tester.Tester;

class BagelRecipe {
	double MARGIN = 0.001; // needed for double approximations
	double flour;
	double water;
	double yeast;
	double salt;
	double malt;

	//maximal constructor
	BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
		Utils u = new Utils();
		this.flour = u.checkRatios(flour, flour, water, 1, this.MARGIN,
				"Flour: " + Double.toString(flour) + " & Water: " + Double.toString(water) + " be equal");
		this.water = water;
		this.yeast = u.checkRatios(yeast, yeast, malt, 1, this.MARGIN,
				"Yeast: " + Double.toString(yeast) + " & Malt: " + Double.toString(malt) + " be equal");
		this.salt = u.checkRatios(salt, flour, salt + yeast, 20, this.MARGIN,
				"Flour: " + Double.toString(flour) + " to Yeast: " + Double.toString(yeast) + " + Salt: "
						+ Double.toString(salt)
						+ " ratio must be 20 times greater");
		this.malt = malt;
	}
	//minimal constructor
	BagelRecipe(double flour, double yeast) {
		this.flour = flour;
		this.water = flour;
		this.yeast = yeast;
		this.malt = yeast;
		this.salt = (flour / 20) - yeast;
	}
	//weight constructor
	BagelRecipe(double flour, double yeast, double salt) {
		this.flour = flour * 4.25;
		this.water = this.flour;
		this.yeast = yeast * 48 / 5;
		this.salt = salt * 48 / 10;
		this.malt = this.yeast;
	}
	public boolean sameRecipe(BagelRecipe that) {
		Utils u = new Utils();
		return u.checkDouble(this.flour, that.flour, 1, this.MARGIN) &&
						u.checkDouble(this.water, that.water, 1, this.MARGIN) &&
						u.checkDouble(this.yeast, that.yeast, 1, this.MARGIN) &&
						u.checkDouble(this.malt, that.malt, 1, this.MARGIN) &&
						u.checkDouble(this.salt, that.salt, 1, this.MARGIN);
	}
}

class Utils {
	public boolean checkDouble(double d1, double d2, double ratio, double margin) {
		return Math.abs(d1 - d2 * ratio) < margin;
	}
	public double checkRatios(double value, double i1, double i2, double ratio, double margin,
			String msg) {
		if (checkDouble(i1, i2, ratio, margin)) {
			return value;
		} else {
			throw new IllegalArgumentException(msg);
		}
	}
	public boolean sameRecipe(BagelRecipe r1, BagelRecipe r2) {
		return r1.sameRecipe(r2);
	}
}

class ExamplesBagel {
	Utils u = new Utils();
	BagelRecipe b1 = new BagelRecipe(100, 100, 1, 4, 1);
	BagelRecipe b2 = new BagelRecipe(100, 1);
	BagelRecipe b3 = new BagelRecipe(101, 2);
	BagelRecipe b4 = new BagelRecipe(23.5, 0.10, 0.83);

	boolean testConstructorException(Tester t) {
		return t.checkConstructorException(
				new IllegalArgumentException("Flour: 101.0 & Water: 100.0 be equal"),
				"BagelRecipe", 101.0, 100.0, 1.0, 4.0, 1.1) &&
						t.checkConstructorException(
								new IllegalArgumentException("Yeast: 2.0 & Malt: 1.0 be equal"),
								"BagelRecipe", 100.0, 100.0, 2.0, 4.0, 1.0) &&
						t.checkConstructorException(
								new IllegalArgumentException(
										"Flour: 100.0 to Yeast: 1.0 + Salt: 5.0 ratio must be 20 times greater"),
								"BagelRecipe", 100.0, 100.0, 1.0, 5.0, 1.0);
	}
	boolean testMinConstructor(Tester t) {
		return t.checkExpect(u.sameRecipe(b2, b1), true) &&
						t.checkExpect(u.sameRecipe(b2, b3), false) &&
						t.checkExpect(u.sameRecipe(b2, b1), true) &&
						t.checkExpect(b1.sameRecipe(b2), true);
	}
}
