(ns sicp-escher.main
  (:require [quil.core :as q])
  (:require [sicp-escher.vectors :as vect])
  (:gen-class))

(defn segments->picture [segments]
  (fn [frame]
    (doseq [[start end] segments]
      (q/line
        (vect/map-to-frame frame start)
        (vect/map-to-frame frame end)))))

(def cross (segments->picture
             [[[0 0] [1.0 1.0]]
              [[0 1.0] [1.0 0]]
              [[0 0.2] [0.2 0]]]))

(def picture cross)

(defn window-frame []
  {:origin [0 0]
   :e1     [(q/width) 0]
   :e2     [0 (q/height)]})

(defn draw []
  (picture (window-frame)))

(q/defsketch escher
             :title "Escher"
             :setup #(q/background 255 255 255)
             :draw draw
             :size [512 512])


