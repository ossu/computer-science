interface ICompareRunners {
	// Returns a negative number if r1.time is less than r2.time (0 for equal)
	int comesBefore(Runner r1, Runner r2);
}

class CompareByTime implements ICompareRunners {
	public int comesBefore(Runner r1, Runner r2) {
		return r1.time - r2.time;
	}
}

class CompareByName implements ICompareRunners {
	public int comesBefore(Runner r1, Runner r2) {
		return r1.name.compareTo(r2.name);
	}
}

class ReverseComparator implements ICompareRunners {
	ICompareRunners comp;
	ReverseComparator(ICompareRunners comp) {
		this.comp = comp;
	}
	public int comesBefore(Runner r1, Runner r2) {
		return this.comp.comesBefore(r1, r2) * -1;
	}
}
