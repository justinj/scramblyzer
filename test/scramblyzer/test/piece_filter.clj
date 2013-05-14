(ns scramblyzer.test.piece-filter
  (:use [scramblyzer.piece-filter])
  (:use [clojure.test]))

(deftest test-edge-oriented
         (is (edge-oriented? "UF"))
         (is (not (edge-oriented? "FU")))
         )

(deftest test-corner-oriented
         (is (corner-oriented? "UFR"))
         (is (not (corner-oriented? "FRU")))
         )

(deftest test-cross-edge
  (is (cross-edge? "DF"))
  (is (cross-edge? "FD"))
  (is (cross-edge? "DL"))
  (is (cross-edge? "LD"))
  (is (cross-edge? "RD"))
  (is (cross-edge? "DR"))
  (is (cross-edge? "DB"))
  (is (cross-edge? "BD"))

  (is (not (cross-edge? "UR")))
  (is (not (cross-edge? "RU")))
  )

