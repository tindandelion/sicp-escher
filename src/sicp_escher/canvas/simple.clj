(ns sicp-escher.canvas.simple
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

(defrecord SimpleCanvas [frame]
  canvas/Canvas

  (scale [this factor-x factor-y]
    (let [coords {:origin   [0.0 0.0]
                  :corner-1 [factor-x 0.0]
                  :corner-2 [0.0 factor-y]}]
      (transform this coords)))

  (move [this factor-x factor-y]
    (let [coords {:origin   [factor-x factor-y]
                  :corner-1 [(+ 1.0 factor-x) factor-y]
                  :corner-2 [factor-x (+ 1.0 factor-y)]}]
      (transform this coords)))

  (rot-ccw [this]
    (let [coords {:origin [1.0 0.0] :corner-1 [1.0 1.0] :corner-2 [0.0 0.0]}]
      (transform this coords)))

  (draw [this picture]
    (picture this))

  data/Painter
  (line [_ start end]
    (quil/line
      (map-vector frame start)
      (map-vector frame end))))

(defn draw [picture]
  (let [initial (SimpleCanvas. {:origin [0.0 0.0] :e1 [1.0 0.0] :e2 [0.0 1.0]})]
    (canvas/draw initial picture)))


