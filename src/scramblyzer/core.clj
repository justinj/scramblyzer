(ns scramblyzer.core
  (:use [scramblyzer.analyzer])
  (:use [scramblyzer.stats])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  (:use [incanter core stats charts])
  (:gen-class))

(defn -main []
  (let [[lengths results] (tabular-data num-oriented-edges)]
    (view (scatter-plot lengths results))))
