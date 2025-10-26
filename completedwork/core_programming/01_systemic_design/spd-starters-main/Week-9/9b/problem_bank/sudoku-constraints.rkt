;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname sudoku-constraints) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
(require spd/tags)
(require racket/function);for compose and curry

;;
;; Sudoku solver with constraint sets
;;
;; This is an extended version of the Sudoku solver that uses a technique
;; called constraint sets to speed up the search.  As always, start by
;; understanding the data definitions, then look at find-blank and next-boards.
;; That will highlight the differences.
;;
;; In Sudoku, the board is a 9x9 grid of SQUARES.
;; There are 9 ROWS and 9 COLUMNS, there are also 9
;; 3x3 BOXES.  Rows, columns and boxes are all UNITs.
;; So there are 27 units.
;;
;; The idea of the game is to fill each square with
;; a Natural[1, 9] such that no unit contains a duplicate
;; number.
;;


;; Data definitions:

(@HtDD Val)
;; Val is Natural
;; interp. value in a sudoku square, in [1, 9]

(@HtDD CSet)
;; CSet is (listof Val)
;; interp. a constraint set is all the values a square can still
;; have. Squares start with a constraint set of all 9 values.

(@HtDD ValOrCSet)
;; ValOrCSet is one of: Val   CSet
;; interp.  Contents of a board cell, value or constraint set if no value yet.

