(ns currency-conversion.core
  (:require [clojure.string :refer [trim]])
  (:require [currency-conversion.open-exchange-rates-client :refer [->OpenExchangeRatesClient]])
  (:require [currency-conversion.gui :refer [show]])
  (:import (java.io File)))

(def ^:private app-id (let [user-home (System/getProperty "user.home")
                            app-id-file (File. user-home ".open-exchange-rates-app-id")]
                        (if (.exists app-id-file)
                          (trim (slurp app-id-file))
                          (throw (RuntimeException. (format "Missing %s" (.toPath app-id-file)))))))

(defn -main []
  (show (->OpenExchangeRatesClient app-id)))
