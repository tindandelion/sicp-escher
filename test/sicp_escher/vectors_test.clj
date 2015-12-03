(ns sicp-escher.vectors-test
  (:require [clojure.test :refer :all]
            [sicp-escher.vectors :refer :all]))

(testing "Vector arithmetic"
  (deftest add-vectors
    (let [v1 [1 1]
          v2 [2 3]]
      (is (= [3 4] (add-vect v1 v2)))))

  (deftest scale-vectors
    (let [v [2 3]]
      (is (= [4 6] (scale-vect v 2)))
      (is (= [1.0 1.5] (scale-vect v 0.5)))
      (is (= [(/ 4 3) 2] (scale-vect v (/ 2 3)))))))

(deftest mapping-frame
  (let [frame {:origin [1 1] :e1 [1 0] :e2 [0 1]}]
    (is (= [3 4] (map-to-frame frame [2 3]))))
  (let [frame {:origin [1 1] :e1 [2 0] :e2 [0 1]}]
    (is (= [5 4] (map-to-frame frame [2 3]))))
  (let [frame {:origin [1 1] :e1 [2 0] :e2 [0 3]}]
    (is (= [5 10] (map-to-frame frame [2 3]))))
  (let [frame {:origin [0 0] :e1 [512 0] :e2 [0 512]}]
    (is (= [512.0 512.0] (map-to-frame frame [1.0 1.0])))))


