(ns retirement-calculator.core
  (:require [clojure.string :refer [blank?]])
  (:import (java.time LocalDate)))

(defn- parse-age [s]
  (if-let [age (try (Integer/parseInt s) (catch Exception _ nil))]
    (if (>= age 0) age)))

(defn- ask-age [question]
  (print question) (flush)
  (or (parse-age (read-line)) (recur question)))

(defn -main []
  (let [current-age (ask-age "What is your current age? ")
        retirement-age (ask-age "At what age would you like to retire? ")
        years-left (- retirement-age current-age)
        current-year (.getYear (LocalDate/now))
        retirement-year (+ current-year years-left)]
    (if (pos? years-left)
      (println (format "You have %d years left until you can retire.\nIt's %d, so you can retire in %d." years-left current-year retirement-year))
      (println (format "You are already retired.")))))
