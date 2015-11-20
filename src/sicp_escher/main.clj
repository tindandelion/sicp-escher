(ns sicp-escher.main
  (:require [quil.core :as q])
  (:gen-class))

(def cross [[[0 0] [512 512]]
            [[0 512] [512 0]]])

(defn map-to-frame [frame pt]
  pt)

(defn segments->picture [segments]
  (fn [frame]
    (doseq [[start end] segments]
      (q/line
        (map-to-frame frame start)
        (map-to-frame frame end)))))

(def picture (segments->picture cross))

(defn window-frame []
  {:origin [0 0]
   :e1     [0 (q/width)]
   :e2     [0 (q/height)]})

(defn draw []
  (picture (window-frame)))

(q/defsketch escher
             :title "Escher"
             :setup #(q/background 255 255 255)
             :draw draw
             :size [512 512])


