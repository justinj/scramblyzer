(ns com.justinjaffray.scrambles.piece-filter)

(def oriented-edges 
  #{"UF" "UR" "UB" "UL" "DF" "DR" "DB" "DL" "FR" "FL" "BR" "BL"})

(def oriented-corners
  #{"UFR" "URB" "UBL" "ULF" "DRF" "DFL" "DLB" "DBR"})

(defn edge-oriented?  [edge]
  "Whether or not the edge is oriented"
  (contains? oriented-edges edge))

(defn corner-oriented? [corner]
  "Whether or not the corner is oriented"
  (contains? oriented-corners corner))
