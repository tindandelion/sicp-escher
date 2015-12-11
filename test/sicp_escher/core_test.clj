(ns sicp-escher.core-test
  (:require [clojure.test :refer :all]
            [sicp-escher.core :as core]))

(deftest single-picture-transformations
  (let [frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]}
        test-picture (fn [fr] fr)
        apply-transform (fn [transform] ((transform test-picture) frame))]
    (is (= frame (apply-transform core/id)) "No transformation")
    (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
           (apply-transform core/flip-vert)) "Flip picture vertically")
    (is (= {:origin [100.0 0.0] :e1 [0.0 100.0] :e2 [-100.0 0.0]}
           (apply-transform core/rotate-90)) "Rotate picture by 90 degree clockwise")
    (is (= {:origin [100.0 100.0] :e1 [-100.0 0.0] :e2 [0.0 -100.0]}
           (apply-transform core/rotate-180)) "Rotate picture by 180 degree clockwise")))
