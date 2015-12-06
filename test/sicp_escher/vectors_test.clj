(ns sicp-escher.vectors-test
  (:require [clojure.test :refer :all]
            [sicp-escher.frame :as frame]))

(deftest mapping-vector-to-frame
  (let [frame {:origin [1 1] :e1 [1 0] :e2 [0 1]}]
    (is (= [3 4] (frame/map-vector frame [2 3]))))
  (let [frame {:origin [1 1] :e1 [2 0] :e2 [0 1]}]
    (is (= [5 4] (frame/map-vector frame [2 3]))))
  (let [frame {:origin [1 1] :e1 [2 0] :e2 [0 3]}]
    (is (= [5 10] (frame/map-vector frame [2 3]))))
  (let [frame {:origin [0 0] :e1 [512 0] :e2 [0 512]}]
    (is (= [512.0 512.0] (frame/map-vector frame [1.0 1.0])))))

(deftest frame-operations
  (let [original {:origin [0 0] :e1 [10 0] :e2 [3 10]}]
    (is (= {:origin [0 10] :e1 [10 0] :e2 [3 -10]}
           (frame/flip-vert original)))
    (is (= {:origin [0 0] :e1 [20 0] :e2 [6 40]}
           (frame/scale original [2 4])))
    (is (= [{:origin [0 0] :e1 [5.0 0] :e2 [1.5 10]}
            {:origin [5.0 0] :e1 [5.0 0] :e2 [1.5 10]}]
           (frame/split-horz original)))
    ))


