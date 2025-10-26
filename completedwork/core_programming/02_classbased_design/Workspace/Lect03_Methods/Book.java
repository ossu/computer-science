mport tester.*;

/*
  +---------------+
  | Book          | 
  +---------------+
  | String title  |
  | String author |
  | int price     | 
  +---------------+  
*/


// to represent a book in a bookstore
class Book {
    String title;
    String author;
    int price;

    // the constructor
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    /* TEMPLATE:
       Fields:
       ... this.title ...         -- String
       ... this.author ...        -- String
       ... this.price ...         -- int
       
       Methods:
       ... this.salePrice(int) ...   -- int
    */

    // Compute the sale price of this Book given using 
    // the given discount rate (as a percentage)
    int salePrice(int discount) {
        return this.price - (this.price * discount) / 100;
    }
}

// examples and tests for the classes that represent
// books and authors
class ExamplesBooks {
    ExamplesBooks() {}

    // examples of books
    Book htdp = new Book("HtDP", "FFK", 60);
    Book beaches = new Book("Beaches", "PC", 20);

    // test the method salePrice for the class Book
    boolean testSalePrice(Tester t) {
        return t.checkExpect(this.htdp.salePrice(30), 42)
            && t.checkExpect(this.beaches.salePrice(20), 16);
    }
}