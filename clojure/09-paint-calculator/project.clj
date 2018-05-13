(defproject paint-calculator "0.1.0-SNAPSHOT"
  :description "Paint Calculator"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot paint-calculator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
