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


