(ns com.justinjaffray.scrambles.test.reid-parser
  (:use [com.justinjaffray.scrambles.reid-parser])
  (:use [clojure.test]))

(deftest test-expand-scramble
         (is (= "R"
                (expand-scramble "R")))
         (is (= "R R"
                (expand-scramble "R2")))
         (is (= "R R R"
                (expand-scramble "R'")))
         (is (= "R R U U U"
                (expand-scramble "R2 U'")))
         )

(deftest test-empty
   (is (= "UF UR UB UL DF DR DB DL FR FL BR BL UFR URB UBL ULF DRF DFL DLB DBR"
          (scramble-to-reid ""))))

(deftest test-single-move
   (is (= "UR UB UL UF DF DR DB DL FR FL BR BL URB UBL ULF UFR DRF DFL DLB DBR"
          (scramble-to-reid "U")))
   (is (= "UF FR UB UL DF BR DB DL DR FL UR BL FDR FRU UBL ULF BRD DFL DLB BUR"
          (scramble-to-reid "R")))
   (is (= "LF UR UB UL RF DR DB DL FU FD BR BL LFU URB UBL LDF RUF RFD DLB DBR"
          (scramble-to-reid "F")))
   (is (= "UF UR UB UL DL DF DR DB FR FL BR BL UFR URB UBL ULF DFL DLB DBR DRF"
          (scramble-to-reid "D")))
   (is (= "UF UR RB UL DF DR LB DL FR FL BD BU UFR RDB RBU ULF DRF DFL LUB LBD"
          (scramble-to-reid "B")))
   (is (= "UF UR UB BL DF DR DB FL FR UL BR DL UFR URB BDL BLU DRF FUL FLD DBR"
          (scramble-to-reid "L")))
         )

(deftest test-tperm
         (is (= "UF UL UB UR DF DR DB DL FR FL BR BL URB UFR UBL ULF DRF DFL DLB DBR"
                (scramble-to-reid "R U R' U' R' F R2 U' R' U' R U R' F'")))
         )

(deftest test-orient
  (is (= ["UF" "FU"]
         (orient ["UF" "UF"]
                 [0 1])))
   )

(deftest test-permute
  (is (= [1 0 2]
         (permute [0 1 2] [1 0 2])))
  (is (= [1 2 0]
         (permute [2 1 0] [1 0 2])))
  )

(deftest test-twist
  (is (= "FU"
         (twist "UF" 1)))
  (is (= "UF"
         (twist "FU" 1)))
  (is (= "RUF"
         (twist "FRU" 1)))
  (is (= "UFR"
         (twist "FRU" -1)))
   )
