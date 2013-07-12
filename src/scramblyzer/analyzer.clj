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
  "Unless the piece satisfies the predicate, replace it with the Acube 
  symbol for ignore (@?)"
  (if (pred piece)
    piece
    "@?"))

(defn- filter-pieces
  "Ignore any pieces not matching the predicate."
  ([pred state]
   (filter-pieces pred pred state))
  ([edge-pred corner-pred state]
     (->State
       (map (partial ignore-piece-unless edge-pred) 
            (.edges state))
       (map (partial ignore-piece-unless corner-pred) 
            (.corners state)))))

(defn- solve-optimal-filtered
  [scramble pred]
  "The optimal solution to the scramble, ignoring
  any pieces that do not satisfy the predicate."
  (let [state (scramble->state scramble)]
    (solve-optimal 
      (filter-pieces
        pred
        state))))

(defn cross-dist
  [scramble]
  "How many moves are required the solve the cross on D of the scramble."
  (solve-optimal-filtered scramble cross-edge?))

(defn roux-block-dist
  [scramble]
  "How many moves are required to solve the first roux block and DL
  note: ignores that roux blocks off by L are still solved"
  (solve-optimal-filtered scramble roux-block-piece?))

(defn edge-dist
  [scramble]
  "How many moves are required the solve the edges of the scramble.
  Takes too long to use in practice."
  (solve-optimal-filtered scramble edge?))

(defn corner-dist
  [scramble]
  "How many moves are required the solve the corners of the scramble."
  (solve-optimal-filtered scramble corner?))

(defn- same-but-misoriented?
  [a b]
  (let [size (dec (count a))
        twists (map #(twist a %) (range 1 (inc size)))]
    (some #(= b %) twists)
  ))

(defn misoriented-pieces
  [scramble]
  "How many pieces in the scramble are permuted, but oriented improperly"
  (let [state (scramble->state scramble)
        edges (.edges state)
        corners (.corners state)]
    (count
      (remove nil? 
        (map same-but-misoriented? 
             (concat edges corners) 
             (concat solved-edges solved-corners))))
    ))
