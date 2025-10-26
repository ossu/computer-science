// to represent a pet owner

import tester.Tester;

class Person {
    String name;
    IPet pet;
    int age;
 
    Person(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }
    
    // is this Person older than the given Person?
    boolean isOlder(Person other) {
    	return this.age > other.age;
    }
    
    boolean sameNamePet(IPet pet) {
    	return this.name == pet.returnName();
    }
    
    Person perish() {
    	return new Person(this.name, new NoPet(), this.age);
    }
}

// to represent a pet
interface IPet { 
	String returnName();
}
 
// to represent a pet cat
class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;
 
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    public String returnName() {
    	return this.name;
    }
}
 
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
 
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    
    public String returnName() {
    	return this.name;
    }
}

class NoPet implements IPet {
	NoPet() {}
	public String returnName () {
		return "";
	}
}

class ExamplesPetOwners {
	
	IPet spot = new Dog("Spot", "Lab", true);
	IPet fido = new Dog("Fido", "Terrior", false);
	
	IPet mittens = new Cat("Mittens", "Mane Coon", true);
	IPet kitten = new Cat("Kitten", "American", false);
	
	Person spot2 = new Person("Spot", this.spot, 22);
	Person bob = new Person("Bob", this.spot, 22);
	Person sally = new Person("Sally", this.fido, 36);
	Person sue = new Person("Sue", this.mittens, 42);
	Person billy = new Person("BIlly", this.kitten, 12);
	
	boolean testIsOlderThan(Tester t) {
		return t.checkExpect(this.bob.isOlder(this.billy), true) &&
				   t.checkExpect(this.sally.isOlder(this.sue), false);
	}
	
	boolean testSameNamePet(Tester t) {
		return t.checkExpect(this.bob.sameNamePet(this.mittens), false) &&
				   t.checkExpect(this.spot2.sameNamePet(this.spot), true);
	}
	
	boolean testPerish(Tester t) {
		return t.checkExpect(this.bob.perish(), new Person(this.bob.name, new NoPet(), this.bob.age));
	}
	
}