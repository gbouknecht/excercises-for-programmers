(ns currency-conversion.exchange-rates-client)

(defprotocol ExchangeRatesClient
  (get-currencies [this]
    "Returns a map of available currencies, mapping the currency code to the
    full name of the currency. For example: {\"AED\" \"United Arab Emirates Dirham\",
    \"AFN\" \"Afghan Afghani\"}.
    Returns nil if the currencies cannot be retrieved successfully.")
  (get-exchange-rates [this base-currency-code currency-codes]
    "Returns a map of exchange rates, mapping the currency code on the rate the
    base currency is exchanged for. For example: {\"AED\" 3.672538, \"AFN\" 66.809999}.
    Returns nil if the rates cannot be retrieved successfully."))
