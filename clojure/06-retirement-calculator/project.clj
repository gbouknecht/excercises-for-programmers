(defproject retirement-calculator "0.1.0-SNAPSHOT"
  :description "Retirement Calculator"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot retirement-calculator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
