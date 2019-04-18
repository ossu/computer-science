;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname fibRectangleGenerator) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

(define (fib n)
        (cond [(< n 1) 0]
              [(= n 1) 1]
              [else (+ (fib (- n 1)) (fib (- n 2)))]))

(define SCALER 2)
(define COLOR "red")
(define TEXTURE "outline")

(define (_addFn index)
        (cond [(< index 2) (square (* (fib 1) SCALER) TEXTURE COLOR)]
              [else (beside
                            (square (* (fib index) SCALER) TEXTURE COLOR)
                            (rotate -90 (_addFn (- index 1))))]))
(overlay (_addFn 1)
(_addFn 2)
(_addFn 3)
(_addFn 4)
(_addFn 5)
(_addFn 6)
(_addFn 7)
(_addFn 8)
(_addFn 9)
(_addFn 10)
(_addFn 11)
(_addFn 12))



