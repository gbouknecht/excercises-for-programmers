(ns self-checkout.core
  (:require [clojure.string :refer [blank?]])
  (:import (java.math RoundingMode)))

(defn- parse [re ^String s]
  (if (re-matches re s) (BigDecimal. s)))

(defn- ask-price [item-number]
  (print (format "Enter the price of item %d: " item-number)) (flush)
  (let [input (read-line)]
    (if (not (blank? input))
      (let [price (parse #"^\d+(.\d+)?$" input)]
        (or price (recur item-number))))))

(defn- ask-quantity [item-number]
  (print (format "Enter the quantity of item %d: " item-number)) (flush)
  (let [quantity (parse #"^\d+$" (read-line))]
    (or quantity (recur item-number))))

(defn- ask-item [item-number]
  (if-let [price (ask-price item-number)]
    (let [quantity (ask-quantity item-number)]
      {:price price :quantity quantity})))

(defn- ask-items []
  (loop [items []]
    (let [item (ask-item (inc (count items)))]
      (if item (recur (conj items item)) items))))

(def ^:private tax-rate 5.5M)

(defn- subtotal-tax-total [items]
  (let [subtotal (apply + (map #(* (:price %1) (:quantity %1)) items))
        tax (* subtotal (/ tax-rate 100M))
        total (+ subtotal tax)]
    {:subtotal subtotal :tax tax :total total}))

(defn -main []
  (let [items (ask-items)]
    (if (not-empty items)
      (let [subtotal-tax-total (subtotal-tax-total items)
            format-amount #(-> %1 (.setScale 2 RoundingMode/HALF_UP) (.toPlainString))]
        (println (format "Subtotal: $%s" (format-amount (:subtotal subtotal-tax-total))))
        (println (format "Tax: $%s" (format-amount (:tax subtotal-tax-total))))
        (println (format "Total: $%s" (format-amount (:total subtotal-tax-total))))))))
