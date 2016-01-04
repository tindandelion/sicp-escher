(ns sicp-escher.quil-canvas
  (:require [sicp-escher.data :as data]
            [sicp-escher.canvas :as canvas]
            [quil.core :as quil]))

(defn- add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn- scale-vect [[x y] [factor-x factor-y]]
  [(* factor-x x) (* factor-y y)])

(defn- sub-vect [[x1 y1] [x2 y2]]
  [(- x1 x2) (- y1 y2)])

(defn- map-vector [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect
      (scale-vect e1 [x x])
      (scale-vect e2 [y y]))))

(defn- frame-transform [origin corner-left corner-btm]
  (fn [frame]
    (let [new-origin (map-vector frame origin)]
      {:origin new-origin
       :e1     (sub-vect
                 (map-vector frame corner-left)
                 new-origin)
       :e2     (sub-vect
                 (map-vector frame corner-btm)
                 new-origin)})))

(defn- transform [canvas {:keys [origin corner-1 corner-2]}]
  (let [tr (frame-transform origin corner-1 corner-2)]
    (update canvas :frame tr)))

(defn update-stroke-weight [value scale-factor]
  (let [current (or value 1)]
    (/ current scale-factor)))

(defrecord SimpleCanvas [frame]
  canvas/Canvas

  (scale [this factor-x factor-y]
    (quil/scale factor-x factor-y)
    (update this :stroke-weight update-stroke-weight factor-x))

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
    (quil/stroke-weight (or (:stroke-weight this) 1))
    (try
      (picture this)
      (finally (quil/pop-matrix))))

  data/Painter
  (line [_ start end]
    (quil/line
      (map-vector frame start)
      (map-vector frame end))))

(defn initial []
  (->SimpleCanvas {:origin [0.0 0.0]
                   :e1     [1.0 0.0]
                   :e2     [0.0 1.0]}))

(defn draw [picture]
  (let [cnv (initial)]
    (canvas/draw cnv picture)))


