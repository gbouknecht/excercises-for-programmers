(ns counting-number-of-characters.core
  (:require [clojure.string :refer [blank?]]))

(defn -main []
  (print "What is the input string? ") (flush)
  (let [input (read-line)]
    (if (not (blank? input))
      (println (format "%s has %d characters." input (count input)))
      (recur))))
