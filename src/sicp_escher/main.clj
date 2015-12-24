(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.data :as data])
  (:gen-class))

(defn blank [_])

(def q (data/q quil/line))
(def p (data/p quil/line))
(def r (data/r quil/line))
(def s (data/s quil/line))
(def t (flip-vert (quartet p q r s)))
(def u (cycled-quartet (flip-horz (rotate s))))


(def side1 (quartet blank blank (rotate t) t))
(def side2 (quartet side1 side1 (rotate t) t))
(def corner1 (quartet blank blank blank u))
(def corner2 (quartet corner1 side1 (rotate side1) u))
(def pseudocorner (quartet corner2 side2 (rotate side2) (rotate t)))
(def fishes (cycled-quartet pseudocorner))
(def picture fishes)

(defn window-frame []
  {:origin [0 0] :e1 [(quil/width) 0] :e2 [0 (quil/height)]})

(defn draw []
  (picture (window-frame)))

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


