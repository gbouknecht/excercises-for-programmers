(ns tip-calculator.calculator
  (:import (java.math RoundingMode)))

(defn tip-and-total [bill-amount tip-rate]
  (let [set-scale #(.setScale %1 2 RoundingMode/HALF_UP)
        tip (set-scale (* bill-amount (/ tip-rate 100M)))
        total (set-scale (+ bill-amount tip))]
    {:tip tip :total total}))
