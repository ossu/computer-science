import tester.Tester;
//Ann's buddies are Bob and Cole
//Bob's buddies are Ann and Ed and Hank
//Cole's buddy is Dan
//Dan's buddy is Cole
//Ed's buddy is Fay
//Fay's buddies are Ed and Gabi
//Gabi's buddies are Ed and Fay
//Hank does not have any buddies
//Jan's buddies are Kim and Len
//Kim's buddies are Jan and Len
//Len's buddies are Jan and Kim

public class ExamplesBuddies {
	Person ann;
	Person bob;
	Person cole;
	Person dan;
	Person ed;
	Person fay;
	Person gabi;
	Person hank;
	Person jan;
	Person kim;
	Person len;

	Person lotsa;

	void initTestConditions() {
		this.ann = new Person("ann");
		this.bob = new Person("bob");
		this.cole = new Person("cole");
		this.dan = new Person("dan");
		this.ed = new Person("ed");
		this.fay = new Person("fay");
		this.gabi = new Person("gabi");
		this.hank = new Person("hank");
		this.jan = new Person("jan");
		this.kim = new Person("kim");
		this.len = new Person("len");

		this.ann.addBuddy(bob);
		this.ann.addBuddy(cole);
		this.bob.addBuddy(ann);
		this.bob.addBuddy(ed);
		this.bob.addBuddy(hank);
		this.cole.addBuddy(dan);
		this.dan.addBuddy(cole);
		this.ed.addBuddy(fay);
		this.fay.addBuddy(ed);
		this.fay.addBuddy(gabi);
		this.gabi.addBuddy(ed);
		this.gabi.addBuddy(fay);
		this.jan.addBuddy(kim);
		this.jan.addBuddy(len);
		this.kim.addBuddy(jan);
		this.kim.addBuddy(len);
		this.len.addBuddy(jan);
		this.len.addBuddy(kim);
	}

	void testInit(Tester t) {
		initTestConditions();
		t.checkExpect(this.ann.buddies,
				new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob, new MTLoBuddy())));
		t.checkExpect(this.hank.buddies, new MTLoBuddy());
	}
	void testCombine(Tester t) {
		initTestConditions();
		ILoBuddy mtList = new MTLoBuddy();
		ILoBuddy list1 = new ConsLoBuddy(this.ann, new ConsLoBuddy(this.bob, mtList));
		ILoBuddy list2 = new ConsLoBuddy(this.cole, new ConsLoBuddy(this.dan, mtList));
		ILoBuddy list_combo = new ConsLoBuddy(this.ann, new ConsLoBuddy(this.bob,
				new ConsLoBuddy(this.cole, new ConsLoBuddy(this.dan, mtList))));
		t.checkExpect(list1.combine(list2), list_combo);
	}

	void testHadBuddy(Tester t) {
		initTestConditions();
		t.checkExpect(this.ann.hasDirectBuddy(bob), true);
		t.checkExpect(this.ann.hasDirectBuddy(cole), true);
		t.checkExpect(this.ann.hasDirectBuddy(dan), false);
		t.checkExpect(this.cole.hasDirectBuddy(ann), false);
	}
	void testCountCommonBuddies(Tester t) {
		initTestConditions();
		this.lotsa = new Person("Lotsa");
		this.lotsa.addBuddy(ann);
		this.lotsa.addBuddy(bob);
		this.lotsa.addBuddy(cole);
		this.lotsa.addBuddy(dan);
		this.lotsa.addBuddy(ed);
		this.lotsa.addBuddy(fay);
		this.lotsa.addBuddy(gabi);
		this.lotsa.addBuddy(hank);
		this.lotsa.addBuddy(jan);
		this.lotsa.addBuddy(kim);
		this.lotsa.addBuddy(len);

		t.checkExpect(this.ann.countCommonBuddies(bob), 0);
		t.checkExpect(this.bob.countCommonBuddies(ann), 0);
		t.checkExpect(this.ann.countCommonBuddies(dan), 1);
		t.checkExpect(this.len.countCommonBuddies(kim), 1);
		t.checkExpect(this.lotsa.countCommonBuddies(bob), 3);
		t.checkExpect(this.bob.countCommonBuddies(lotsa), 3);
	}
	void testHasExtendedBuddy(Tester t) {
		initTestConditions();
		t.checkExpect(this.ann.hasExtendedBuddy(bob), true);
		t.checkExpect(this.ann.hasExtendedBuddy(ed), true);
		t.checkExpect(this.ann.hasExtendedBuddy(fay), true);
		t.checkExpect(this.ann.hasExtendedBuddy(gabi), true);
		t.checkExpect(this.ann.hasExtendedBuddy(jan), false);
	}
	void testPartyCount(Tester t) {
		t.checkExpect(this.len.partyCount(), 2);
		t.checkExpect(this.kim.partyCount(), 2);
		t.checkExpect(this.hank.partyCount(), 0);
		t.checkExpect(this.ann.partyCount(), 7);
	}
}
