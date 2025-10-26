	/*
	 *   +--------------------+
	*    | Person             |
	*    +--------------------+
	*    | String name        |
	*    | int age            |
	*    | String gender      |
	*    | Address address    | ----+
	*    +--------------------+     |
	*                               |
	*                               v 
	*                    +---------------------+
	*                    | Address             |
	*                    +---------------------+
	*                    | String City         |
	*                    | String State        |
	*                    +---------------------+
	 */

class Person{
	String name;
	int age;
	String gender;
	Address address;
	
	Person(String name, int age, String gender, Address address){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
}

class Address{
	String city;
	String state;
	
	Address(String city, String state){
		this.city = city;
		this.state = state;
	}
}

class ExamplesPersons{
	ExamplesPersons() {}
	
	Address boston = new Address("Boston", "MA");
	Address warwick = new Address("Warwick", "RI");
	Address nashua = new Address("Nashua", "NH");
	
	Person tim = new Person("Tim", 23, "Male", this.boston);
	Person kate = new Person("Kate", 22, "Female", this.warwick);
	Person rebecca = new Person ("Rebecca", 31, "non-binary", this.nashua);
}