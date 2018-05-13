(ns area-of-rectangular-room.calculator-test
  (:require [clojure.test :refer :all])
  (:require [area-of-rectangular-room.calculator :refer :all]))

(deftest area-test
  (are [x y] (= x y)
             0.0 (area 0.0 0.0)
             300.0 (area 15.0 20.0)))

(deftest convert-area-test
  (are [x y] (= x y)
             0.0 (convert-area 0.0 {:from :feet :to :meters})
             0.0 (convert-area 0.0 {:from :meters :to :feet})
             27.870912 (convert-area 300.0 {:from :feet :to :meters})
             300.0 (convert-area 27.870912 {:from :meters :to :feet})))
