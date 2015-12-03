(ns sicp-escher.core
  (:require [sicp-escher.vectors :as v]))

(defn make-frame [origin edge1 edge2]
  {:origin origin :e1 edge1 :e2 edge2})

(defn map-to-frame [{origin :origin e1 :e1 e2 :e2} [x y]]
  (v/add-vect
    origin
    (v/add-vect
      (v/scale-vect e1 x)
      (v/scale-vect e2 y))))

(defn flip-vert [picture]
  picture)