(ns sicp-escher.main
  (:require [quil.core :as quil])
  (:require [sicp-escher.transforms :refer :all])
  (:require [sicp-escher.data :as data])
  (:require [sicp-escher.data.lines :as lines])
  (:require [sicp-escher.data.svg :as svg])
  (:require [sicp-escher.canvas.quil :as canvas])
  (:gen-class))

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
  (quil/set-state! :picture (data/compose-picture (svg/t) lines/u))
  (quil/no-loop))

(defn -main []
  (quil/defsketch escher
                  :title "Escher"
                  :setup setup
                  :draw draw
                  :size [800 800]))



