// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

	Person first;
	ILoBuddy rest;

	ConsLoBuddy(Person first, ILoBuddy rest) {
		this.first = first;
		this.rest = rest;
	}
	// returns ILoBuddy which includes this and that
	public ILoBuddy combine(ILoBuddy that) {
		if (!that.isIncluded(this.first)) {
			return new ConsLoBuddy(this.first, this.rest.combine(that));
		} else {
			return this.rest.combine(that);
		}
	}
	// returns true if that person is included in buddy list
	public boolean isIncluded(Person that) {
		if (this.first.equals(that)) {
			return true;
		} else {
			return this.rest.isIncluded(that);
		}
	}
	// returns the count of common persons in buddy list
	public int countCommon(ILoBuddy those) {
		return this.countCommonHelper(those, 0);
	}
	public int countCommonHelper(ILoBuddy those, int csf) {
		if (those.isIncluded(this.first)) {
			return this.rest.countCommonHelper(those, csf + 1);
		} else {
			return this.rest.countCommonHelper(those, csf);
		}
	}
	// returns true if that person is included this extended buddy list 
	public boolean hasExtendedBuddy(Person that) {
		return this.hasExtendedBuddyHelper(that, new MTLoBuddy());
	}
	public boolean hasExtendedBuddyHelper(Person that, ILoBuddy wasChecked) {
		if (wasChecked.isIncluded(this.first)) {
			// if the first person was already checked move on
			return this.rest.hasExtendedBuddyHelper(that, wasChecked);
		} else if (this.first.equals(that)) {
			// if the first person is the person person we are looking for return true
			return true;
		} else {
			return this.rest.combine(this.first.buddies)				// add first's buddies to work list accumulator (this.rest)
					.hasExtendedBuddyHelper(that,										// recursive call search
							new ConsLoBuddy(this.first, wasChecked));		// add first to wasChecked so we dont look at them again
		}
	}
	public int partyCount(int csf, ILoBuddy wasChecked) {
		if (wasChecked.isIncluded(this.first)) {
			// if the first person was already checked move on
			return this.rest.partyCount(csf, wasChecked);
		} else {
			return this.rest.combine(this.first.buddies)
					.partyCount(csf + 1,
							new ConsLoBuddy(this.first, wasChecked));
		}
	}
}
