(ns com.justinjaffray.scrambles.test.analyzer
  (:use [com.justinjaffray.scrambles.analyzer])
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
