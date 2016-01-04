(ns sicp-escher.canvas.quil
  (:require [sicp-escher.canvas :as canvas]
            [quil.core :as quil]))

(defrecord QuilCanvas [stroke-weight]
  canvas/Canvas

  (scale [this factor-x factor-y]
    (quil/scale factor-x factor-y)
    (update this :stroke-weight #(/ % factor-x)))

  (move [this factor-x factor-y]
    (quil/translate factor-x factor-y)
    this)

  (rot-ccw [this]
    (quil/translate 0.5 0.5)
    (quil/rotate quil/HALF-PI)
    (quil/translate -0.5 -0.5)
    this)

  (draw [this picture]
    (quil/push-matrix)
    (quil/stroke-weight (:stroke-weight this))
    (try
      (picture this)
      (finally (quil/pop-matrix))))

  (line [_ start end]
    (quil/line start end)))

(defn draw [picture]
  (let [initial (QuilCanvas. 1)]
    (canvas/draw initial picture)))
