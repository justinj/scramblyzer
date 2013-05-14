(ns scramblyzer.piece-filter
  (:use [scramblyzer.reid-parser])
  )

(def oriented-edges 
  #{"UF" "UR" "UB" "UL" "DF" "DR" "DB" "DL" "FR" "FL" "BR" "BL"})

(def oriented-corners
  #{"UFR" "URB" "UBL" "ULF" "DRF" "DFL" "DLB" "DBR"})

(def cross-edges
  #{"DR" "DB" "DL" "DF"})

(defn- all-twists
  "Produces all the twists of a piece,
  e.g, FU => (FU UF)"
  [piece]
  (let [amount (.length piece)]
    (take amount
          (iterate #(twist % 1) 
                   piece))))

(defn edge-oriented?  [edge]
  "Whether or not the edge is oriented"
  (oriented-edges edge))

(defn corner-oriented? [corner]
  "Whether or not the corner is oriented"
  (oriented-corners corner))

(defn cross-edge? [edge]
  "Whether the edge is a cross edge"
  (some cross-edges (all-twists edge)))
