(ns sicp-escher.data
  (:require [sicp-escher.transforms :refer :all]))

(defn blank [_])

(defn side-split [pic n]
  (if (zero? n)
    blank
    (let [smaller (side-split pic (dec n))]
      (quartet smaller smaller (rot-ccw pic) pic))))

(defn corner-split [side-pic corner-pic n]
  (if (zero? n)
    blank
    (let [side (side-split side-pic (dec n))
          corner (corner-split side-pic corner-pic (dec n))]
      (quartet corner side (rot-ccw side) corner-pic))))

(defn compose-picture [side-pic corner-pic n]
  (let [side (side-split side-pic n)
        corner (corner-split side-pic corner-pic n)
        pseudocorner (quartet corner side (rot-ccw side) (rot-ccw side-pic))
        entire-picture (cycled-quartet pseudocorner)]
    entire-picture))

