class Runner {
	String name;
	int age;
	int bib;
	boolean isMale;
	int pos;
	int time;
	Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
		this.name = name;
		this.age = age;
		this.bib = bib;
		this.isMale = isMale;
		this.pos = pos;
		this.time = time;
	}

	public boolean isMaleRunner() {
		return this.isMale;
	}
	public boolean isFemaleRunner() {
		return !this.isMale;
	}
	boolean finishesBefore(Runner r) {
		return this.time < r.time;
	}
}
