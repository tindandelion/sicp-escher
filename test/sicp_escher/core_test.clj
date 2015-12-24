(ns sicp-escher.core-test
  (:require [clojure.test :refer :all]
            [sicp-escher.core :as core]))

(def test-pic (fn [fr] fr))
(def frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]})

(deftest single-picture-transformations
  (letfn [(apply-transform [transform]
            ((transform test-pic) frame))]

    (is (= frame (apply-transform core/id))
        "No transformation")

    (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
           (apply-transform core/flip-vert))
        "Flip picture over the vertical axis")

    (is (= {:origin [100.0 0.0] :e1 [-100.0 0.0] :e2 [0.0 100.0]}
           (apply-transform core/flip-horz))
        "Flip picture over the horizontal axis")

    (is (= {:origin [100.0 100.0] :e1 [-100.0 0.0] :e2 [0.0 -100.0]}
           (apply-transform core/rotate-180))
        "Rotate picture by 180 degree clockwise")

    (let [scaled (core/scale 0.5 test-pic)]
      (is (= {:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]} (scaled frame))
          "Scale a picture proportionally by a given factor"))

    (let [rotated (core/rotate-ccw-90 test-pic)]
      (is (= {:origin [0.0 100.0] :e1 [0.0 -100.0] :e2 [100.0 0.0]} (rotated frame))
          "Rotate a picture counter-clockwise by 90 degrees"))
    ))

(deftest combining-picture-transformations
  (let [combination (core/beside test-pic test-pic)]
    (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
            {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}]
           (combination frame))
        "Placing pictures beside each other"))

  (let [combination (core/below test-pic test-pic)]
    (is (= [{:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}
            {:origin [0.0 50.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}]
           (combination frame))
        "Placing pictures below each other"))
  )

(deftest high-order-transformations
  (let [combination (core/quartet test-pic test-pic test-pic test-pic)]
    (is (= [[{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]
            [{:origin [0.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]]
           (combination frame))
        "Placing pictures in four quadrants"))


  (let [combination (core/cycled-quartet test-pic)]
    (is (= [[{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [100.0 0.0] :e1 [0.0 50.0] :e2 [-50.0 0.0]}]
            [{:origin [0.0 100.0] :e1 [0.0 -50.0] :e2 [50.0 0.0]}
             {:origin [100.0 100.0] :e1 [-50.0 0.0] :e2 [0.0 -50.0]}]]
           (combination frame))
        "Placing same in four quadrants rotating it by 90 degrees"))

  (let [combination (core/right-split test-pic 1)]
    (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
            [{:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]]
           (combination frame))
        "Right-plitting picture once")))

