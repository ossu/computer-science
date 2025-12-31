import java.util.Arrays;
import java.util.ArrayList;
import tester.Tester;

class Examples {
	Course algo = new Course("Algorithms and Data");
	Course compilers = new Course("Compilers");
	Course compSys = new Course("Computer Systems");
	Course database = new Course("Database Design");
	Course fundies2 = new Course("Fundies 2");
	Course fundies1 = new Course("Fundies 1");
	Course parallelProcessing = new Course("Large-Scale Parallel Data Processing");
	Course ooDesign = new Course("Object-Oriented Design");
	Course languages = new Course("Programming Languages");
	Course compTheory = new Course("Theory of Computation");

	ArrayList<Course> schedule0 = new ArrayList<Course>();
	ArrayList<Course> scheduleFull = new ArrayList<Course>(
			Arrays.asList(fundies1, fundies2, database,
					compSys, algo, parallelProcessing, ooDesign,
					compTheory, languages, compilers));
	ArrayList<Course> schedulePartial = new ArrayList<Course>(
			Arrays.asList(fundies1, fundies2, database,
					compSys, algo));
	ArrayList<Course> scheduleMissing = new ArrayList<Course>(
			Arrays.asList(fundies2, database,
					compSys, algo, parallelProcessing, ooDesign,
					compTheory, languages, compilers));
	ArrayList<Course> scheduleWrongOrder = new ArrayList<Course>(
			Arrays.asList(fundies1, fundies2, database,
					compSys, parallelProcessing, algo, ooDesign,
					compTheory, languages, compilers));

	Curriculum curri0;
	Curriculum curri1;

	void init() {
		//		fundies1
		this.database.addPrereq(fundies1);
		this.fundies2.addPrereq(fundies1);
		this.algo.addPrereq(fundies2);
		this.compSys.addPrereq(fundies2);
		this.ooDesign.addPrereq(fundies2);
		this.compTheory.addPrereq(fundies2);
		this.parallelProcessing.addPrereq(algo);
		this.parallelProcessing.addPrereq(compSys);
		this.languages.addPrereq(compTheory);
		this.languages.addPrereq(ooDesign);
		this.compilers.addPrereq(languages);

		this.curri0 = new Curriculum();
		this.curri1 = new Curriculum(new ArrayList<Course>(Arrays.asList(
				algo, compilers, compSys, database, fundies1, fundies2,
				parallelProcessing, ooDesign, languages, compTheory)));
	}
	void testProcess(Tester t) {
		init();
		t.checkExpect(curri1.process(new ArrayList<Course>(), fundies1),
				new ArrayList<Course>(Arrays.asList(fundies1)));
		t.checkExpect(curri1.process(new ArrayList<Course>(), fundies2),
				new ArrayList<Course>(Arrays.asList(fundies1, fundies2)));
		t.checkExpect(curri1.process(new ArrayList<Course>(), parallelProcessing),
				new ArrayList<Course>(Arrays.asList(fundies1, fundies2, algo, compSys,
						parallelProcessing)));
		t.checkExpect(curri1.process(new ArrayList<Course>(), compilers),
				new ArrayList<Course>(Arrays.asList(fundies1, fundies2, compTheory,
						ooDesign, languages, compilers)));
	}
	void testComesAfterPrereq(Tester t) {
		init();
		t.checkExpect(curri1.comesAfterPrereqs(scheduleFull, fundies1), true);
		t.checkExpect(curri1.comesAfterPrereqs(scheduleFull, compilers), true);
		t.checkExpect(curri1.comesAfterPrereqs(scheduleMissing, fundies2), false);
		t.checkExpect(curri1.comesAfterPrereqs(scheduleWrongOrder, fundies1), true);
		t.checkExpect(curri1.comesAfterPrereqs(scheduleWrongOrder, parallelProcessing), true);
	}
	void testValidSchedule(Tester t) {
		init();
		t.checkExpect(curri1.validSchedule(schedule0), true);
		t.checkExpect(curri1.validSchedule(scheduleFull), true);
		t.checkExpect(curri1.validSchedule(schedulePartial), true);
		t.checkExpect(curri1.validSchedule(scheduleMissing), false);
		t.checkExpect(curri1.validSchedule(scheduleWrongOrder), true);
	}
	void testTopologicalSort(Tester t) {
		init();
		t.checkExpect(curri0.topSort(), new ArrayList<Course>());
		t.checkExpect(curri1.topSort(), new ArrayList<Course>(Arrays.asList(
				fundies1, fundies2, algo, compTheory, ooDesign, languages,
				compilers, compSys, database, parallelProcessing)));
	}
}
