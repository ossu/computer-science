;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname ta-solver-submission) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; ta-solver-starter.rkt


;  PROBLEM 1:
;
;  Consider a social network similar to Twitter called Chirper. Each user has a name, a note about
;  whether or not they are a verified user, and follows some number of people.
;
;  Design a data definition for Chirper, including a template that is tail recursive and avoids
;  cycles.
;
;  Then design a function called most-followers which determines which user in a Chirper Network is
;  followed by the most people.
;

(define-struct user (name verified? follows))
;; User is (make-user String Boolean (listof User)
;; interp. the users name, whether the user if verified, and which other users, user follows

(define U0 (make-user "A" false (list (make-user "B" true empty))))
(define U1
  (shared ((-a- (make-user "A" false (list -b-)))
           (-b- (make-user "B" true  (list -a-))))
    -a-))
(define U2
  (shared ((-a- (make-user "A" false (list -b-)))
           (-b- (make-user "B" true  (list -c-)))
           (-c- (make-user "C" false (list -a-))))
    -a-))
(define U3
  (shared ((-a- (make-user "A" false (list -b- -d-)))
           (-b- (make-user "B" true  (list -c- -e-)))
           (-c- (make-user "C" false (list -b-)))
           (-d- (make-user "D" false (list -e-)))
           (-e- (make-user "E" false (list -f- -a-)))
           (-f- (make-user "F" false (list -b-))))
    -a-))

;; template:
;;  tail recursive encapsulate w/ local, worklist, context-preserving
;;  accumulator with what rooms have we already visited in complete
;;  tail recursive traversal so far
#;
(define (fn-for-user u0)
  ;; todo is (listof User); a worklist accumulator
  ;; visited is (listof String; names of all the users visited so far
  (local [(define (fn-for-user u todo visited)
            (cond [(member? (user-name u) visited)
                   (fn-for-lou todo visited)]
                  [else
                   (fn-for-lou (append (user-followers u) todo) (cons (user-name u) visited))]))
          (define (fn-for-lou todo visited)
            (cond [(empty? todo) ...]
                  [else
                   (fn-for-user (first todo) (rest todo) visited)]))]
    (fn-for-user u0 empty empty)))

(define-struct follows (name count))
;; follows is (make-follows String Natural)
;; inpterp. the user name and how many people follow user

;; Users -> String 
;; produce the first user-name found with the most followers in the network of provided user
(check-expect (most-followers U0) "B")
(check-expect (most-followers U1) "A")
(check-expect (most-followers U3) "B")

(define (most-followers u0)
  ;; lof is (listof follows); result accumulator with username and count of followers
  ;; todo is (listof User); a worklist accumulator
  ;; visited is (listof String; names of all the users visited so far
  (local [(define (fn-for-user u lof todo visited)
            (cond [(member? (user-name u) visited)
                   (fn-for-lou
                    (add1-lof lof (user-name u))             ;lof
                    todo
                    visited)]
                  [else
                   (fn-for-lou
                    (cons (make-follows (user-name u) 1) lof) ;lof
                    (append (user-follows u) todo)         ;todo
                    (cons (user-name u) visited))]))         ;visited
          (define (fn-for-lou lof todo visited)
            (cond [(empty? todo) (max-follows lof)]
                  [else
                   (fn-for-user
                    (first todo)                             ;user
                    lof
                    (rest todo)                              ;todo
                    visited)]))
          (define (add1-lof_ lof un acc) 
            (cond [(empty? lof) (error "Missing Entry")] ; Should never happen
                  [(string=? (follows-name (first lof)) un)
                   (append acc (cons (make-follows un (add1 (follows-count (first lof)))) (rest lof)))]
                  [else
                   (add1-lof_ (rest lof) un (cons (first lof) acc))]))
          (define (add1-lof lof un) ; listof User) String -> (listof User); produce lof with 1 follower added to provided user
            (add1-lof_ lof un empty))
          (define (max-follows_ lof name count)
            (cond [(empty? lof) name]
                  [(> (follows-count (first lof)) count)
                   (max-follows_ (rest lof) (follows-name (first lof)) (follows-count (first lof)))]
                  [else
                   (max-follows_ (rest lof) name count)]))
          (define (max-follows lof) ; (listof User)        -> String;        produce name of user with most followers
            (max-follows_ lof "" 0))]
    (fn-for-user u0 empty empty empty)))

;  PROBLEM 2:
;
;  In UBC's version of How to Code, there are often more than 800 students taking
;  the course in any given semester, meaning there are often over 40 Teaching Assistants.
;
;  Designing a schedule for them by hand is hard work - luckily we've learned enough now to write
;  a program to do it for us!
;
;  Below are some data definitions for a simplified version of a TA schedule. There are some
;  number of slots that must be filled, each represented by a natural number. Each TA is
;  available for some of these slots, and has a maximum number of shifts they can work.
;
;  Design a search program that consumes a list of TAs and a list of Slots, and produces one
;  valid schedule where each Slot is assigned to a TA, and no TA is working more than their
;  maximum shifts. If no such schedules exist, produce false.
;
;  You should supplement the given check-expects and remember to follow the recipe!


;; Slot is Natural
;; interp. each TA slot has a number, is the same length, and none overlap
(define-struct ta (name max avail))
;; TA is (make-ta String Natural (listof Slot))
;; interp. the TA's name, number of slots they can work, and slots they're available for
(define SOBA (make-ta "Soba" 2 (list 1 3)))
(define UDON (make-ta "Udon" 1 (list 3 4)))
(define RAMEN (make-ta "Ramen" 1 (list 2)))

(define NOODLE-TAs (list SOBA UDON RAMEN))


(define-struct assignment (name slot))
;; Assignment is (make-assignment TA Slot)
;; interp. the TA is assigned to work the slot

;; Schedule is (listof Assignment)


;; ============================= FUNCTIONS


;; (listof TA) (listof Slot) -> Schedule or false
;; produce valid schedule given TAs and Slots; false if impossible

(check-expect (schedule-tas empty empty) empty)
(check-expect (schedule-tas empty (list 1 2)) false)
(check-expect (schedule-tas (list SOBA) empty) empty)

(check-expect (schedule-tas (list SOBA) (list 1)) (list (make-assignment "Soba" 1)))
(check-expect (schedule-tas (list SOBA) (list 2)) false)
(check-expect (schedule-tas (list SOBA) (list 1 3)) (list (make-assignment "Soba" 3)
                                                          (make-assignment "Soba" 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4))
              (list
               (make-assignment "Udon" 4)
               (make-assignment "Soba" 3)
               (make-assignment "Ramen" 2)
               (make-assignment "Soba" 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4 5)) false)

;(define (schedule-tas tas slots) empty) ;stub

(define (schedule-tas tas0 slots0)
  (local [(define (fill-los lot los schedule)              ; produce a schedule when all slots have been filled or false if not valid solution
            (cond [(empty? los) schedule]
                  [(false? schedule) false]
                  [(empty? lot) false]
                  [else
                   (fill-slot (first lot)
                              (rest lot)
                              (first los)
                              (rest los)
                              schedule
                              empty)]))
          (define (fill-slot ta lot slot los schedule rsf) ; produce a schedule when TA has been assined to slot or false if no TAs are available
            (cond [(ta-available? ta slot)
                   (fill-los (reduce-ta ta lot rsf)
                             los
                             (add-ta ta slot schedule))]
                  [(empty? lot) false]
                  [else
                   (fill-slot (first lot)
                              (rest lot)
                              slot
                              los
                              schedule
                              (cons ta rsf))]))
          (define (ta-available? ta slot)                  ; produce true if ta is available for provided slot
            (member? slot (ta-avail ta)))
          (define (reduce-ta ta lot rsf)                   ; produce listof TAs with the one availability removed from TA
            (append rsf (cons (make-ta (ta-name ta)
                                       (sub1 (ta-max ta))
                                       (ta-avail ta))
                              lot)))
          (define (add-ta ta slot schedule)                ; produce a schedule with TA added to slot on provided schedule
            (cons (make-assignment (ta-name ta) slot) schedule))
          ]
    (fill-los tas0 slots0 empty)))


#;
(define (schedule-tas tas slots)
  (local [(define (fn-for-ta ta tas slots)
            (cond [((...) (... ta))]
                  [else
                   (fn-for-lot (... tas) (... slots))]))
          (define (fn-for-lot tas slots)
            (cond [(empty? tas) (...)]
                  [else
                   (fn-for-ta (first tas) (rest tas) slots)]))
          ])
  (fn-for-lot tas slots))
    
            
