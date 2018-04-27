(ns tip-calculator.ui-test
  (:require [clojure.test :refer :all])
  (:require [clojure.string :refer [includes?]])
  (:require [tip-calculator.ui :refer :all])
  (:import (java.io BufferedReader StringReader StringWriter)))

(defn- ask-and-calculate-with-input [& inputs]
  (let [formatted-input (apply str (map #(format "%s\n" %1) inputs))]
    (binding [*in* (-> formatted-input StringReader. BufferedReader.) *out* (StringWriter.)]
      (do (ask-and-calculate) (str *out*)))))

(defn- includes-tip-and-total? [s tip total]
  (and
    (includes? s (format "Tip: $%s" tip))
    (includes? s (format "Total: $%s" total))))

(deftest ask-and-calculate-test
  (let [amount-validation-message "Enter a valid amount!"
        rate-validation-message "Enter a valid rate!"]
    (testing "Valid input"
      (are [tip total bill-amount tip-rate] (let [result (ask-and-calculate-with-input bill-amount tip-rate)]
                                              (and
                                                (not (includes? result amount-validation-message))
                                                (not (includes? result rate-validation-message))
                                                (includes-tip-and-total? result tip total)))
                                            "0.00" "0.00" "0" "0"
                                            "0.00" "1.00" "1" "0"
                                            "0.05" "1.05" "1" "5"
                                            "0.05" "1.05" "1.0" "5"
                                            "1.69" "12.94" "11.25" "15"))
    (testing "Invalid bill amount input"
      (are [invalid-bill-amount] (let [result (ask-and-calculate-with-input invalid-bill-amount "1" "5")]
                           (and
                             (includes? result amount-validation-message)
                             (not (includes? result rate-validation-message))
                             (includes-tip-and-total? result "0.05" "1.05")))
                         "\n" "-1" "-1.00" "1." "1.0." "1.0.0" ".0" "."))
    (testing "Invalid tip rate input"
      (are [invalid-tip-rate] (let [result (ask-and-calculate-with-input "1" invalid-tip-rate "5")]
                        (and
                          (not (includes? result amount-validation-message))
                          (includes? result rate-validation-message)
                          (includes-tip-and-total? result "0.05" "1.05")))
                      "\n" "-1" "-1.00" "1." "1.0" "1.0.0" ".0" "."))))
