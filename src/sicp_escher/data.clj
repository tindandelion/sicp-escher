(ns sicp-escher.data
  (:require [sicp-escher.transforms :refer :all]))

(defn compose-picture [t u]
  (let [blank (fn [_])
        side1 (quartet blank blank (rot-ccw t) t)
        side2 (quartet side1 side1 (rot-ccw t) t)
        corner1 (quartet blank blank blank u)
        corner2 (quartet corner1 side1 (rot-ccw side1) u)
        pseudocorner (quartet corner2 side2 (rot-ccw side2) (rot-ccw t))
        entire-picture (cycled-quartet pseudocorner)]
    entire-picture))

