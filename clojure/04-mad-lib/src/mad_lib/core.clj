(ns mad-lib.core
  (:require [clojure.string :refer [blank?]]))

(defn ask [question]
  (print question) (flush)
  (let [answer (read-line)]
    (if (blank? answer) (recur question) answer)))

(defn -main []
  (let [noun (ask "Enter a noun: ")
        verb (ask "Enter a verb: ")
        adjective (ask "Enter an adjective: ")
        adverb (ask "Enter an adverb: ")]
    (println (format "Do you %s your %s %s %s? That's hilarious!" verb adjective noun adverb))))
