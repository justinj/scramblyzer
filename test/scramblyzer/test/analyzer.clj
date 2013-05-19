(ns scramblyzer.test.analyzer
  (:use [scramblyzer.analyzer])
  (:use [clojure.test]))

(deftest test-num-oriented-edges
  (is (= 12
         (num-oriented-edges "")))
  (is (= 8
         (num-oriented-edges "F")))
  )

(deftest test-num-oriented-corners
  (is (= 8
         (num-oriented-corners "")))
  (is (= 4
         (num-oriented-corners "R")))
 )

(deftest test-cross-dist
  (is (= 0 (cross-dist "")))
  (is (= 1 (cross-dist "R")))
  (is (= 1 (cross-dist "L2 F2 L2")))
  (is (= 1 (cross-dist "F")))
  )

(deftest test-roux-block-dist
  (is (= 0 (roux-block-dist "")))
  (is (= 1 (roux-block-dist "F")))
  )

(deftest test-corner-dist
  (is (= 0 (corner-dist "")))
  (is (= 1 (corner-dist "R")))
  (is (= 0 (corner-dist "R U' R U R U R U' R' U' R2")))
  )

(deftest test-edge-dist
  (is (= 0 (edge-dist "")))
  (is (= 1 (edge-dist "R")))
  (is (= 0 (edge-dist "R' U R' D2 R U' R' D2 R2")))
  )
