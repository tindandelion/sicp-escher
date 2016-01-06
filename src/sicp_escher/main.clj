(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.transforms :refer :all])
  (:require [sicp-escher.data :as data])
  (:require [sicp-escher.canvas.quil :as canvas])
  (:gen-class))

(defn compose-picture [t u]
  (let [blank (fn [_])
        side1 (quartet blank blank (rot-ccw t) t)
        side2 (quartet side1 side1 (rot-ccw t) t)
        corner1 (quartet blank blank blank u)
        corner2 (quartet corner1 side1 (rot-ccw side1) u)
        pseudocorner (quartet corner2 side2 (rot-ccw side2) (rot-ccw t))
        entire-picture (cycled-quartet pseudocorner)]
    entire-picture))

(defn initial-transform [picture]
  (-> picture
      (flip-vert)
      (scale (quil/width) (quil/height))))

(defn draw []
  (-> (quil/state :picture)
      (initial-transform)
      (canvas/draw)))

(defn setup []
  (quil/no-smooth)
  (quil/frame-rate 1)
  (quil/stroke-weight 1)
  (quil/background 200)
  (quil/set-state! :picture (compose-picture data/t data/u))
  (quil/no-loop))

(defn -main []
  (quil/defsketch escher
                  :title "Escher"
                  :setup setup
                  :draw draw
                  :size [800 800]))



