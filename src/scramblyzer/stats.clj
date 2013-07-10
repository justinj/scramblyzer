(ns scramblyzer.stats
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv]))


(defn- average
  [values]
  (float (/ (reduce + values) (count values))))

(defn- scrambles-of-length
  [length amount scramble-dir]
  (take amount
        (split 
          (slurp (str scramble-dir "/len_" length))
          #"\n"
          -1)))

(defn- analyze-scrambles
  [metric-function scrambles]
  "Provides the average value of applying the metric-function
  to each scramble in the list"
  (average
    (map metric-function
         scrambles)))

(defn- analyze-lists
  [lists metric-function]
  (for [scrambles lists]
    (analyze-scrambles metric-function scrambles)))

(defn- add-header
  [table]
  "Add the header to the table"
  (concat [["length" "stat"]]
          [table]))

(defn- get-scramble-lists
  [scramble-count max-length scramble-dir]
  (map #(scrambles-of-length % scramble-count scramble-dir) (range max-length))
  )

(defn tabular-data
  [{:keys [metric-function
           scramble-count
           max-length
           scramble-dir]}]
  (let [scramble-lists (get-scramble-lists scramble-count 
                                           max-length 
                                           scramble-dir)
        results (analyze-lists scramble-lists metric-function)]
    [(range (count results)) results]))
