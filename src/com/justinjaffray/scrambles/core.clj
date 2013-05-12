(ns com.justinjaffray.scrambles.core
  (:use [com.justinjaffray.scrambles.analyzer])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  )

; (def solved-edges "UF UR UB UL DF DR DB DL FR FL BR BL")
; (def solved-corners "UFR URB UBL ULF DRF DFL DLB DBR")
; (def solved-state (acube.format.ReidParser/parse 
;                     (str solved-edges " " solved-corners))) 

; (def edges "UF UL UB UR DF DR DB DL FR FL BR BL")
; (def corners "URB UFR UBL ULF DRF DFL DLB DBR")
; (def tperm (acube.format.ReidParser/parse 
;                     (str edges " " corners))) 

; (defn -main [] (println (.solveOptimal tperm
;                            acube.Metric/FACE
;                            acube.Turn/valueSet
;                            20
;                            false
;                            (new acube.console.ConsoleReporter))))

(def analyzing-function num-oriented-edges)

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

(def number-of-scramble-lengths 10)

(def scramble-lists
  (map scrambles-of-length (range 0 number-of-scramble-lengths)))

(defn- analyze-scrambles
  [metric-function scrambles]
  "Analyzes a list of scrambles for a specific function"
  (average
    (map metric-function
         scrambles))
  )

(defn- analyze-lists
  [lists]
  (for [scrambles lists]
    (analyze-scrambles analyzing-function scrambles)))

(def filename "data/results.csv")

(defn -main []
    (spit filename
          (with-out-str
            (write-csv *out*
                       (map list
                            (range)
                            (analyze-lists scramble-lists))))))
