(ns sicp-escher.core
  (:require [sicp-escher.canvas :as canvas]))

(defn- canvas-transform [picture transformer & args]
  #(picture (apply transformer % args)))

(defn flip-vert [picture]
  (canvas-transform picture canvas/flip-vert))

(defn scale [picture factor-x factor-y]
  (canvas-transform picture canvas/scale factor-x factor-y))

(defn rot-ccw [picture]
  (canvas-transform picture canvas/rot-ccw))

(defn move [picture factor-x factor-y]
  (canvas-transform picture canvas/move factor-x factor-y))

(defn beside [left-pic right-pic]
  (let [new-left (scale left-pic 0.5 1.0)
        new-right (-> right-pic
                      (scale 0.5 1.0)
                      (move 0.5 0.0))]
    (fn [canvas]
      [(new-left canvas) (new-right canvas)])))

(defn below [lower-pic upper-pic]
  (let [new-lower (scale lower-pic 1.0 0.5)
        new-upper (-> upper-pic
                      (scale 1.0 0.5)
                      (move 0.0 0.5))]
    (fn [frame]
      [(new-upper frame) (new-lower frame)])))

(defn quartet [top-left top-right bottom-left bottom-right]
  (let [top (beside top-left top-right)
        bottom (beside bottom-left bottom-right)]
    (below bottom top)))

(defn cycled-quartet [picture]
  (let [btm-left (rot-ccw picture)
        btm-right (rot-ccw btm-left)
        top-right (rot-ccw btm-right)]
    (quartet picture top-right btm-left btm-right)))
