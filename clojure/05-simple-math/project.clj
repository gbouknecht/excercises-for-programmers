(defproject simple-math "0.1.0-SNAPSHOT"
  :description "Simple Math"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot simple-math.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
