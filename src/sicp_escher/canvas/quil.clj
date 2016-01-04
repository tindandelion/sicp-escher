(ns sicp-escher.canvas.quil
  (:require [sicp-escher.canvas :as canvas]
            [quil.core :as quil]))

(defn- rot-center [angle-radians]
  (quil/translate 0.5 0.5)
  (quil/rotate angle-radians)
  (quil/translate -0.5 -0.5))

(defrecord QuilCanvas [stroke-weight]
  canvas/Canvas

  (scale [this factor-x factor-y]
    (quil/scale factor-x factor-y)
    (update this :stroke-weight #(/ % factor-x)))

  (move [this factor-x factor-y]
    (quil/translate factor-x factor-y)
    this)

  (rot-ccw [this]
    (rot-center quil/HALF-PI)
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
