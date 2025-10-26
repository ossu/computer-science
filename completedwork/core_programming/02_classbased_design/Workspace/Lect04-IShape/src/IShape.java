import tester.*;
/**
 * HtDC Lectures
 * Lecture 5: Methods for unions of classes
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */


/*
 +----------------------------+                    
 | IShape                     |                    
 +----------------------------+                    
 | double area()              |                    
 | double distToOrigin()      |                    
 | IShape grow(int)           |                    
 | boolean biggerThan(IShape) |                    
 | boolean contains(CartPt)   |                    
 +----------------------------+                    
                    |                                    
                   / \                                   
                   ---                                   
                    |                                    
             -------------------------------------                  
             |                                   |                  
   +----------------------------+   +----------------------------+  
   | Circle                     |   | Square                     |  
   +----------------------------+   +----------------------------+  
 +-| CartPt center              | +-| CartPt nw                  |  
 | | int radius                 | | | int size                   |  
 | | String color               | | | String color               |  
 | +----------------------------+ | +----------------------------+  
 | | double area()              | | | double area()              |  
 | | double distToOrigin()      | | | double distToOrigin()      |  
 | | IShape grow(int)           | | | IShape grow(int)           |  
 | | boolean biggerThan(IShape) | | | boolean biggerThan(IShape) |  
 | | boolean contains(CartPt)   | | | boolean contains(CartPt)   |  
 | +----------------------------+ | +----------------------------+  
 +----+ +-------------------------+
      | |
      v v                                                                     
 +-----------------------+
 | CartPt                |
 +-----------------------+
 | int x                 |
 | int y                 |
 +-----------------------+
 | double distToOrigin() |
 | double distTo(CartPt) |
 +-----------------------+ 
 */

// to represent a geometric shape

/* EXERSIZE - !!!
 * Define a new shape, Combo, that contains two IShapes. Its distanceToOrigin should be the minimum 
 * of the distances of its two components, its area should be the sum of the areas of its two components,
 * it can grow by growing both of its components, and it contains a CartPt if either of its components 
 * contains the given point.
 */

/*
 +--------+
 | CartPt |
 +--------+
 | int x  |
 | int y  |
 +--------+
 
 */

// to represent a Cartesian point
class CartPt {
    int x;
    int y;
    
    CartPt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // to compute the distance form this point to the origin
    public double distToOrigin(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    // to compute the distance form this point to the given point
    public double distTo(CartPt pt){
        return Math.sqrt((this.x - pt.x) * (this.x - pt.x) + 
                         (this.y - pt.y) * (this.y - pt.y));
    }
}

interface IShape {
    // to compute the area of this shape
    double area();
    
    // to compute the distance form this shape to the origin
    double distToOrigin();
    
    // to increase the size of this shape by the given increment
    IShape grow(int inc);
    
    // is the area of this shape bigger than the area of the given shape?
    boolean biggerThan(IShape that);
    
    // does this shape (including the boundary) contain the given point?
    boolean contains(CartPt pt);
}

// to represent a circle
class Circle implements IShape {
    CartPt center;
    int radius;
    String color;
    
    Circle(CartPt center, int radius, String color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }
    
    /*
     // ** TEMPLATE ** 
     public returnType methodName() {
     ... this.center ...              -- CartPt
     ... this.radius ...              -- int
     ... this.color ...               -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     ... this.biggerThan(IShape that) ... -- boolean
     ... this.contains(CartPt pt) ...     -- boolean
     */
    // to compute the area of this shape
    public double area(){
        return Math.PI * this.radius * this.radius;
    }
    
    // to compute the distance form this shape to the origin
    public double distToOrigin(){
        return this.center.distToOrigin() - this.radius;
    }
    
    // to increase the size of this shape by the given increment
    public IShape grow(int inc){
        return new Circle(this.center, this.radius + inc, this.color);
    }
    
    // is the area of this shape bigger than the area of the given shape?
    public boolean biggerThan(IShape that){
        /*---------------------------------------------------
         // ** TEMPLATE ** 
         public returnType methodName() {
         ... this.center ...              -- CartPt
         ... this.radius ...              -- int
         ... this.color ...               -- String
         
         ... this.area() ...                  -- double 
         ... this.distToOrigin() ...          -- double 
         ... this.grow(int inc) ...           -- IShape
         
         ... that.center ...              -- CartPt
         ... that.radius ...              -- int
         ... that.color ...               -- String
         
         ... that.area() ...                  -- double 
         ... that.distToOrigin() ...          -- double 
         ... that.grow(int inc) ...           -- IShape
         ---------------------------------------------------*/
        return this.area() >= that.area();
    }
    
