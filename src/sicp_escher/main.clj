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

(def picture (beside cross (flip-vert cross)))

(defn window-frame []
  (frame/make-frame [0 0] [(q/width) 0] [0 (q/height)]))

(defn draw []
  (picture (window-frame)))

(q/defsketch escher
             :title "Escher"
             :setup #(q/background 255 255 255)
             :draw draw
             :size [512 512])


