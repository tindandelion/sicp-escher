(ns sicp-escher.main
  (:require [quil.core :as q])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.frame :as frame])
  (:gen-class))

(defn segments->picture [segments]
  (fn [frame]
    (doseq [[start end] segments]
      (q/line
        (frame/map-vector frame start)
        (frame/map-vector frame end)))))

(def cross (segments->picture
             [[[0 0] [1.0 1.0]]
              [[0 1.0] [1.0 0]]
              [[0 0.2] [0.2 0]]]))

(def corner (corner-split cross 4))

; (def tr (square-of-four id flip-horz flip-vert rotate180))

(def picture corner)

(defn window-frame []
  {:origin [0 0] :e1 [(q/width) 0] :e2 [0 (q/height)]})

(defn draw []
  (picture (window-frame)))

(defn setup []
  (q/smooth)
  (q/frame-rate 1)
  (q/stroke-weight 2)
  (q/background 200))

(q/defsketch escher
             :title "Escher"
             :setup setup
             :draw draw
             :size [512 512])


