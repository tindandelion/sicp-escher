(ns sicp-escher.main
  (:require [quil.core :as q])
  (:require [sicp-escher.core :refer :all])
  (:require [sicp-escher.data :as data])
  (:gen-class))

(def picture (data/grid->picture data/p q/line))
; (def picture (beside cross (rotate-180 cross)))

(defn window-frame []
  {:origin [0 0] :e1 [(q/width) 0] :e2 [0 (q/height)]})

(defn draw []
  (picture (window-frame)))

(defn setup []
  (q/smooth)
  (q/frame-rate 1)
  (q/stroke-weight 1)
  (q/background 200))

(q/defsketch escher
             :title "Escher"
             :setup setup
             :draw draw
             :size [512 512])


