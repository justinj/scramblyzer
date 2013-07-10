(ns scramblyzer.core
  (:use [scramblyzer.analyzer])
  (:use [scramblyzer.stats])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  (:use [incanter core stats charts])
  (:gen-class))

(def args {:metric-function num-oriented-edges
           :scramble-count 10
           :max-length 10
           :scramble-dir "scrambles/6gen"})


(defn -main []
  (let [[lengths results] (tabular-data args)]
    (view (scatter-plot lengths results))))