(@HtDD Board)
;; Board is (listof ValOrCSet)   that is 81 elements long
;; interp.
;;  Visually a board is a 9x9 array of squares, where each square
;;  has a row and column number (r, c).  But we represent it as a
;;  single flat list, in which the rows are layed out one after
;;  another in a linear fashion. (See interp. of Pos below for how
;;  we convert between (r, c) and position in a board.

(@HtDD Pos)
;; Pos is Natural[0, 80]
;; interp.
;;  the position of a square on the board, if the position is p, then
;;    - the row is (quotient p 9)
;;    - the column is (remainder p 9)

(define (pos->r   p)   (quotient  p 9))
(define (pos->c   p)   (remainder p 9))
(define (r-c->pos r c) (+ (* r 9) c))

(@HtDD Unit)
;; Unit is (listof Pos) of length 9
;; interp. 
;;  The position of every square in a unit. There are
;;  27 of these for the 9 rows, 9 columns and 9 boxes.


;; Constants:

(define ALL-VALS (list 1 2 3 4 5 6 7 8 9))

(define B ALL-VALS) ;B stands for blank


(define BD1 
  (list B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B))

(define BD2 
  (list 1 2 3 4 5 6 7 8 9  ;Note that these boards don't
        B B B B B B B B B  ;have the appropriately reduced
        B B B B B B B B B  ;constraint sets. They have to
        B B B B B B B B B  ;be given to preload-board first.
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B
        B B B B B B B B B))

(define BD3 
  (list 1 B B B B B B B B
        2 B B B B B B B B
        3 B B B B B B B B
        4 B B B B B B B B
        5 B B B B B B B B
        6 B B B B B B B B
        7 B B B B B B B B
        8 B B B B B B B B
        9 B B B B B B B B))

(define BD4                ;easy, never backtracks
  (list 2 7 4 B 9 1 B B 5
        1 B B 5 B B B 9 B
        6 B B B B 3 2 8 B
        B B 1 9 B B B B 8
        B B 5 1 B B 6 B B
        7 B B B 8 B B B 3
        4 B 2 B B B B B 9
        B B B B B B B 7 B
        8 B B 3 4 9 B B B))

(define BD4s               ;solution to 4
  (list 2 7 4 8 9 1 3 6 5
        1 3 8 5 2 6 4 9 7
        6 5 9 4 7 3 2 8 1
        3 2 1 9 6 4 7 5 8
        9 8 5 1 3 7 6 4 2
        7 4 6 2 8 5 9 1 3
        4 6 2 7 5 8 1 3 9
        5 9 3 6 1 2 8 7 4
        8 1 7 3 4 9 5 2 6))

(define BD5                ;hard
  (list 5 B B B B 4 B 7 B
        B 1 B B 5 B 6 B B
        B B 4 9 B B B B B
        B 9 B B B 7 5 B B
        1 8 B 2 B B B B B 
        B B B B B 6 B B B 
        B B 3 B B B B B 8
        B 6 B B 8 B B B 9
        B B 8 B 7 B B 3 1))

(define BD5s               ;solution to 5
  (list 5 3 9 1 6 4 8 7 2
        8 1 2 7 5 3 6 9 4
        6 7 4 9 2 8 3 1 5
        2 9 6 4 1 7 5 8 3
        1 8 7 2 3 5 9 4 6
        3 4 5 8 9 6 1 2 7
        9 2 3 5 4 1 7 6 8
        7 6 1 3 8 2 4 5 9
        4 5 8 6 7 9 2 3 1))

(define BD6                ;hardest ever? (Dr Arto Inkala)
  (list B B 5 3 B B B B B 
        8 B B B B B B 2 B
        B 7 B B 1 B 5 B B 
        4 B B B B 5 3 B B
        B 1 B B 7 B B B 6
        B B 3 2 B B B 8 B
        B 6 B 5 B B B B 9
        B B 4 B B B B 3 B
        B B B B B 9 7 B B))




;; Positions of all the rows, columns and boxes:

(define BOARD (build-list 81 identity))

(define ROWS
  (build-list 9 (λ (r) (build-list 9 (curry  r-c->pos r)))))

(define COLS
  (build-list 9 (λ (c) (build-list 9 (curryr r-c->pos c)))))

(define BOXES
  (local [(define (one-box r0 c0)
            (build-list 9
                        (λ (i)
                          (one-square r0 c0 (quotient i 3) (remainder i 3)))))
          (define (one-square r0 c0 r c)
            (r-c->pos (+ (* r0 3) r)
                      (+ (* c0 3) c)))]
    (build-list 9 (λ (i) (one-box (quotient i 3) (remainder i 3))))))

(define UNITS (append ROWS COLS BOXES))




;; Functions:


(@signature Board -> Board or false)
;; produce solution for bd; or false if bd is unsolvable
;; Assumption: bd is already valid
(check-expect (solve BD4) BD4s)
(check-expect (solve BD5) BD5s)

(@template genrec arb-tree encapsulated)           

(define (solve bd)
  ;;Termination argument:
  ;; trivial case:  checked board has no blanks
  ;; reduction step: fill blank with all possible valid values
  ;; argument: the reduction step fills the board by one, so
  ;;           eventually the board will be full. 
  (local [(define (solve/one bd)
            (if (solved? bd)
                bd 
                (solve/list (next-boards bd))))
          
          (define (solve/list lobd)
            (cond [(empty? lobd) false]
                  [else
                   (local [(define try (solve/one (first lobd)))]
                     (if (not (false? try))
                         try
                         (solve/list (rest lobd))))]))
          
          (define (next-boards bd)
            (local [(define blank (find-blank bd))  ;position of blank
                    (define cset  (bref bd blank))] ;cset of blank
              (map (curry bsubst bd blank) cset)))] ;valid boards from cset
            
    (solve/one (setup-csets bd))))

;; In the version without constraint propagation, next-boards is:
;;          
;;          (define (next-boards bd)
;;            (local [(define blank (find-blank bd))] ;position of blank
;;              (filter valid-board?                  ;valid next boards
;;                      (map (curry bsubst bd blank) 
;;                           ALL-VALS))))
;;
;; and find-blank just picks the first blank.



(@signature Board -> Boolean)
;; produce true if board has no blank squares
(check-expect (solved? BD1) false)
(check-expect (solved? (list 1 1)) true) ;white box test

(define (solved? bd) (andmap number? bd))


(@signature Board -> Pos)
;; Produce the position of the blank square with the smallest constraint set
;; ASSUMPTION: the board contains at least 1 blank square
(check-expect (find-blank BD1) (r-c->pos 0 0))
(check-expect (find-blank BD2) (r-c->pos 1 0))
(check-expect (find-blank BD3) (r-c->pos 0 1))
(check-expect (find-blank BD4) (r-c->pos 0 3))
(check-expect (find-blank BD5) (r-c->pos 0 1))

(define (find-blank bd)
  ;; Accumulators na la are the position and length of the
  ;; blank with the fewest number of possible values so far.
  (local [(define (scan n vals na la)
            (cond [(empty? vals) na]                  
                  [else
                   (local [(define v (first vals))]                     
                     (if (and (not (number? v))
                              (< (length v) la))
                         (scan (add1 n) (rest vals) n (length v))
                         (scan (add1 n) (rest vals) na la)))]))]
    
    (scan 0 bd 0 10)))

(@signature Board -> Boolean)
;; produce true if no unit on bd contains duplicate values; false otherwise
(check-expect (valid-board? BD1) true)
(check-expect (valid-board? BD2) true)
(check-expect (valid-board? BD3) true)
(check-expect (valid-board? BD4) true)
(check-expect (valid-board? BD5) true)
(check-expect (valid-board? (cons 2 (rest BD2))) false)
(check-expect (valid-board? (cons 2 (rest BD3))) false)
(check-expect (valid-board? (append (list 2 6) (rest (rest BD4)))) false)


(define (valid-board? bd)
  (local [(define (check-units lou)
            (andmap check-unit lou))     
          (define (check-unit u)
            (no-duplicates? (map (curry bref bd) u)))
          (define (no-duplicates? vals)
            (cond [(empty? vals) true]
                  [else
                   (if (and (number? (first vals))
                            (member (first vals) (rest vals)))
                       false
                       (no-duplicates? (rest vals)))]))]
    (check-units UNITS)))




(@signature Board Pos -> ValOrCSet)
;; produce value at given position on board,
(check-expect (bref BD2 (r-c->pos 0 5)) 6)
(check-expect (bref BD3 (r-c->pos 7 0)) 8)

(@template Board add-param)

(define (bref bd p)
  (list-ref bd p))


(@signature Board Pos Val -> Board)
;; produce new board with val at given position
;; bsubst only works to add numbers to a valid board. So in
;; particular you can't change a square that is already a number.
(check-expect (bsubst BD1 (r-c->pos 0 0) 1)
              (local [(define X (rest ALL-VALS))]
                (list 1 X X  X X X  X X X
                      X X X  B B B  B B B
                      X X X  B B B  B B B
                      
                      X B B  B B B  B B B                      
                      X B B  B B B  B B B                      
                      X B B  B B B  B B B 
                      
                      X B B  B B B  B B B
                      X B B  B B B  B B B
                      X B B  B B B  B B B)))

(@template Board encapsulated)

(define (bsubst bd p0 nv) 
  (local [(define (scan p vals)
            (cond [(empty? vals) empty]
                  [else
                   (cons (val-for-p p (first vals))
                         (scan (add1 p) (rest vals)))]))
          
          (define (val-for-p p ov)
            (cond [(number? ov)      ov]
                  [(= p p0)          nv]
                  [(same-unit? p p0) (remove nv ov)]
                  [else ov]))]
    
    (scan 0 bd)))


(@signature Board -> Board)
;; setup the initial constraint sets for a board
(check-expect (setup-csets BD2)
              (local [(define X (list 4 5 6 7 8 9))
                      (define Y (list 1 2 3 7 8 9))
                      (define Z (list 1 2 3 4 5 6))                        
                      (define A (remove 1 ALL-VALS))
                      (define B (remove 2 ALL-VALS))
                      (define C (remove 3 ALL-VALS))
                      (define D (remove 4 ALL-VALS))
                      (define E (remove 5 ALL-VALS))
                      (define F (remove 6 ALL-VALS))
                      (define G (remove 7 ALL-VALS))
                      (define H (remove 8 ALL-VALS))
                      (define I (remove 9 ALL-VALS))]
                
                (list 1 2 3  4 5 6  7 8 9
                      X X X  Y Y Y  Z Z Z
                      X X X  Y Y Y  Z Z Z
                      
                      A B C  D E F  G H I
                      A B C  D E F  G H I
                      A B C  D E F  G H I
                      
                      A B C  D E F  G H I
                      A B C  D E F  G H I
                      A B C  D E F  G H I)))

(@template use-abstract-fn)

(define (setup-csets bd)
  (local [(define (cset-for-square p0)
            (local [(define (scan vals p cset)
                      (cond [(empty? vals) cset]
                            [else
                             (if (same-unit? p p0)
                                 (scan (rest vals)
                                       (add1 p)
                                       (remove (first vals) cset))
                                 (scan (rest vals) (add1 p) cset))]))]
              (scan bd 0 ALL-VALS)))]
    (map (λ (p v)
           (if (number? v)
               v
               (cset-for-square p)))
         BOARD
         bd)))



(@signature Pos Pos -> Boolean)
;; produce true if two positions are in a common unit
(check-expect (same-unit? (r-c->pos 0 1) (r-c->pos 0 6)) true)  ;same row
(check-expect (same-unit? (r-c->pos 3 4) (r-c->pos 5 4)) true)  ;same col
(check-expect (same-unit? (r-c->pos 0 1) (r-c->pos 2 2)) true)  ;UL box
(check-expect (same-unit? (r-c->pos 7 8) (r-c->pos 8 6)) true)  ;LR box
(check-expect (same-unit? (r-c->pos 0 1) (r-c->pos 1 6)) false) ;
(check-expect (same-unit? (r-c->pos 3 4) (r-c->pos 7 5)) false) ;
(check-expect (same-unit? (r-c->pos 0 1) (r-c->pos 7 8)) false) ;

(@template Pos add-param)

(define (same-unit? p p0)
  (local [(define r (pos->r p))
          (define c (pos->c p))                    
          (define r0 (pos->r p0))
          (define c0 (pos->c p0))]
    (or (= r0 r)
        (= c0 c)
        (and (= (quotient r0 3) (quotient r 3))
             (= (quotient c0 3) (quotient c 3))))))



