// 	Design a data representation for your registrar’s office.
//
//	Information about a course includes:
//		- a department name (a String), 
//		- a course number (int),
//		- an instructor (Instructor), 
//		- and an enrollment, which you should represent with a list of students. 
//		
//		For a student, the registrar keeps track of:
//		- the first name 
//		- last name
//		- the list of courses for which the student has enrolled. 
//
//		For an instructor, the registrar also keeps track of:
//		- first name
//		- last name
//		- a list of currently assigned courses.
//
//		Construct examples of at least  three courses, at least two professors 
//		(one of whom teaches more than one course) and at least four students 
//		(at least one of whom is enrolled in more than one class).

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
	void addInstructor(Instructor i) {
		if (this.instructor != null) {
			throw new RuntimeException("Only one instructor can be added to course");
		} else {
			this.instructor = i;
		}
	}
	void addStudent(Student s) {
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

	void addCourse(Course c) {
		this.courses = new ConsList<Course>(c, this.courses);
		c.addInstructor(this);
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
	void addCourse(Course c) {
		this.courses = new ConsList<Course>(c, this.courses);
		c.addStudent(this);
	}
}

class Examples {
	Course c1;
	Course c2;
	Course c3;
	Instructor i1;
	Instructor i2;
	Student s1;
	Student s2;
	Student s3;
	Student s4;

	void initCond() {
		this.c1 = new Course("CS", 101);
		this.c2 = new Course("CS", 202);
		this.c3 = new Course("English", 305);

		this.i1 = new Instructor("Vidoje", "Mihajlovikj");
		this.i2 = new Instructor("Bill", "Shakes");

		this.s1 = new Student("Kevin", "Hart");
		this.s2 = new Student("Ted", "Kaczenski");
		this.s3 = new Student("Fred", "Flinstone");
		this.s4 = new Student("Eddy", "Brock");

		this.i1.addCourse(c1);
		this.i1.addCourse(c2);
		this.i2.addCourse(c3);

		this.s1.addCourse(c1);
		this.s1.addCourse(c2);
		this.s1.addCourse(c3);
		this.s2.addCourse(c2);
		this.s2.addCourse(c3);
		this.s3.addCourse(c2);
		this.s4.addCourse(c3);
	}

	void testConstructors(Tester t) {
		initCond();
		t.checkException(
				new RuntimeException("Only one instructor can be added to course"),
				c2,
				"addInstructor",
				i2);
	}
}
