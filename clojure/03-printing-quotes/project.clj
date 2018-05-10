(defproject printing-quotes "0.1.0-SNAPSHOT"
  :description "Printing Quotes"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot printing-quotes.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
