;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname firstImageLarger) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

;; Image Image -> Boolean
;; produce true if the first image is larger than the second image (larger area)
(check-expect (firstImageLarger? (square 5 "solid" "red") (square 4 "solid" "red")) true)
(check-expect (firstImageLarger? (square 7 "solid" "red") (rectangle 6 8 "solid" "red")) true)
(check-expect (firstImageLarger? (square 4 "solid" "red") (rectangle 2 8 "solid" "red")) false)
(check-expect (firstImageLarger? (rectangle 4 6 "solid" "red") (square 5 "solid" "red")) false)
(check-expect (firstImageLarger? (square 4 "solid" "red") (square 5 "solid" "red")) false)


;(define (firstImageLarger? img1 img2) true)                ;stub

;(define (firstImageLarger? img1 img2)                      ;template
;        (... img1 img2))

(define (firstImageLarger? img1 img2)                       ;code the function body
        (> (* (image-width img1) (image-height img1))
           (* (image-width img2) (image-height img2))))