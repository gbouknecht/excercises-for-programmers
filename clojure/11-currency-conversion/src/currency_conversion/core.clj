(ns currency-conversion.core
  (:require [clojure.string :refer [trim]])
  (:require [currency-conversion.exchange-rates-client :refer [get-currencies get-exchange-rates]])
  (:require [currency-conversion.open-exchange-rates-client :refer [->OpenExchangeRatesClient]])
  (:import (java.io File)))

(def ^:private app-id (let [user-home (System/getProperty "user.home")
                            app-id-file (File. user-home ".open-exchange-rates-app-id")]
                        (if (.exists app-id-file)
                          (trim (slurp app-id-file))
                          (throw (RuntimeException. (format "Missing %s" (.toPath app-id-file)))))))

(defn -main []
  (let [client (->OpenExchangeRatesClient app-id)]
    (println (format "Returned currencies: %s" (get-currencies client)))
    (println (format "Returned rates: %s" (get-exchange-rates client "USD" ["EUR", "JPY", "GBP"])))))
