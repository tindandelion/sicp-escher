(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defrecord Canvas [frame])

(defn id [picture] picture)

(defn- make-transform [new-origin new-corner-1 new-corner-2]
  (let [frame-transform (frame/make-transform new-origin new-corner-1 new-corner-2)]
    (fn [canvas]
      (->Canvas (frame-transform (:frame canvas))))))

(defn- transformer-fn-canvas [new-origin new-corner-1 new-corner-2]
  (let [transform (make-transform new-origin new-corner-1 new-corner-2)]
    (fn [picture]
      (fn [canvas] (picture (transform canvas))))))


(def flip-vert (transformer-fn-canvas [0.0 1.0] [1.0 1.0] [0.0 0.0]))
(def flip-horz (transformer-fn-canvas [1.0 0.0] [0.0 0.0] [1.0 1.0]))
(def rotate (transformer-fn-canvas [0.0 1.0] [0.0 0.0] [1.0 1.0]))

(defn scale [factor picture]
  (let [transformer (transformer-fn-canvas [0.0 0.0] [factor 0.0] [0.0 factor])]
    (transformer picture)))

(defn beside [left-pic right-pic]
  (let [left-transform (make-transform [0.0 0.0] [0.5 0.0] [0.0 1.0])
        right-transform (make-transform [0.5 0.0] [1.0 0.0] [0.5 1.0])]
    (fn [canvas]
      [(left-pic (left-transform canvas))
       (right-pic (right-transform canvas))])))

(defn below [lower-pic upper-pic]
  (let [upper-transform (make-transform [0.0 0.0] [1.0 0.0] [0.0 0.5])
        lower-transform (make-transform [0.0 0.5] [1.0 0.5] [0.0 1.0])]
    (fn [frame]
      [(upper-pic (upper-transform frame))
       (lower-pic (lower-transform frame))])))

(defn quartet [top-left top-right bottom-left bottom-right]
  (let [top (beside top-left top-right)
        bottom (beside bottom-left bottom-right)]
    (below bottom top)))

(defn cycled-quartet [picture]
  (let [btm-left (rotate picture)
        btm-right (rotate btm-left)
        top-right (rotate btm-right)]
    (quartet picture top-right btm-left btm-right)))

