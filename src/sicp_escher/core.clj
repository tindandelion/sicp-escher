(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn- make-transform [new-origin new-corner-1 new-corner-2]
  (let [frame-transform (frame/make-transform new-origin new-corner-1 new-corner-2)]
    (fn [canvas]
      (update canvas :frame frame-transform))))

(defn- transformer-fn [new-origin new-corner-1 new-corner-2]
  (let [transform (make-transform new-origin new-corner-1 new-corner-2)]
    (fn [picture]
      (fn [canvas] (picture (transform canvas))))))


(def flip-vert (transformer-fn [0.0 1.0] [1.0 1.0] [0.0 0.0]))
(def rot-ccw (transformer-fn [1.0 0.0] [1.0 1.0] [0.0 0.0]))

(defn scale [picture factor-x factor-y]
  (let [transformer (transformer-fn [0.0 0.0] [factor-x 0.0] [0.0 factor-y])]
    (transformer picture)))

(defn beside [left-pic right-pic]
  (let [left-transform (make-transform [0.0 0.0] [0.5 0.0] [0.0 1.0])
        right-transform (make-transform [0.5 0.0] [1.0 0.0] [0.5 1.0])]
    (fn [canvas]
      [(left-pic (left-transform canvas))
       (right-pic (right-transform canvas))])))

(defn below [lower-pic upper-pic]
  (let [lower-transform (make-transform [0.0 0.0] [1.0 0.0] [0.0 0.5])
        upper-transform (make-transform [0.0 0.5] [1.0 0.5] [0.0 1.0])]
    (fn [frame]
      [(upper-pic (upper-transform frame))
       (lower-pic (lower-transform frame))])))

(defn quartet [top-left top-right bottom-left bottom-right]
  (let [top (beside top-left top-right)
        bottom (beside bottom-left bottom-right)]
    (below bottom top)))

(defn cycled-quartet [picture]
  (let [btm-left (rot-ccw picture)
        btm-right (rot-ccw btm-left)
        top-right (rot-ccw btm-right)]
    (quartet picture top-right btm-left btm-right)))
