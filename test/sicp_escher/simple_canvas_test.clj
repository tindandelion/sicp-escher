(ns sicp-escher.simple-canvas-test
  (:require [clojure.test :refer :all]
            [sicp-escher.simple-canvas :as simple]
            [sicp-escher.canvas :as canvas]))

(def frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]})
(def canvas (simple/->SimpleCanvas frame))

(deftest simple-canvas-transformations
  (let [scaled (canvas/scale canvas 0.5 0.3)]
    (is (= {:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 30.0]}
           (:frame scaled))
        "Scale a canvas by given factors"))

  (let [rotated (canvas/rot-ccw canvas)]
    (is (= {:origin [100.0 0.0] :e1 [0.0 100.0] :e2 [-100.0 0.0]}
           (:frame rotated))
        "Rotate a canvas counter-clockwise by 90 degrees"))

  (let [moved (canvas/move canvas 0.5 0.3)]
    (is (= {:origin [50.0 30.0] :e1 [100.0 0.0] :e2 [0.0 100.0]}
           (:frame moved))
        "Move a canvas by a given factor"))
  )
