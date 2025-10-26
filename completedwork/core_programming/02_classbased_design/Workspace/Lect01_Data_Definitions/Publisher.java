/*
 * Lecture 1 Notes
 */

// Booleans
 boolean isDaytime = true;
 boolean inNewYork - false;

 // Strings
 String courseName = "Fundies IT";

 // Naturals
 int ten = 10;
 double pointSix = 0.6;



 class Book {
    String title
    Author author;
    int price;
    Publisher publisher;

    // Constructor
    Book(String title, Author author, int price, Publisher publisher){
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
    }
 }

 class Author {
    String name;
    int yob;

    // constructor
    Author(String name, int yob) {
        this.name = name;
        this.yob = yob;
    }
 }

 class ExamplesBooks{
    //empty constructor
    ExamplesBooks() {}

    // creating example books
    Publisher doubleday = new Publisher("Doubleday", "USA", 1897);
    Author pat = new Author("Pat Conroy", 1948);
    Book beaches = new Book("Beaches", this.pat, 20, this.doubleday);

    String beachesAuthorName = this.beaches.author.name;
 }

/* Example Problem
 * Enhance the definitions of Book and Author above to include Publisher information. 
 * A Publisher should have fields representing their name (that is a String), 
 * their country of operation (that is also a String), and the year they opened 
 * for business (that is an int). Should Books or Authors have Publishers? 
 * Enhance the class diagram above to include your new inforamation. Define the new class. 
 * And enhance the ExamplesBooks class to include examples of the new data.
 */

  /* Updated
*    +---------------------+
*    | Book                |
*    +---------------------+
*    | String title        |
*    | Author author       |--------------------------------+
*    | Number price        |                                |
*    | Publisher publisher |----+                           |
*    +---------------------+    |                           |
*                               v                           v
*                      +----------------+            +-------------+
*                      | Publisher      |            | Author      |
*                      +----------------+            +-------------+
*                      | String name    |            | String name |
*                      | String country |            | Number yob  |
*                      | int yoo        |            +-------------+
*                      +----------------+
 */

 class Publisher {
    String name;
    String country;
    int yoo; // year of opening

    // constructor
    Publisher(String name, String country, int yoo){
        this.name = name;
        this.country = country;
        this.yoo = yoo;
    }
 }