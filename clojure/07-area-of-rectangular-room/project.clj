(defproject area-of-rectangular-room "0.1.0-SNAPSHOT"
  :description "Area of a Rectangular Room"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot area-of-rectangular-room.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
