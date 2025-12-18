import java.util.Objects;
import tester.Tester;

class Runner {
	int age;
	String name;

	Runner(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Runner)) {
			return false;
		}
		Runner rObj = (Runner) obj;
		return this.name.equals(rObj.name) && this.age == rObj.age;
	}

	public int hashCode() {
		return Objects.hash(this.name, this.age);
	}
}

class RunnerExamples {
	Runner amy;
	Runner bob;
	Runner cale;
	Runner dave;
	Runner stillBob;
	Runner notBob;
	void init() {
		amy = new Runner(50, "amy");
		bob = new Runner(40, "bob");
		cale = new Runner(30, "cale");
		dave = new Runner(20, "dave");

		stillBob = new Runner(40, "bob");
		notBob = new Runner(20, "bob");
	}
	void testHashEquality(Tester t) {
		init();
		t.checkExpect(this.bob.equals(this.bob), true);
		t.checkExpect(this.bob.equals(this.stillBob), true);
		t.checkExpect(this.bob.equals(this.notBob), false);
		t.checkExpect(this.bob.hashCode(), this.stillBob.hashCode());
		t.checkExpect(this.amy.hashCode(), new Runner(50, "amy").hashCode());
		t.checkExpect(this.bob.hashCode() == this.notBob.hashCode(), false);
	}
}
