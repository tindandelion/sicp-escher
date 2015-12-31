(ns sicp-escher.simple-canvas
  (:require [sicp-escher.data :as data]
            [sicp-escher.frame :as frame]
            [quil.core :as quil]))

(defrecord Canvas [frame]
  frame/Transformable

  (transform [canvas {:keys [origin corner-1 corner-2]}]
    (let [tr (frame/frame-transform origin corner-1 corner-2)]
      (update canvas :frame tr)))

  data/Painter
  (line [_ start end]
    (quil/line
      (frame/map-vector frame start)
      (frame/map-vector frame end))))


