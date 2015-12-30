(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.data :as data]
            [sicp-escher.frame :as frame])
  (:import (sicp_escher.core Canvas))
  (:gen-class))

(extend-type Canvas
  data/Painter
  (line [this start end]
    (let [frame (:frame this)]
      (quil/line
        (frame/map-vector frame start)
        (frame/map-vector frame end)))))

(defn blank [_])


(def p data/p)
(def q data/q)
(def r data/r)
(def s data/s)

(def t  (quartet p q r s))
(def u (cycled-quartet (rot-ccw q)))


(def side1 (quartet blank blank (rot-ccw t) t))
(def side2 (quartet side1 side1 (rot-ccw t) t))
(def corner1 (quartet blank blank blank u))
(def corner2 (quartet corner1 side1 (rot-ccw side1) u))
(def pseudocorner (quartet corner2 side2 (rot-ccw side2) (rot-ccw t)))
(def fishes (cycled-quartet pseudocorner))
(def picture fishes)

(defn window-canvas []
  (->Canvas {:origin [0 0]
             :e1     [(quil/width) 0]
             :e2     [0 (quil/height)]}))

(defn draw []
  (let [pic (flip-vert picture)]
    (pic (window-canvas))))

(defn setup []
  (quil/no-smooth)
  (quil/frame-rate 1)
  (quil/stroke-weight 1)
  (quil/background 200))

(quil/defsketch escher
                :title "Escher"
                :setup setup
                :draw draw
                :size [600 600])


