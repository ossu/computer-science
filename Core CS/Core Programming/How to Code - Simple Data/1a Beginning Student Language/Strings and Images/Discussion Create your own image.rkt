;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Discussion Create your own image|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)




(beside
       (square 50 "outline" "red")
       (above
             (square 30 "outline" "red")
             (beside (above (square 10 "outline" "red")
                            (square 10 "outline" "red"))
                     (square 20 "outline" "red"))))
       