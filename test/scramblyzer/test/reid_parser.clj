(ns scramblyzer.test.reid-parser
  (:use [scramblyzer.reid-parser])
  (:use [clojure.test]))

(deftest test-empty
   (is (= "UF UR UB UL DF DR DB DL FR FL BR BL UFR URB UBL ULF DRF DFL DLB DBR"
          (scramble->reid ""))))

(deftest test-single-move
   (is (= "UR UB UL UF DF DR DB DL FR FL BR BL URB UBL ULF UFR DRF DFL DLB DBR"
          (scramble->reid "U")))
   (is (= "UF FR UB UL DF BR DB DL DR FL UR BL FDR FRU UBL ULF BRD DFL DLB BUR"
          (scramble->reid "R")))
   (is (= "LF UR UB UL RF DR DB DL FU FD BR BL LFU URB UBL LDF RUF RFD DLB DBR"
          (scramble->reid "F")))
   (is (= "UF UR UB UL DL DF DR DB FR FL BR BL UFR URB UBL ULF DFL DLB DBR DRF"
          (scramble->reid "D")))
   (is (= "UF UR RB UL DF DR LB DL FR FL BD BU UFR RDB RBU ULF DRF DFL LUB LBD"
          (scramble->reid "B")))
   (is (= "UF UR UB BL DF DR DB FL FR UL BR DL UFR URB BDL BLU DRF FUL FLD DBR"
          (scramble->reid "L"))))

(deftest test-tperm
         (is (= "UF UL UB UR DF DR DB DL FR FL BR BL URB UFR UBL ULF DRF DFL DLB DBR"
                (scramble->reid "R U R' U' R' F R2 U' R' U' R U R' F'"))))

(deftest test-twist
  (is (= "FU"
         (twist "UF" 1)))
  (is (= "UF"
         (twist "FU" 1)))
  (is (= "RUF"
         (twist "FRU" 1)))
  (is (= "UFR"
         (twist "FRU" -1))))

