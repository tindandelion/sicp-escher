(ns sicp-escher.vectors-test
  (:require [clojure.test :refer :all]
            [sicp-escher.vectors :refer :all]))


(defn scale-vect [{x :x y :y} factor]
  {:x (* factor x) :y (* factor y)})

(testing "Vector arithmetic"
  (deftest add-vectors
    (let [v1 {:x 1 :y 1}
          v2 {:x 2 :y 3}]
      (is (= {:x 3 :y 4} (add-vect v1 v2)))))

  (deftest scale-vectors
    (let [v {:x 2 :y 3}]
      (is (= {:x 4 :y 6} (scale-vect v 2)))
      (is (= {:x 1.0 :y 1.5} (scale-vect v 0.5)))
      (is (= {:x (/ 4 3) :y 2} (scale-vect v (/ 2 3)))))))


