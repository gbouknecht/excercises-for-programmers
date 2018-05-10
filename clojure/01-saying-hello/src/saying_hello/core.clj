(ns saying-hello.core
  (:require [clojure.string :refer [blank?]]))

(defn -main []
  (print "What is your name? ") (flush)
  (let [name (read-line)]
    (if (not (blank? name))
      (println (format "Hello, %s, nice to meet you!" name))
      (recur))))