    // does this shape (including the boundary) contain the given point?
    public boolean contains(CartPt pt){
        /*---------------------------------------------------
         // ** TEMPLATE ** 
         public returnType methodName() {
         ... this.center ...              -- CartPt
         ... this.radius ...              -- int
         ... this.color ...               -- String
         
         ... this.area() ...                  -- double 
         ... this.distToOrigin() ...          -- double 
         ... this.grow(int inc) ...           -- IShape
         
         ... this.center.distToOrigin() ...      -- double
         ... this.center.distTo(CartPt x) ...    -- double
         
         ... pt.distToOrigin() ...               -- double
         ... pt.distTo(CartPt x) ...             -- double
         ---------------------------------------------------*/
        return this.center.distTo(pt) <= this.radius;
    }
    
}

// to represent a square
class Square implements IShape {
    CartPt nw;
    int size;
    String color;
    
    Square(CartPt nw, int size, String color) {
        this.nw = nw;
        this.size = size;
        this.color = color;
    }
    
    /*
     // ** TEMPLATE ** 
     returnType methodName() {
     ... this.nw ...              -- CartPt
     ... this.size ...            -- int
     ... this.color ...           -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     }
     */
    
    // to compute the area of this shape
    public double area(){
        return this.size * this.size;
    }
    
    // to compute the distance form this shape to the origin
    public double distToOrigin(){
        return this.nw.distToOrigin();
    }
    
    // to increase the size of this shape by the given increment
    public IShape grow(int inc){
        return new Square(this.nw, this.size + inc, this.color);
    }
    
    // is the area of this shape bigger than the area of the given shape?
    public boolean biggerThan(IShape that){
        /*---------------------------------------------------
         // ** TEMPLATE ** 
         public returnType methodName() {
         ... this.nw ...                  -- CartPt
         ... this.size ...                -- int
         ... this.color ...               -- String
         
         ... this.area() ...                  -- double 
         ... this.distToOrigin() ...          -- double 
         ... this.grow(int inc) ...           -- IShape
         
         ... that.nw ...                  -- CartPt
         ... that.size ...                -- int
         ... that.color ...               -- String
         
         ... that.area() ...                  -- double 
         ... that.distToOrigin() ...          -- double 
         ... that.grow(int inc) ...           -- IShape
         ---------------------------------------------------*/
        return this.area() >= that.area();
    }
    
    // does this shape (including the boundary) contain the given point?
    public boolean contains(CartPt pt){
        /*---------------------------------------------------
         // ** TEMPLATE ** 
         public returnType methodName() {
         ... this.nw ...                  -- CartPt
         ... this.size ...                -- int
         ... this.color ...               -- String
         
         ... this.area() ...                  -- double 
         ... this.distToOrigin() ...          -- double 
         ... this.grow(int inc) ...           -- IShape
         
         ... this.nw.distToOrigin() ...       -- double
         ... this.nw.distTo(CartPt x) ...     -- double
         
         ... pt.distToOrigin() ...               -- double
         ... pt.distTo(CartPt x) ...             -- double
         ---------------------------------------------------*/
        return (this.nw.x <= pt.x) && (pt.x <= this.nw.x + this.size) &&
        (this.nw.y <= pt.y) && (pt.y <= this.nw.y + this.size);            
    }
}

class Combo implements IShape {
    IShape s1;
    IShape s2;

    Combo(IShape s1, IShape s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public double area() {
        return s1.area() + s2.area();
    }

    public double distToOrigin() {
        return Math.min(s1.distToOrigin(), s2.distToOrigin());
    }

    public IShape grow(int inc) {
        return new Combo(this.s1.grow(inc), this.s2.grow(inc));
    }
    
    public boolean biggerThan(IShape that) {
        return this.area() > that.area();
    }

    public boolean contains(CartPt pt) {
        return s1.contains(pt) ||
        		   s2.contains(pt);
    }
}

class ExamplesShapes {
    ExamplesShapes() {}
    
    CartPt pt1 = new CartPt(0, 0);
    CartPt pt2 = new CartPt(3, 4);
    CartPt pt3 = new CartPt(7, 1);
    
