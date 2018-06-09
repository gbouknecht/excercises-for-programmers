(ns currency-conversion.converter)

(defn exchange-rate [base-currency-code exchange-rates from-currency-code to-currency-code]
  (let [from-rate (exchange-rates from-currency-code)
        to-rate (exchange-rates to-currency-code)]
    (cond
      (= from-currency-code to-currency-code) 1
      (= from-currency-code base-currency-code) to-rate
      (= to-currency-code base-currency-code) (and from-rate (/ from-rate))
      :else (and from-rate to-rate (/ to-rate from-rate)))))

(defn convert-amount [base-currency-code exchange-rates from-currency-code to-currency-code amount]
  (let [rate (exchange-rate base-currency-code exchange-rates from-currency-code to-currency-code)]
    (and rate (* amount rate))))
