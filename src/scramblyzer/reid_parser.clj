(ns scramblyzer.reid-parser
  [:use [clojure.string :only [join split blank?]]])

(defrecord Move 
  [edge-perm
   edge-orie
   corner-perm
   corner-orie])

(defrecord State
  [edges corners])

(def moves
  {
    "U" (Move. [1 2 3 0 4 5 6 7 8 9 10 11]
               [0 0 0 0 0 0 0 0 0 0 0 0]
               [1 2 3 0 4 5 6 7]
               [0 0 0 0 0 0 0 0])

    "D" (Move. [0 1 2 3 7 4 5 6 8 9 10 11]
               [0 0 0 0 0 0 0 0 0 0 0 0]
               [0 1 2 3 5 6 7 4]
               [0 0 0 0 0 0 0 0])

    "F" (Move. [9 1 2 3 8 5 6 7 0 4 10 11]
               [1 0 0 0 1 0 0 0 1 1 0 0]
               [3 1 2 5 0 4 6 7]
               [1 0 0 -1 -1 1 0 0])

    "B" (Move. [0 1 10 3 4 5 11 7 8 9 6 2]
               [0 0 1 0 0 0 1 0 0 0 1 1]
               [0 7 1 3 4 5 2 6]
               [0 -1 1 0 0 0 -1 1])

    "L" (Move. [0 1 2 11 4 5 6 9 8 3 10 7]
               [0 0 0 0 0 0 0 0 0 0 0 0]
               [0 1 6 2 4 3 5 7]
               [0 0 -1 1 0 -1 1 0])

    "R" (Move. [0 8 2 3 4 10 6 7 5 9 1 11]
               [0 0 0 0 0 0 0 0 0 0 0 0]
               [4 0 2 3 7 5 6 1]
               [-1 1 0 0 1 0 0 -1])})

(defn- scramble-moves
  [scramble]
  (remove blank?
          (seq (split scramble #"\s+"))))

(def move-type-counts
  {""  1
   "2" 2
   "'" 3})

(defn- expand-move
  [move]
  "Turn a move like R2 into clockwise turns like R R"
  (let [prefix (subs move 0 1)
        suffix (subs move 1)]
    (take (move-type-counts suffix)
          (cycle prefix)))) 

(defn- expand-scramble
  [scramble]
  "Turn all the moves into one of {U,D,L,R,F,B}"
  (let [moves (scramble-moves scramble)]
  (join " "
        (mapcat
          expand-move
          moves))))

(def solved-edges
  ; 0    1    2    3    4    5    6    7    8    9    10   11
  ["UF" "UR" "UB" "UL" "DF" "DR" "DB" "DL" "FR" "FL" "BR" "BL"])

(def solved-corners
  ; 0     1     2     3     4     5     6     7
  ["UFR" "URB" "UBL" "ULF" "DRF" "DFL" "DLB" "DBR"])

(def solved-state
  (State. solved-edges solved-corners))

(defn twist
  [piece-name amount]
  "Twist a piece by the amount given.
  e.g., UF twisted becomes FU"
  (let [sides (seq piece-name)
        times (mod amount (count sides))]
    (cond (= times 0) piece-name
          :else (twist
                  (join (concat (rest sides) (list (first sides))))
                  (dec times)))))

(defn- permute
  [base permutation]
  "Applies the permutation to the base"
  (vec (for [index permutation]
    (base index))))

(defn- orient
  [base orientation]
  "Applies the orientation to the pieces"
  (vec (map twist base orientation)))

(defn- apply-move-edges
  [edges move]
  (-> edges
    (permute (.edge-perm move))
    (orient  (.edge-orie move))))

(defn- apply-move-corners
  [corners move]
  (-> corners
    (permute (.corner-perm move))
    (orient  (.corner-orie move))))

(defn- apply-move
  [state move-name]
  (let [move (moves move-name)]
  (State. (apply-move-edges   (.edges state)   move)
          (apply-move-corners (.corners state) move))))

(defn scramble->state 
  [scramble]
  "Create a State record from a scramble"
  (let [moves (-> scramble
                expand-scramble
                scramble-moves)]
      (reduce apply-move
              solved-state
              moves)))

(defn- string->pieces
  [reid-string]
  (split reid-string #" "))

(defn state->reid 
  [state]
  "Takes a State record and makes a Reid string out of it"
  (str (join " " (concat (.edges state) (.corners state)))))

(defn- reid-edges
  [reid-string]
  (take 12 (string->pieces reid-string)))

(defn- reid-corners
  [reid-string]
  (drop 12 (string->pieces reid-string)))

(defn reid->state
  [reid]
  (State.
    (reid-edges reid)
    (reid-corners reid)))

(defn scramble->reid
  [scramble]
  (-> scramble
      scramble->state
      state->reid))
