(ns sicp-escher.core-test
  (:require [clojure.test :refer :all]
            [sicp-escher.core :as core]))

(def test-pic (fn [canvas] (:frame canvas)))

(def canvas (core/->Canvas {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]}))

(deftest single-picture-transformations
  (let [same-pic (core/id test-pic)]
    (is (= {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]}
           (same-pic canvas)) "No transformation"))

  (let [flipped (core/flip-vert test-pic)]
    (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
           (flipped canvas))
        "Flip picture over the vertical axis"))

  (let [flipped (core/flip-horz test-pic)]
    (is (= {:origin [100.0 0.0] :e1 [-100.0 0.0] :e2 [0.0 100.0]}
           (flipped canvas))
        "Flip picture over the horizontal axis"))

  (let [scaled (core/scale test-pic 0.5 0.3)]
    (is (= {:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 30.0]} (scaled canvas))
        "Scale a picture by given factors"))

  (let [rotated (core/rotate test-pic)]
    (is (= {:origin [0.0 100.0] :e1 [0.0 -100.0] :e2 [100.0 0.0]} (rotated canvas))
        "Rotate a picture counter-clockwise by 90 degrees"))
  )

(deftest combining-picture-transformations
  (let [combination (core/beside test-pic test-pic)]
    (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
            {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}]
           (combination canvas))
        "Placing pictures beside each other"))

  (let [combination (core/below test-pic test-pic)]
    (is (= [{:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}
            {:origin [0.0 50.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}]
           (combination canvas))
        "Placing pictures below each other"))
  )

(deftest high-order-transformations
  (let [combination (core/quartet test-pic test-pic test-pic test-pic)]
    (is (= [[{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]
            [{:origin [0.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]]
           (combination canvas))
        "Placing pictures in four quadrants"))


  (let [combination (core/cycled-quartet test-pic)]
    (is (= [[{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [100.0 0.0] :e1 [0.0 50.0] :e2 [-50.0 0.0]}]
            [{:origin [0.0 100.0] :e1 [0.0 -50.0] :e2 [50.0 0.0]}
             {:origin [100.0 100.0] :e1 [-50.0 0.0] :e2 [0.0 -50.0]}]]
           (combination canvas))
        "Placing same in four quadrants rotating it by 90 degrees"))
  )

