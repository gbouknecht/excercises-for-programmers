(defproject counting-number-of-characters "0.1.0-SNAPSHOT"
  :description "Counting Number of Characters"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot counting-number-of-characters.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
