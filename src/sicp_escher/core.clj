(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn id [picture] picture)

(defn- transformer-fn [new-origin new-corner-1 new-corner-2]
  (let [transform (frame/make-transform new-origin new-corner-1 new-corner-2)]
    (fn [picture]
      (fn [frame]
        (picture (transform frame))))))

(def flip-vert (transformer-fn [0.0 1.0] [1.0 1.0] [0.0 0.0]))
(def flip-horz (transformer-fn [1.0 0.0] [0.0 0.0] [1.0 1.0]))
(def rotate (transformer-fn [0.0 1.0] [0.0 0.0] [1.0 1.0]))

(defn scale [factor picture]
  (let [transformer (transformer-fn [0.0 0.0] [factor 0.0] [0.0 factor])]
    (transformer picture)))

(defn beside [left-pic right-pic]
  (let [left-transform (frame/make-transform [0.0 0.0] [0.5 0.0] [0.0 1.0])
        right-transform (frame/make-transform [0.5 0.0] [1.0 0.0] [0.5 1.0])]
    (fn [frame]
      [(left-pic (left-transform frame))
       (right-pic (right-transform frame))])))

(defn below [lower-pic upper-pic]
  (let [upper-transform (frame/make-transform [0.0 0.0] [1.0 0.0] [0.0 0.5])
        lower-transform (frame/make-transform [0.0 0.5] [1.0 0.5] [0.0 1.0])]
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

