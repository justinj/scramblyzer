(ns scramblyzer.stats
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv]))

(def number-of-scramble-lengths 10)
(def scrambles-to-use 1)

(defn- average
  [values]
  (float (/ (reduce + values) (count values))))

(defn- scrambles-of-length
  [length]
  (take scrambles-to-use
        (split 
          (slurp (str "scrambles/6gen/len_" length))
          #"\n"
          -1)))

(def scramble-lists
  (map scrambles-of-length (range 0 number-of-scramble-lengths)))

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
          table))
(defn- tabular-data
  [metric-function]
  (add-header
    (map list
         (range)
         (analyze-lists scramble-lists metric-function))))

(defn get-csv 
  [metric-function]
  (with-out-str
    (write-csv 
      *out*
      (tabular-data metric-function))))
