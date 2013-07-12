(ns scramblyzer.core
  (:use [scramblyzer.analyzer])
  (:use [scramblyzer.stats])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  (:use [incanter core stats charts])
  (:gen-class))

(def args {:metric-function misoriented-pieces
           :scramble-count 100
           :max-length 20
           :scramble-dir "scrambles/6gen"})


; (defn -main []
;   (let [[lengths results] (tabular-data args)]
;     (view (scatter-plot results lengths))))

(defn -main []
  (let [results (analyze-file misoriented-pieces "scrambles/random-state/scrambles")]
    (println (str "mean: " (mean results)))
    (println (str "sd: " (sd results)))
    )
  )
