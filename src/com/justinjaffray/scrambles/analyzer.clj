(ns com.justinjaffray.scrambles.analyzer
  (:use [com.justinjaffray.scrambles.reid-parser])
  (:use [com.justinjaffray.scrambles.piece-filter])
  )

(defn num-oriented-corners
  [scramble]
  "Determines the number of oriented corners from a scramble"
  (count
    (filter corner-oriented? 
            (.corners (scramble-to-state scramble)))))

(defn num-oriented-edges
  [scramble]
  "Determines the number of oriented edges from a scramble"
  (count
    (filter edge-oriented? 
            (.edges (scramble-to-state scramble)))))
