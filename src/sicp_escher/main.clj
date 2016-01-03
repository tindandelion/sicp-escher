(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.data :as data])
  (:require [sicp-escher.quil-canvas :as canvas])
  (:gen-class))

(defn blank [_])

(def t data/t)
(def u data/u)

(def side1 (quartet blank blank (rot-ccw t) t))
(def side2 (quartet side1 side1 (rot-ccw t) t))
(def corner1 (quartet blank blank blank u))
(def corner2 (quartet corner1 side1 (rot-ccw side1) u))
(def pseudocorner (quartet corner2 side2 (rot-ccw side2) (rot-ccw t)))
(def fishes (cycled-quartet pseudocorner))
(def picture data/p)

(defn initial-transform [picture]
  (-> picture
      (flip-vert)
      (scale (quil/width) (quil/height))))

(defn draw []
  (let [pic (initial-transform picture)]
    (canvas/draw pic)))

(defn setup []
  (quil/no-smooth)
  (quil/frame-rate 1)
  (quil/stroke-weight 1)
  (quil/background 200))

(defn -main []
  (quil/defsketch escher
                  :title "Escher"
                  :renderer :opengl
                  :setup setup
                  :draw draw
                  :size [800 800]))



