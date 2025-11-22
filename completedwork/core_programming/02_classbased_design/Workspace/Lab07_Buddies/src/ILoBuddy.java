
// represents a list of Person's buddies
interface ILoBuddy {
	// returns ILoBuddy which includes this and that
	ILoBuddy combine(ILoBuddy that);
	// returns true if that person is included in buddy list
	boolean isIncluded(Person that);
	// returns the count of common persons in buddy list
	int countCommon(ILoBuddy those);
	int countCommonHelper(ILoBuddy those, int csf);
	// returns true if that person is included this extended buddylist 
	boolean hasExtendedBuddy(Person that);
	boolean hasExtendedBuddyHelper(Person that, ILoBuddy wasChecked);
	int partyCount(int csf, ILoBuddy wasChecked);
}
