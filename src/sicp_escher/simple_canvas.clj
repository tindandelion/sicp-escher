(ns sicp-escher.simple-canvas
  (:require [sicp-escher.data :as data]
            [sicp-escher.frame :as frame]
            [quil.core :as quil]))

(defrecord Canvas [frame]
  data/Painter
  (line [_ start end]
    (quil/line
      (frame/map-vector frame start)
      (frame/map-vector frame end))))