    IShape c1 = new Circle(new CartPt(50, 50), 10, "red");
    IShape c2 = new Circle(new CartPt(50, 50), 30, "red");
    IShape c3 = new Circle(new CartPt(30, 100), 30, "blue");
    
    IShape s1 = new Square(new CartPt(50, 50), 30, "red");
    IShape s2 = new Square(new CartPt(50, 50), 50, "red");
    IShape s3 = new Square(new CartPt(20, 40), 10, "green");

    IShape co1 = new Combo(c1, s1);
    IShape co2 = new Combo(c2, s2);
    IShape co3 = new Combo(c3, s3);
    
    // test the method distToOrigin in the class CartPt
    boolean testDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.pt1.distToOrigin(), 0.0, 0.001) &&
        t.checkInexact(this.pt2.distToOrigin(), 5.0, 0.001);
    }
    
    // test the method distTo in the class CartPt
    boolean testDistTo(Tester t) { 
        return
        t.checkInexact(this.pt1.distTo(this.pt2), 5.0, 0.001) &&
        t.checkInexact(this.pt2.distTo(this.pt3), 5.0, 0.001);
    }
    
    // test the method area in the class Circle
    boolean testCircleArea(Tester t) { 
        return
        t.checkInexact(this.c1.area(), 314.15, 0.01);
    }
    
    // test the method grow in the class Circle
    boolean testSquareArea(Tester t) { 
        return
        t.checkInexact(this.s1.area(), 900.0, 0.01);
    }
    
    // test the method distToOrigin in the class Circle
    boolean testCircleDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.c1.distToOrigin(), 60.71, 0.01) &&
        t.checkInexact(this.c3.distToOrigin(), 74.40, 0.01);
    }
    
    // test the method distTo in the class Circle
    boolean testSquareDistToOrigin(Tester t) { 
        return
        t.checkInexact(this.s1.distToOrigin(), 70.71, 0.01) &&
        t.checkInexact(this.s3.distToOrigin(), 44.72, 0.01);
    }

       boolean testComboDistTo(Tester t) {
        return t.checkInexact(this.co1.distToOrigin(), this.c1.distToOrigin(), 0.01);
    }
    
    // test the method grow in the class Circle
    boolean testCircleGrow(Tester t) { 
        return
        t.checkExpect(this.c1.grow(20), this.c2);
    }
    
    // test the method grow in the class Circle
    boolean testSquareGrow(Tester t) { 
        return
        t.checkExpect(this.s1.grow(20), this.s2);
    }

    boolean testComboGrow(Tester t) {
        return t.checkExpect(this.co1.grow(20), this.co2);
    }
    
    // test the method biggerThan in the class Circle
    boolean testCircleBiggerThan(Tester t) { 
        return
        t.checkExpect(this.c1.biggerThan(this.c2), false) && 
        t.checkExpect(this.c2.biggerThan(this.c1), true) && 
        t.checkExpect(this.c1.biggerThan(this.s1), false) && 
        t.checkExpect(this.c1.biggerThan(this.s3), true);
    }
    
    // test the method biggerThan in the class Square
    boolean testSquareBiggerThan(Tester t) { 
        return
        t.checkExpect(this.s1.biggerThan(this.s2), false) && 
        t.checkExpect(this.s2.biggerThan(this.s1), true) && 
        t.checkExpect(this.s1.biggerThan(this.c1), true) && 
        t.checkExpect(this.s3.biggerThan(this.c1), false);
    }

    boolean testComboBiggerThan(Tester t) {
        return t.checkExpect(this.co1.biggerThan(this.co2), false) &&
               t.checkExpect(this.co2.biggerThan(this.co1 ), true);
    }
    
    // test the method contains in the class Circle
    boolean testCircleContains(Tester t) { 
        return
        t.checkExpect(this.c1.contains(new CartPt(100, 100)), false) && 
        t.checkExpect(this.c2.contains(new CartPt(40, 60)), true);
    }
    
    // test the method contains in the class Square
    boolean testSquareContains(Tester t) { 
        return
        t.checkExpect(this.s1.contains(new CartPt(100, 100)), false) && 
        t.checkExpect(this.s2.contains(new CartPt(55, 60)), true);
    }

    boolean testComboContains(Tester t) {
        return t.checkExpect(this.co1.contains(new CartPt(100, 100)), false) &&
               t.checkExpect(this.co2.contains(new CartPt(40, 60)), true);
    }
}

