(defproject pizza-party "0.1.0-SNAPSHOT"
  :description "Pizza Party"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot pizza-party.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
