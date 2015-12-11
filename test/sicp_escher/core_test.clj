(ns sicp-escher.core-test
  (:require [clojure.test :refer :all]
            [sicp-escher.core :as core]))

(let [frame {:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 100.0]}
      test-picture (fn [fr] fr)]

  (deftest single-picture-transformations
    (letfn [(apply-transform [transform]
              ((transform test-picture) frame))]

      (is (= frame (apply-transform core/id)) "No transformation")
      (is (= {:origin [0.0 100.0] :e1 [100.0 0.0] :e2 [0.0 -100.0]}
             (apply-transform core/flip-vert)) "Flip picture over the vertical axis")
      (is (= {:origin [100.0 0.0] :e1 [-100.0 0.0] :e2 [0.0 100.0]}
             (apply-transform core/flip-horz)) "Flip picture over the horizontal axis")
      (is (= {:origin [100.0 100.0] :e1 [-100.0 0.0] :e2 [0.0 -100.0]}
             (apply-transform core/rotate-180)) "Rotate picture by 180 degree clockwise")))

  (deftest combining-picture-transformations
    (letfn [(apply-transform [transform]
              ((transform test-picture test-picture) frame))]
      (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
              {:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}]
             (apply-transform core/beside)) "Placing pictures beside each other")
      (is (= [{:origin [0.0 0.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}
              {:origin [0.0 50.0] :e1 [100.0 0.0] :e2 [0.0 50.0]}]
             (apply-transform core/below)) "Placing pictures below each other")))

  (deftest composite-transformations
    (is (= [{:origin [0.0 0.0] :e1 [50.0 0.0] :e2 [0.0 100.0]}
            [{:origin [50.0 0.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}
             {:origin [50.0 50.0] :e1 [50.0 0.0] :e2 [0.0 50.0]}]]
           ((core/right-split test-picture 1) frame)) "Right-plitting picture once"))
  )

