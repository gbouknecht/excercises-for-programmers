(defproject saying-hello "0.1.0-SNAPSHOT"
  :description "Saying Hello"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot saying-hello.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
