(ns com.justinjaffray.scrambles.core)

(def solved-edges "UF UR UB UL DF DR DB DL FR FL BR BL")
(def solved-corners "UFR URB UBL ULF DRF DFL DLB DBR")
(def solved-state (acube.format.ReidParser/parse 
                    (str solved-edges " " solved-corners))) 

(def edges "UF UL UB UR DF DR DB DL FR FL BR BL")
(def corners "URB UFR UBL ULF DRF DFL DLB DBR")
(def tperm (acube.format.ReidParser/parse 
                    (str edges " " corners))) 

(defn -main [] (println (.solveOptimal tperm
                           acube.Metric/FACE
                           acube.Turn/valueSet
                           20
                           false
                           (new acube.console.ConsoleReporter))))
