(defproject currency-conversion "0.1.0-SNAPSHOT"
  :description "Currency Conversion"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.logging "0.4.1"]
                 [com.stuartsierra/log.dev "0.2.0"]
                 [http-kit "2.3.0"]]
  :exclusions [commons-logging
               log4j
               org.apache.logging.log4j/log4j
               org.slf4j/simple
               org.slf4j/slf4j-jcl
               org.slf4j/slf4j-nop
               org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-log4j13]
  :main ^:skip-aot currency-conversion.core
  :target-path "target/%s"
  :profiles {:test    {:dependencies [[http-kit.fake "0.2.2"]]}
             :uberjar {:aot :all}})
