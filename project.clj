(defproject scrambles "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [acube/acube "4.0.0"]
                 ]
  :main com.justinjaffray.scrambles.core)
