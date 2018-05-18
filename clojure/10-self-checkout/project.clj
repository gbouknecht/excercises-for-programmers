(defproject self-checkout "0.1.0-SNAPSHOT"
  :description "Self-Checkout"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot self-checkout.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
