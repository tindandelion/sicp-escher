(ns sicp-escher.simple-transforms-test
  (:require [clojure.test :refer :all])
  (:require [sicp-escher.transforms :as tr])
  (:require [sicp-escher.canvas :as cnv]))

(defn- record-transform [recorder transform]
  (update recorder :recorded-transforms #(conj % transform)))

(defrecord TransformRecorder [recorded-transforms]
  cnv/Canvas
  (move [this factor-x factor-y]
    (record-transform this [:move factor-x factor-y]))

  (scale [this factor-x factor-y]
    (record-transform this [:scale factor-x factor-y]))

  (rot-ccw [this]
    (record-transform this [:rot-ccw]))

  (draw [this picture]
    (picture this)))

(defn pic [canvas] (:recorded-transforms canvas))
(def recorder (TransformRecorder. []))

(deftest performing-simple-transforms
  (let [scaled (tr/scale pic 0.3 0.4)]
    (is (= [[:scale 0.3 0.4]] (cnv/draw recorder scaled))
        "Scaling picture by given factors"))

  (let [moved (tr/move pic 0.3 0.4)]
    (is (= [[:move 0.3 0.4]] (cnv/draw recorder moved))
        "Moving picture by given factors"))

  (let [rotated (tr/rot-ccw pic)]
    (is (= [[:rot-ccw]] (cnv/draw recorder rotated))
        "Rotating picture"))

  (let [flipped (tr/flip-vert pic)]
    (is (= [[:move 0.0 1.0] [:scale 1.0 -1.0]] (cnv/draw recorder flipped))
        "Flip vertically means moving picture up and then changing the sign of y-axis"))
  )


