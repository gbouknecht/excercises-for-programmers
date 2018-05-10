(ns printing-quotes.core
  (:require [clojure.string :refer [blank?]]))

(defn- ask-for-author []
  (print "Who said it? ") (flush)
  (let [author (read-line)]
    (if (blank? author) (recur) author)))

(defn- ask-for-quotes []
  (loop [quotes []]
    (print "What is the quote? ") (flush)
    (let [quote (read-line)]
      (if (blank? quote)
        quotes
        (let [author (ask-for-author)]
          (recur (conj quotes {:quote quote :author author})))))))

(defn -main []
  (doseq [quote (ask-for-quotes)]
    (println (format "%s says, \"%s\"" (:author quote) (:quote quote)))))
