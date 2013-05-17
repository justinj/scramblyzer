(ns scramblyzer.analyzer
  (:use [clojure.string :only [split join]])
  (:use [scramblyzer.reid-parser])
  (:use [scramblyzer.piece-filter])
  )

(def max-distance 20)

(defn num-oriented-corners
  [scramble]
  "Determines the number of oriented corners from a scramble"
  (count
    (filter corner-oriented? 
            (.corners (scramble->state scramble)))))

(defn num-oriented-edges
  [scramble]
  "Determines the number of oriented edges from a scramble"
  (count
    (filter edge-oriented? 
            (.edges (scramble->state scramble)))))

(defn solve-optimal
  [state]
  "Gets the htm distance for the given reid string"
  (let [cube-state (acube.format.ReidParser/parse (state->reid state))
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
                     nil))]
    (.solveOptimal cube-state
                   acube.Metric/FACE
                   acube.Turn/valueSet
                   max-distance
                   false
                   reporter )
    @distance-result))

(defn- ignore-piece-unless
  [pred piece]
  "Unless the piece or any of its twists matches the predicate,
  replace it with the Acube symbol for ignore (@?)"
  (if (pred piece)
    piece
    "@?"))

(defn- filter-pieces
  ([pred state]
   (filter-pieces pred pred state))
  ([edge-pred corner-pred state]
     (->State
       (map (partial ignore-piece-unless edge-pred) 
            (.edges state))
       (map (partial ignore-piece-unless corner-pred) 
            (.corners state)))))

(defn cross-dist
  [scramble]
  (let [state (scramble->state scramble)]
    (solve-optimal 
      (filter-pieces
        cross-edge?
        state))))
