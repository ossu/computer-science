;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Objects) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; An Object is [Itself Name Params -> Value]
;; A Name is a symbol
;; Params is [Listof Value]
;; Itself is the object on which the method is being invoked
 
(define object
  (λ(this prop args)
    (error prop "No such property found")))
(define (make-pos x y)
  (local [(define super object)]
    (λ(this prop args)
      (cond
        [(symbol=? prop 'x) x]
        [(symbol=? prop 'y) y]
        [(symbol=? prop 'dist-to-0)
         (sqrt (+ (sqr x) (sqr y)))]
        [(symbol=? prop 'closer-than)
         (local [(define that (first args))]
           (< (dot this 'dist-to-0 '()) (dot that 'dist-to-0 '())))]
        ;; NEW:
        [else (super this prop args)]))))
(define (make-3d-pos x y z)
  (local [(define super (make-pos x y))]
    (λ(this prop args)
      (cond
        [(symbol=? prop 'z) z]
        [(symbol=? prop 'dist-to-0)
         (sqrt (+ (sqr x) (sqr y) (sqr z)))]
        ;; NEW:
        [else (super this prop args)]))))
(define (dot obj prop args)
  ;; Passes the object to itself
  (obj obj prop args))

(define my-pos1 (make-pos 3 4))
(define my-pos2 (make-pos 6 8))
(define my-pos3 (make-3d-pos 3 4 12))
 
(check-expect (dot my-pos1 'x '-) 3)
(check-expect (dot my-pos2 'y '-) 8)
(check-error (dot my-pos2 'z '-) "z: No such property found")

(check-expect (dot my-pos3 'z '-) 12)
(check-error (dot my-pos3 'w '-) "w: No such property found")

(check-expect (dot my-pos1 'dist-to-0 '-) 5)
(check-expect (dot my-pos2 'dist-to-0 '-) 10)
(check-expect (dot my-pos3 'dist-to-0 '-) 13)

;; Test 1: (3,4) is closer to the origin than (6,8)
(check-expect (dot my-pos1 'closer-than (list my-pos2)) true)
;; Test 2: (6,8) is not closer to the origin than (3,4)
(check-expect (dot my-pos2 'closer-than (list my-pos1)) false)
;; Test 3: (6,8) is closer to the origin than (3,4,12)
(check-expect (dot my-pos2 'closer-than (list my-pos3)) true)
;; Test 4: (3,4,12) is not closer to the origin than (6,8)
(check-expect (dot my-pos3 'closer-than (list my-pos2)) false)