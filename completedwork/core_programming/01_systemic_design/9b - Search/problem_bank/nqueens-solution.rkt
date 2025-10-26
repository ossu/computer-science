;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname nqueens-solution) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require spd/tags)

;; nqueens-solution.rkt

;; A simple solution to the generic nqueens problem.
;;  - uses lambda to reduce and simplify the code


;; Data definitions:

(@HtDD Position)
;; Position is Natural
;; interp. positions on the board
;;         if    N is the number of queens
;;         then  (sqr N) is the number of positions on the board
;;         so    this number should be in [0, (- (sqr N) 1)]
(define P1 0)        ;upper left corner of board
(define P2 (- 16 1)) ;lower right corner of 4x4 board

;; Board is (listof Position)  up to N elements long
;; interp. the positions of the queens that have been placed on the board
(define BD1 empty)           ;no queens placed
(define BD2 (list 0))        ;one queen in upper left corner
(define BD3 (list 14 8 7 1)) ;a solution to 4x4 puzzle 




;; Functions:

;; NOTE about following function. It could have been designed with N as a
;; top-level constant and all the locally defined functions as top-level
;; functions. But doing it the way we have done below, makes it easy to make
;; the top-level nqueens function consume N which is kind of nice.  The
;; trampoline starts the actual search out by calling fn-for-bd with an empty
;; board.

(@HtDF nqueens)
(@signature Natural -> Board or false)
;; produce first found solution for n queens of size N; or false if none exists
(check-expect (nqueens 1) (list 0))
(check-expect (nqueens 2) false)
(check-expect (nqueens 3) false)
(check-expect (nqueens 4) (list 14 8 7 1))
(check-expect (nqueens 5) (list 23 16 14 7 0))
(check-expect (nqueens 6) (list 34 26 18 17 9 1))
(check-expect (nqueens 7) (list 47 38 29 27 18 9 0))
(check-expect (nqueens 8) (list 59 49 46 34 29 23 12 0))

(@template encapsulated backtracking genrec arb-tree)


(define (nqueens N)          
  ;; Termination argument:
  ;; Trivial cases:
  ;;   bd is solved or there are no valid next boards left to explore
  ;; 
  ;; Reduction step:
  ;;   (fn-for-lobd (next-boards bd)) in other words go explore the
  ;;   valid next boards of this board
  ;; 
  ;; Since board is finite, and each board is explored at most once, 
  ;; search will definitely terminate. (But the search space does grow
  ;; really fast!)
  
  (local [;; Board -> Board or false
          ;; do backtracking search of generated arb-arity tree of boards
          (define (fn-for-bd bd)
            (if (solved? bd)
                bd
                (fn-for-lobd (next-boards bd))))
          
          (define (fn-for-lobd lobd)
            (cond [(empty? lobd) false]
                  [else
                   (local [(define try (fn-for-bd (first lobd)))]
                     (if (not (false? try))
                         try
                         (fn-for-lobd (rest lobd))))]))
          
          
          ;; Board -> Boolean
          ;; Produce true if board has N queens.
          ;; ASSUMPTION: Board is valid.
          (define (solved? bd) (= (length bd) N))

          
          ;; Board -> (listof Board)
          ;; produce next valid boards by adding a queen at every position 
          ;; that does not attack an existing queen
          ;; (@template fn-composition use-abstract-fn)
          (define (next-boards bd)
            (map (lambda (p2) (cons p2 bd))
                 (filter (lambda (p2)
                           (not-attacks-existing-queen? p2 bd))
                         (build-list (sqr N) identity)))) ;all positions

          (define (not-attacks-existing-queen? p bd)
            (andmap (lambda (existing-queen)
                      (not (attack? existing-queen p)))
                    bd))
          
          
          ;; Board -> (listof Board)
          ;; produce next valid boards by adding a queen at every new position 
          ;;   - comes after all existing queen positions (not required)
          ;;   - does not attack any existing queens
          ;; (@template encapsulated fn-composition use-abstract-fn)
          ;(define (next-boards bd)
          ;  (local [(define max-so-far (foldr max -1 bd))]       ;not required
          ;            (map (lambda (p2) (cons p2 bd)) 
          ;                 (filter (lambda (p2)
          ;                           (and (> p2 max-so-far)      ;not required
          ;                                (andmap (lambda (p1)     
          ;                                          (not (attack? p2 p1)))
          ;                                        bd)))
          ;                         (build-list (sqr N) identity)))))
                    
          
          ;; Position Position -> Boolean
          ;; produce true if queens at position a and b attack each other
          (define (attack? pa pb)
            (local [(define x1 (pos-x pa))
                    (define y1 (pos-y pa))
                    (define x2 (pos-x pb))
                    (define y2 (pos-y pb))]
              (or (= x1 x2)                           ;same row
                  (= y1 y2)                           ;same column
                  (= (/ (- y2 y1) (- x2 x1))  1)      ;same slope  1 diagonal
                  (= (/ (- y2 y1) (- x2 x1)) -1))))   ;same slope -1 diagonal
          
          
          ;; Pos -> Natural[0, N)
          ;; produce the row or column number for the given position
          (define (pos-x p) (remainder p N))
          (define (pos-y p) (quotient  p N))]
    
    (fn-for-bd empty)))

