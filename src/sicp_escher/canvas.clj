(ns sicp-escher.canvas)

(defprotocol Canvas
  (rot-ccw [this])
  (scale [this factor-x factor-y])
  (move [this factor-x factor-y])

  (draw [this picture])
  (line [this start end]))


