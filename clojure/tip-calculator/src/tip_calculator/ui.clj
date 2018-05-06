(ns tip-calculator.ui
  (:require [tip-calculator.calculator :refer :all]))

(defn- ask-and-parse [question validation-re validation-message]
  (let [parse #(if (re-matches validation-re %1) (BigDecimal. ^String %1))]
    (loop []
      (do
        (print (format "%s " question)) (flush)
        (or
          (parse (read-line))
          (do (println validation-message) (recur)))))))

(defn- bill-amount [] (ask-and-parse "What is the bill amount?" #"^\d+(.\d+)?$" "Enter a valid amount!"))
(defn- tip-rate [] (ask-and-parse "What is the tip rate?" #"^\d+$" "Enter a valid rate!"))

(defn ask-and-calculate []
  (let [{tip :tip total :total} (tip-and-total (bill-amount) (tip-rate))]
    (println (format "Tip: $%s" (.toPlainString tip)))
    (println (format "Total: $%s" (.toPlainString total)))))
