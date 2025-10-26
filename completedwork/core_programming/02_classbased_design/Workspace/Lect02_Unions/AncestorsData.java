import tester.*;
/**
 * HtDC Lectures
 * Lecture 3: Data Definitions: Unions
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */

// to represent an ancestor tree
interface IAT { }

// to represent an unknown member of an ancestor tree
class Unknown implements IAT{
  Unknown() { }
}

// to represent a person with the person's ancestor tree
class Person implements IAT{
  String name;
  IAT mom;
  IAT dad;

  Person(String name, IAT mom, IAT dad){
    this.name = name;
    this.mom = mom;this.dad = dad;
  }
}

// examples and tests for the class hierarchy that represents 
// ancestor trees
class ExamplesAncestors{
  ExamplesAncestors(){}

  IAT unknown = new Unknown();
  IAT mary = new Person("Mary", this.unknown, this.unknown);
  IAT robert = new Person("Robert", this.unknown, this.unknown);
  IAT john = new Person("John", this.unknown, this.unknown);

  IAT jane = new Person("Jane", this.mary, this.robert);

  IAT dan = new Person("Dan", this.jane, this.john);
}