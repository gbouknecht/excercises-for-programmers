(ns currency-conversion.open-exchange-rates-client-test
  (:require [clojure.test :refer :all])
  (:require [currency-conversion.exchange-rates-client :refer [get-currencies get-rates]])
  (:require [currency-conversion.open-exchange-rates-client :refer [->OpenExchangeRatesClient]])
  (:require [org.httpkit.fake :refer [with-fake-http]]))

(deftest get-currencies-test
  (let [client (->OpenExchangeRatesClient "my-app-id")
        url "https://openexchangerates.org/api/currencies.json"]
    (testing "Returns nil if request gives error"
      (with-fake-http [url {:error "My error"}]
                      (is (= (get-currencies client) nil))))
    (testing "Returns nil if response status code is not 200"
      (with-fake-http [url {:status 201}]
                      (is (= (get-currencies client) nil))))
    (testing "Returns a map of available currencies if status code is 200"
      (with-fake-http [url {:status 200 :body "{\"AED\": \"United Arab Emirates Dirham\", \"AFN\": \"Afghan Afghani\"}"}]
                      (is (= (get-currencies client) {"AED" "United Arab Emirates Dirham", "AFN" "Afghan Afghani"}))))))

(deftest get-rates-test
  (let [app-id "my-app-id"
        client (->OpenExchangeRatesClient app-id)
        url "https://openexchangerates.org/api/latest.json"]
    (testing "Adds app_id, base and symbols as request parameters"
      (with-fake-http [#(= (:query-params %) {:app_id app-id :base "EUR" :symbols "AED,AFN"}) {:status 200 :body "{}"}]
                      (is (nil? (get-rates client "EUR" ["AED" "AFN"])))))
    (testing "Returns nil if request gives error"
      (with-fake-http [url {:error "My error"}]
                      (is (= (get-rates client "EUR" ["AED" "AFN"]) nil))))
    (testing "Returns nil if response status code is not 200"
      (with-fake-http [url {:status 201}]
                      (is (= (get-rates client "EUR" ["AED" "AFN"]) nil))))
    (testing "Returns a map of rates if status code is 200"
      (with-fake-http [url {:status 200 :body "{\"rates\": {\"AED\": 3.672538, \"AFN\": 66.809999}}"}]
                      (is (= (get-rates client "EUR" ["AED" "AFN"]) {"AED" 3.672538, "AFN" 66.809999}))))))
