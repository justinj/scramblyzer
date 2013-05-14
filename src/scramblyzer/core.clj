(ns scramblyzer.core
  (:use [scramblyzer.analyzer])
  (:use [scramblyzer.stats])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  (:gen-class))

(defn -main []
  (println
    (get-csv cross-dist)))
