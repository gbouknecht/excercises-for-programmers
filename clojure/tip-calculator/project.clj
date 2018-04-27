(defproject tip-calculator "0.1.0-SNAPSHOT"
  :description "Tip Calculator"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot tip-calculator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
