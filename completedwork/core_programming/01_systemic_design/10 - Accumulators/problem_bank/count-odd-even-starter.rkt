;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname count-odd-even-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))

;; count-odd-even-starter.rkt

; PROBLEM:
;
; Previously we have written functions to count the number
;of elements in a list. In this
; problem we want a function that produces separate
;counts of the number of odd and even
; numbers in a list, and we only want to traverse
;the list once to produce that result.
;
; Design a tail recursive function that produces
;the Counts for a given list of numbers.
; Your function should produce Counts, as defined
;by the data definition below.
;
; There are two ways to code this function,
;one with 2 accumulators and one with a single
; accumulator. You should provide both solutions.
;


(define-struct counts (odds evens))
;; Counts is (make-counts Natural Natural)
;; interp. describes the number of even and odd numbers in a list

(define C1 (make-counts 0 0)) ;describes an empty list
(define C2 (make-counts 3 2)) ;describes (list 1 2 3 4 5))

(check-expect (count empty) (make-counts 0 0))
(check-expect (count (list 1)) (make-counts 1 0))
(check-expect (count (list 2)) (make-counts 0 1))
(check-expect (count (list 1 2 3 4 5)) (make-counts 3 2))
(check-expect (count (list -1 -2 3 -4 5)) (make-counts 3 2))

(define (count1 lon acc)
  (cond [(empty? lon) acc]
        [(odd? (first lon))
         (count1 (rest lon) (make-counts (add1 (counts-odds acc))
                                         (counts-evens acc)))]
        [(even? (first lon))
         (count1 (rest lon) (make-counts (counts-odds acc)
                                         (add1 (counts-evens acc))))]))

(define (count2 lon odds evens)
  (cond [(empty? lon) (make-counts odds evens)]
        [(odd? (first lon)) (count2 (rest lon) (add1 odds) evens)]
        [(even? (first lon)) (count2 (rest lon) odds (add1 evens))]))

(define (count lon) (count1 lon (make-counts 0 0)))
;(define (count lon) (count2 lon 0 0))