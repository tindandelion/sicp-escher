(ns sicp-escher.core-test
  (:require [clojure.test :refer :all]
            [sicp-escher.core :as core]
            [sicp-escher.simple-canvas :as simple]))

(def test-pic (fn [canvas] (:frame canvas)))
(def frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]})
(def canvas (simple/->Canvas frame))

(deftest single-picture-transformations

  (let [flipped (core/flip-vert test-pic)]
    (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
           (flipped canvas))
        "Flip picture over the vertical axis"))

  (let [scaled (core/scale test-pic 0.5 0.3)]
    (is (= {:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 30.0]} (scaled canvas))
        "Scale a picture by given factors"))

  (let [rotated (core/rot-ccw test-pic)]
    (is (= {:origin [100.0 0.0] :e1 [0.0 100.0] :e2 [-100.0 0.0]} (rotated canvas))
        "Rotate a picture counter-clockwise by 90 degrees"))
  )

(deftest combining-picture-transformations
  (let [combination (core/beside test-pic test-pic)]
    (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
            {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}]
           (combination canvas))
        "Placing pictures beside each other"))

  (let [combination (core/below test-pic test-pic)
        upper {:origin [0.0 50.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}
        lower {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}]
    (is (= [upper lower] (combination canvas))
        "Placing pictures below each other"))
  )

(deftest high-order-transformations
  (let [combination (core/quartet test-pic test-pic test-pic test-pic)

        top-left {:origin [0.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
        top-right {:origin [50.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
        btm-left {:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
        btm-right {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]
    (is (= [[top-left top-right]
            [btm-left btm-right]]
           (combination canvas))
        "Placing pictures in four quadrants"))


  (let [combination (core/cycled-quartet test-pic)
        top-left {:origin [0.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
        top-right {:origin [50.0 100.0] :e1 [0.0 -50.0] :e2 [50.0 0.0]}
        btm-left {:origin [50.0 0.0] :e1 [0.0 50.0] :e2 [-50.0 0.0]}
        btm-right {:origin [100.0 50.0] :e1 [-50.0 0.0] :e2 [0.0 -50.0]}]
    (is (= [[top-left top-right]
            [btm-left btm-right]]
           (combination canvas))
        "Placing same in four quadrants rotating it by 90 degrees"))
  )

