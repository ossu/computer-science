;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname nqueens-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require spd/tags)

;; nqueens-starter.rkt


This project involves the design of a program to solve the n queens puzzle.

This starter file explains the problem and provides a few hints you can use
to help with the solution.

The key to solving this problem is to follow the recipes! It is a challenging
problem, but if you understand how the recipes lead to the design of a Sudoku
solve then you can follow the recipes to get to the design for this program.
  

The n queens problem consists of finding a way to place n chess queens
on a n by n chess board while making sure that none of the queens attack each
other. 

The BOARD consists of n^2 individual SQUARES arranged in 4 rows of 4 columns.
The colour of the squares does not matter. Each square can either be empty
or can contain a queen.

A POSITION on the board refers to a specific square.

A queen ATTACKS every square in its row, its column, and both of its diagonals.

A board is VALID if none of the queens placed on it attack each other.

A valid board is SOLVED if it contains n queens.


There are many strategies for solving nqueens, but you should use the following:
  
  - Use a backtracking search over a generated arb-arity tree that
    is trying to add 1 queen at a time to the board. If you find a
    valid board with 4 queens produce that result.

  - You should design a function that consumes a natural - N - and
    tries to find a solution.
    
    
    
NOTE 1: You can tell whether two queens are on the same diagonal by comparing
the slope of the line between them. If one queen is at row and column (r1, c1)
and another queen is at row and column (r2, c2) then the slope of the line
between them is: (/ (- r2 r1) (- c2 c1)).  If that slope is 1 or -1 then the
queens are on the same diagonal.