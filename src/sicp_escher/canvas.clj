(ns sicp-escher.canvas)

(defprotocol Canvas
  (flip-vert [this])
  (rot-ccw [this])
  (scale [this factor-x factor-y])
  (move [this factor-x factor-y]))

