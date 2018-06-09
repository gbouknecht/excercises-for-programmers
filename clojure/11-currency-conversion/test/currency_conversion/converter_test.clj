(ns currency-conversion.converter-test
  (:require [clojure.test :refer :all])
  (:require [currency-conversion.converter :refer :all]))

(deftest exchange-rate-test
  (let [base-currency-code "USD"
        exchange-rates {"EUR" 0.9, "JPY" 89.3}]
    (are [from-currency-code to-currency-code expected-rate]
      (= (exchange-rate base-currency-code exchange-rates from-currency-code to-currency-code) expected-rate)
      "USD" "AED" nil
      "AED" "USD" nil
      "EUR" "AED" nil
      "AED" "EUR" nil
      "AED" "AED" 1
      "USD" "USD" 1
      "EUR" "EUR" 1
      "USD" "EUR" 0.9
      "EUR" "USD" (/ 0.9)
      "EUR" "JPY" (/ 89.3 0.9))))

(deftest convert-amount-test
  (let [base-currency-code "USD"
        exchange-rates {"EUR" 0.9, "JPY" 89.3}]
    (are [from-amount from-currency-code to-amount to-currency-code]
      (= (convert-amount base-currency-code exchange-rates from-currency-code to-currency-code from-amount) to-amount)
      100 "USD" nil "AED"
      100 "AED" nil "USD"
      100 "EUR" nil "AED"
      100 "AED" nil "EUR"
      100 "USD" 100 "USD"
      100 "EUR" 100 "EUR"
      100 "AED" 100 "AED"
      100 "USD" (* 100 0.9) "EUR"
      100 "EUR" (/ 100 0.9) "USD"
      100 "EUR" (* 100 (/ 89.3 0.9)) "JPY")))
