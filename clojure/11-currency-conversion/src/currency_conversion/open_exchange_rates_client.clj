(ns currency-conversion.open-exchange-rates-client
  (:require [clojure.data.json :as json])
  (:require [clojure.string :refer [join trim]])
  (:require [org.httpkit.client :as http])
  (:require [clojure.tools.logging :as log])
  (:require [currency-conversion.exchange-rates-client :refer [ExchangeRatesClient]]))

(def ^:private api-url "https://openexchangerates.org/api")

(defn- do-get [relative-url query-params]
  (let [url (str api-url "/" relative-url)
        options {:query-params query-params}
        {:keys [error status body]} @(http/get url options)]
    (if error
      (log/errorf "Error getting %s (%s)" url error)
      (if (not= status 200)
        (log/errorf "Error getting %s (HTTP status: %s)" url status)
        (json/read-str body)))))

(deftype OpenExchangeRatesClient [app-id]
  ExchangeRatesClient
  (get-currencies [_]
    (do-get "currencies.json" nil))
  (get-rates [_ base-currency-code currency-codes]
    (get (do-get "latest.json" {:app_id app-id :base base-currency-code :symbols (join "," currency-codes)}) "rates")))
