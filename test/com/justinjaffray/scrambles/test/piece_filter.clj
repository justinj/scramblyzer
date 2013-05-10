(ns com.justinjaffray.scrambles.test.piece-filter
  (:use [com.justinjaffray.scrambles.piece-filter])
  (:use [clojure.test]))

(deftest test-edge-oriented
         (is (edge-oriented? "UF"))
         (is (not (edge-oriented? "FU")))
         )

(deftest test-corner-oriented
         (is (corner-oriented? "UFR"))
         (is (not (corner-oriented? "FRU")))
         )
