(ns tip-calculator.core
  (:require [tip-calculator.ui :refer :all]))

(defn -main []
  (ask-and-calculate))
