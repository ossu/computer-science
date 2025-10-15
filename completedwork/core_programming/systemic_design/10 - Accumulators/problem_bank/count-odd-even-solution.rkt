;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname count-odd-even-solution) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))

;; count-odd-even-solution.rkt

; PROBLEM:
;
; Previously we have written functions to count the number of elements in a list. In this
; problem we want a function that produces separate counts of the number of odd and even
; numbers in a list, and we only want to traverse the list once to produce that result.
;
; Design a tail recursive function that produces the Counts for a given list of numbers.
; Your function should produce Counts, as defined by the data definition below.
;
; There are two ways to code this function, one with 2 accumulators and one with a single
; accumulator. You should provide both solutions.
;


(define-struct counts (odds evens))
;; Counts is (make-counts Natural Natural)
;; interp. describes the number of even and odd numbers in a list

(define C1 (make-counts 0 0)) ;describes an empty list
(define C2 (make-counts 3 2)) ;describes (list 1 2 3 4 5))


;; (listof Integer) -> Counts
;; Produce the count of odd and even numbers in lon

(check-expect (count empty) (make-counts 0 0))
(check-expect (count (list 1)) (make-counts 1 0))
(check-expect (count (list 2)) (make-counts 0 1))
(check-expect (count (list 1 2 3 4 5)) (make-counts 3 2))
(check-expect (count (list -1 -2 3 -4 5)) (make-counts 3 2))


; <template according to (listof Number) + 2 accumulators>
(define (count lon0)
  ;; odds: Natural; the count of odd numbers so far
  ;; evens: Natural; the count of even numbers so far
  ;;
  ;; (count (list 1 2 3) 0 0)
  ;; (count (list   2 3) 1 0)
  ;; (count (list     3) 1 1)
  ;; (count empty        2 1)
  (local [(define (count lon odds evens)
            (cond [(empty? lon)(make-counts odds evens)]
                  [else
                   (if (odd? (first lon))
                       (count (rest lon) (add1 odds) evens)
                       (count (rest lon) odds (add1 evens)))]))]
    (count lon0 0 0)))

#;
; <template according to (listof Number) + accumulator>
(define (count lon0)
  ;; rsf: Count; the counts so far
  ;; (count (list 1 2 3) (make-counts 0 0))
  ;; (count (list   2 3) (make-counts 1 0))
  ;; (count (list     3) (make-counts 1 1))
  ;; (count empty        (make-counts 2 1))
  (local [(define (count lon rsf)
            (cond [(empty? lon) rsf]
                  [else
                   (if (odd? (first lon))
                       (count (rest lon) (make-counts (add1 (counts-odds rsf)) (counts-evens rsf)))
                       (count (rest lon) (make-counts (counts-odds rsf) (add1 (counts-evens rsf)))))]))]
    (count lon0 (make-counts 0 0))))
