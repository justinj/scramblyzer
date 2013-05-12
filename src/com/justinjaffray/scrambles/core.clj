(ns com.justinjaffray.scrambles.core
  (:use [com.justinjaffray.scrambles.analyzer])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  )

(def analyzing-function num-oriented-corners)

(defn- average
  [values]
  (float (/ (reduce + values) (count values)))
  )

(defn- scrambles-of-length
  [length]
  (split 
    (slurp (str "data/scrambles_len_" length))
    #"\n"
    -1))

(def number-of-scramble-lengths 50)

(def scramble-lists
  (map scrambles-of-length (range 0 number-of-scramble-lengths)))

(defn- analyze-scrambles
  [metric-function scrambles]
  "Analyzes a list of scrambles for a specific function"
  (average
    (map metric-function
         scrambles)))

(defn- analyze-lists
  [lists]
  (for [scrambles lists]
    (analyze-scrambles analyzing-function scrambles)))

(def filename "data/results.csv")

(defn -main []
    (spit filename
          (with-out-str
            (write-csv *out*
                       (concat [["length" "stat"]]
                       (map list
                            (range)
                            (analyze-lists scramble-lists)))))))
