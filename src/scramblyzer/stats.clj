(ns scramblyzer.stats
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv]))

(def filename "data/results.csv")
(def number-of-scramble-lengths 50)
(def scrambles-to-use 1000)

(defn- average
  [values]
  (float (/ (reduce + values) (count values))))

(defn- scrambles-of-length
  [length]
  (take scrambles-to-use
  (split 
    (slurp (str "data/scrambles_len_" length))
    #"\n"
    -1)))

(def scramble-lists
  (map scrambles-of-length (range 0 number-of-scramble-lengths)))

(defn- analyze-scrambles
  [metric-function scrambles]
  "Analyzes a list of scrambles for a specific function"
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

(defn get-csv 
  [metric-function]
  (with-out-str
    (write-csv *out*
               (add-header
                 (map list
                      (range)
                      (analyze-lists scramble-lists metric-function))))))

