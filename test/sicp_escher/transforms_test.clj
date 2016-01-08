(ns sicp-escher.transforms-test
  (:require [clojure.test :refer :all]
            [sicp-escher.transforms :as core]
            [sicp-escher.canvas.simple :as simple]))

(def test-pic (fn [canvas] (:frame canvas)))
(def frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]})
(def canvas (simple/->SimpleCanvas frame))

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
  (let [flipped (core/flip-vert test-pic)]
    (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
           (flipped canvas))
        "Flip a picture vertically"))


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
