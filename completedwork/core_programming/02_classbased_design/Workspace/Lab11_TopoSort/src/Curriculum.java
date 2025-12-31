import java.util.ArrayList;

class Curriculum {
	ArrayList<Course> courses;
	Curriculum(ArrayList<Course> courses) {
		this.courses = courses;
	}
	Curriculum() {
		this(new ArrayList<Course>());
	}
	// EFFECT: adds another course to the set of known courses
	void addCourse(Course c) {
		this.courses.add(c);
	}

	// If this course is in processed, then do nothing; 
	// otherwise, process this course’s prereqs, then 
	// add it to processed
	ArrayList<Course> process(ArrayList<Course> processed, Course c) {
		if (!processed.contains(c)) {
			// process this course’s prereqs
			for (Course prereq : c.prereqs) {
				this.process(processed, prereq);
			}
			// add only after all prereqs are processed
			processed.add(c);
		}
		return processed;
	}
	// returns true if the provided Course appears in the schedule 
	// after all of its prerequisites are satisfied
	boolean comesAfterPrereqs(ArrayList<Course> schedule, Course c) {
		ArrayList<Course> allPrereqs = this.process(new ArrayList<Course>(), c);
		for (Course prereq : allPrereqs) {
			if (!schedule.contains(prereq)) {
				return false;
			}
		}
		return true;
	}
	// returns true if the schedule is valid for all the courses in it.
	boolean validSchedule(ArrayList<Course> schedule) {
		for (Course c : schedule) {
			if (!this.comesAfterPrereqs(schedule, c)) {
				return false;
			}
		}
		return true;
	}
	// process every course in the given list with that result list. 
	// Once every course has been processed, return the result list.
	ArrayList<Course> topSort() {
		ArrayList<Course> response = new ArrayList<Course>();
		for (Course c : this.courses) {
			response = this.process(response, c);
		}
		return response;
	}
}

class Course {
	String name;
	ArrayList<Course> prereqs;
	Course(String name) {
		this.name = name;
		this.prereqs = new ArrayList<Course>();
	}
	// EFFECT: adds a course as a prereq to this one
	void addPrereq(Course c) {
		this.prereqs.add(c);
	}
	// add methods here
}
