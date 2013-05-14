(ns scramblyzer.analyzer
  (:use [clojure.string :only [split join]])
  (:use [scramblyzer.reid-parser])
  (:use [scramblyzer.piece-filter]))

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
                     nil))]
    (.solveOptimal cube-state
                   acube.Metric/FACE
                   acube.Turn/valueSet
                   20
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

(defn- string->pieces
  [reid-string]
  (split reid-string #" "))

(defn- reid-edges
  [reid-string]
  (take 12 (string->pieces reid-string)))

(defn- reid-corners
  [reid-string]
  (drop 12 (string->pieces reid-string)))

(defn filter-pieces
  ([pred reid]
   (filter-pieces pred pred reid))
  ([edge-pred corner-pred reid]
    (let [edges   (reid-edges reid)
          corners (reid-corners reid)]
     (str (join " " 
                (concat
                  (map 
                    (partial ignore-piece-unless edge-pred) 
                    edges)
                  (map 
                    (partial ignore-piece-unless corner-pred)
                    corners)))))))

(defn cross-dist
  [scramble]
  (solve-optimal (filter-pieces
                   cross-edge?
                   (scramble->reid scramble))))
