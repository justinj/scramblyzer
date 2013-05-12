(ns com.justinjaffray.scrambles.analyzer
  (:use [com.justinjaffray.scrambles.reid-parser])
  (:use [com.justinjaffray.scrambles.piece-filter])
  )

(defn num-oriented-corners
  [scramble]
  "Determines the number of oriented corners from a scramble"
  (count
    (filter corner-oriented? 
            (.corners (scramble-to-state scramble)))))

(defn num-oriented-edges
  [scramble]
  "Determines the number of oriented edges from a scramble"
  (count
    (filter edge-oriented? 
            (.edges (scramble-to-state scramble)))))

(defn solve-optimal
  [reid-string]
  "Gets the qtm distance for the given reid string"
  (let [cube-state (acube.format.ReidParser/parse reid-string)
        distance-result (ref nil)
        reporter (proxy
                   [acube.Reporter] []
                   (solvingStarted 
                     ([s]
                      nil))
                   (tableCreationStarted
                     ([s]
                      nil))
                   (depthChanged
                     [depth]
                     nil)
                   (shouldStop
                     []
                     false)
                   (sequenceFound
                     [result ql fl sl sql]
                     (dosync
                       (ref-set distance-result
                                fl)))
                   (onePhaseStatistics
                     [pruneChecks pruneHits]
                     nil)
                   )]
    (.solveOptimal cube-state
                   acube.Metric/FACE
                   acube.Turn/valueSet
                   20
                   false
                   reporter )
    @distance-result))

(defn cross-dist
  [scramble]
  (solve-optimal (scramble-to-reid scramble)))
