
// represents a Person with a user name and a list of buddies
class Person {

	String username;
	ILoBuddy buddies;

	Person(String username) {
		this.username = username;
		this.buddies = new MTLoBuddy();
	}

	// add p to this person's buddy list
	void addBuddy(Person p) {
		this.buddies = new ConsLoBuddy(p, this.buddies);
	}

	// returns true if this Person has that as a direct buddy
	boolean hasDirectBuddy(Person that) {
		return this.buddies.isIncluded(that);
	}

	// returns the number of people that are direct buddies 
	// of both this and that person
	int countCommonBuddies(Person that) {
		return this.buddies.countCommon(that.buddies);
	}

	// will that person be invited to a party 
	// organized by this person?
	boolean hasExtendedBuddy(Person that) {
		return this.buddies.hasExtendedBuddy(that);
	}
	// renamed hasExtendedBuddy
	boolean isInvited(Person that) {
		return this.hasExtendedBuddy(that);
	}

	// returns the number of people who will show up at the party 
	// given by this person (not including inviter (this))
	int partyCount() {
		return this.buddies.partyCount(0,							// initialize count
				new ConsLoBuddy(this, new MTLoBuddy()));	// add this person to wasChecked to avoid off by one
	}
}
