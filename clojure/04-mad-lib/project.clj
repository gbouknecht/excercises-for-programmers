(defproject mad-lib "0.1.0-SNAPSHOT"
  :description "Mad Lib"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot mad-lib.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
