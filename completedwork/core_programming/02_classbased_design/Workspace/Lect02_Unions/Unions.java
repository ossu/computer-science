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

/*
               +----------+                
               | IStation |                
               +----------+                
               +----------+                
                     |                     
                    / \                    
                    ---                    
                     |                     
         -----------------------           
         |                     |           
  +--------------+    +-----------------+  
  | TStop        |    | CommStation     |  
  +--------------+    +-----------------+  
  | String name  |    | String name     |  
  | String line  |    | String line     |  
  | double price |    | boolean express |  
  +--------------+    +-----------------+     
 */

//to represent a train station
interface IStation {
}

//to represent a subway station
class TStop implements IStation {
  String name;
  String line;
  double price;

  TStop(String name, String line, double price) {
    this.name = name;
    this.line = line;
    this.price = price;
  }
}

//to represent a stop on a commuter line
class CommStation implements IStation {
  String name;
  String line;
  boolean express;

  CommStation(String name, String line, boolean express) {
    this.name = name;
    this.line = line;
    this.express = express;
  }
}

class ExamplesIStation{
  ExamplesIStation() {}

  /*
  Harvard station on the Red line costs $1.25 to enter
  Kenmore station on the Green line costs $1.25 to enter
  Riverside station on the Green line costs $2.50 to enter

  Back Bay station on the Framingham line is an express stop
  West Newton stop on the Framingham line is not an express stop
  Wellesely Hills on the Worcester line is not an express stop
   */

  IStation harvard = new TStop("Harvard", "red", 1.25);
  IStation kenmore = new TStop("Kenmore", "green", 1.25);
  IStation riverside = new TStop("Riverside", "green", 2.50);

  IStation backbay = 
      new CommStation("Back Bay", "Framingham", true);
  IStation wnewton = 
      new CommStation("West Newton", "Framingham", false);
  IStation wellhills = 
      new CommStation("Wellesley Hills", "Worcester", false);
}