import tester.Tester;

class Course {
	String department;
	int number;
	Instructor instructor;
	IList<Student> students;

	Course(String department, int number, Instructor instructor, IList<Student> students) {
		this.department = department;
		this.number = number;
		this.instructor = instructor;
		this.students = students;
	}
	Course(String department, int number) {
		this.department = department;
		this.number = number;
		this.instructor = null;
		this.students = new MtList<Student>();
	}
	// returns the instructor for this course
	Instructor getInstructor() {
		return this.instructor;
	}
	// returns the student enrollment for this course
	IList<Student> getStudents() {
		return this.students;
	}
	// adds the given instructor to this course
	void addInstructor(Instructor i) {
		if (this.instructor != null) {
			throw new RuntimeException("Only one instructor can be added to course");
		} else {
			this.instructor = i;
		}
	}
	// adds the given student to this course's rolls (IList<Student>)
	void enroll(Student s) {
		this.students = new ConsList<Student>(s, this.students);
	}
}

class Instructor {
	String first;
	String last;
	IList<Course> courses;
	Instructor(String first, String last, IList<Course> courses) {
		this.first = first;
		this.last = last;
		this.courses = courses;
	}
	Instructor(String first, String last) {
		this.first = first;
		this.last = last;
		this.courses = new MtList<Course>();
	}
	// returns this list of courses this instructor teaches
	IList<Course> getCourses() {
		return this.courses;
	}
	// adds this instructor to the given course
	void addCourse(Course c) {
		this.courses = new ConsList<Course>(c, this.courses);
		c.addInstructor(this);
	}
	// return true if the given Student is in more than one of 
	// this Instructor’s Courses.
	boolean dejavu(Student c) {
		return c.courses.map(new CoursesToInstructor()).count(this) > 1;
	}
}

class Student {
	String first;
	String last;
	IList<Course> courses;
	Student(String first, String last, IList<Course> courses) {
		this.first = first;
		this.last = last;
		this.courses = courses;
	}
	Student(String first, String last) {
		this.first = first;
		this.last = last;
		this.courses = new MtList<Course>();
	}
	IList<Course> getCourses() {
		return this.courses;
	}
	// adds this Student in the given Course. 
	void enroll(Course c) {
		this.courses = new ConsList<Course>(c, this.courses);
		c.enroll(this);
	}
	// returns true if the given Student is in any of the same classes as
	// this Student.
	boolean classmates(Student c) {
		return this.courses.mapList(new CoursesToStudents()).contains(c);
	}
}

class CoursesToStudents implements IFunc<Course, IList<Student>> {
	public IList<Student> apply(Course c) {
		return c.getStudents();
	}
}

class CoursesToInstructor implements IFunc<Course, Instructor> {
	public Instructor apply(Course c) {
		return c.getInstructor();
	}
}

class ExamplesRegistrar {
	Course c1;
	Course c2;
	Course c3;
	Course c4;
	Instructor i1;
	Instructor i2;
	Student s1;
	Student s2;
	Student s3;
	Student s4;
	Student s5;

	void initCond() {
		this.c1 = new Course("CS", 101);
		this.c2 = new Course("CS", 202);
		this.c3 = new Course("CS", 457);
		this.c4 = new Course("English", 305);

		this.i1 = new Instructor("Vidoje", "Mihajlovikj");
		this.i2 = new Instructor("Bill", "Shakes");

		this.s1 = new Student("Kevin", "Hart");
		this.s2 = new Student("Ted", "Kaczenski");
		this.s3 = new Student("Fred", "Flinstone");
		this.s4 = new Student("Eddy", "Brock");
		this.s5 = new Student("Steve", "McRaft");

		this.i1.addCourse(c1);
		this.i1.addCourse(c2);
		this.i1.addCourse(c3);
		this.i2.addCourse(c4);

		this.s1.enroll(c1);
		this.s1.enroll(c2);
		this.s1.enroll(c3);
		this.s2.enroll(c2);
		this.s2.enroll(c3);
		this.s3.enroll(c1);
		this.s4.enroll(c3);
		this.s5.enroll(c4);
	}

	void testConstructors(Tester t) {
		initCond();
		t.checkException(new RuntimeException("Only one instructor can be added to course"),
				c2, "addInstructor", i2);
	}
	void testClassmates(Tester t) {
		initCond();
		t.checkExpect(this.s1.classmates(s2), true);
		t.checkExpect(this.s2.classmates(s1), true);
		t.checkExpect(this.s5.classmates(s2), false);
		t.checkExpect(this.s2.classmates(s5), false);
	}
	void testDejavu(Tester t) {
		initCond();
		t.checkExpect(this.i1.dejavu(s1), true);
		t.checkExpect(this.i1.dejavu(s2), true);
		t.checkExpect(this.i2.dejavu(s1), false);
		t.checkExpect(this.i2.dejavu(s5), false);
	}
}
