
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
	MTLoBuddy() {}

	// returns ILoBuddy which includes this and that
	public ILoBuddy combine(ILoBuddy that) {
		return that;
	}
	// returns true if that person is included in buddy list
	public boolean isIncluded(Person that) {
		return false;
	}
	// returns the count of common persons in buddy list
	public int countCommon(ILoBuddy those) {
		return 0;
	}
	public int countCommonHelper(ILoBuddy those, int csf) {
		return csf;
	}
	// returns true if that person is included this extended buddylist 
	public boolean hasExtendedBuddy(Person that) {
		return false;
	}
	public boolean hasExtendedBuddyHelper(Person that, ILoBuddy wasChecked) {
		return false;
	}
	public int partyCount(int csf, ILoBuddy wasChecked) {
		return csf;
	}
}
