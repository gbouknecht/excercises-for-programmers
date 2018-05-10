(ns tip-calculator.calculator-test
  (:require [clojure.test :refer :all]
            [tip-calculator.calculator :refer :all]))

(deftest tip-and-total-test
  (are [x y] (= x y)
             {:tip 0.00M :total 0.00M} (tip-and-total 0.00M 0)
             {:tip 0.00M :total 1.00M} (tip-and-total 1.00M 0)
             {:tip 0.05M :total 1.05M} (tip-and-total 1.00M 5)
             {:tip 0.06M :total 1.16M} (tip-and-total 1.10M 5)
             {:tip 1.69M :total 12.94M} (tip-and-total 11.25M 15)
             {:tip 21.67M :total 43.34M} (tip-and-total 21.67M 100)))
