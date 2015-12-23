(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.data :as data])
  (:gen-class))

(defn blank [_])

(def t (flip-vert (quartet
                    (data/p quil/line)
                    (data/q quil/line)
                    (data/r quil/line)
                    (data/s quil/line))))


(def side1 (quartet blank blank (rotate-ccw-90 t) t))
(def picture side1)

; (def picture (beside cross (rotate-180 cross)))

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
                :size [512 512])


