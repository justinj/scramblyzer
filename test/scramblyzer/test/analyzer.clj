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
  (is (= 0
         (cross-dist "")))
  (is (= 1
         (cross-dist "R")))
  (is (= 1
         (cross-dist "L2 F2 L2")))
  (is (= 1
         (cross-dist "F")))
         )
