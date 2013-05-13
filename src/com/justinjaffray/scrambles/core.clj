(ns com.justinjaffray.scrambles.core
  (:use [com.justinjaffray.scrambles.analyzer])
  (:use [com.justinjaffray.scrambles.stats])
  (:use [clojure.string :only [split]])
  (:use [clojure.data.csv])
  (:gen-class)
  )

(defn -main []
  (println
    (get-csv cross-dist)))
