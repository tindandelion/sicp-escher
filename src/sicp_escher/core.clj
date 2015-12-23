(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn id [picture] picture)

(defn scale [factor picture]
  (let [transform (frame/make-transform [0.0 0.0] [factor 0.0] [0.0 factor])]
    (fn [frame] (picture (transform frame)))))

(defn flip-vert [picture]
  (let [transform (frame/make-transform [0.0 1.0] [1.0 1.0] [0.0 0.0])]
    (fn [frame] (picture (transform frame)))))

(defn flip-horz [picture]
  (let [transform (frame/make-transform [1.0 0.0] [0.0 0.0] [1.0 1.0])]
    (fn [frame] (picture (transform frame)))))

(defn rotate-ccw-90 [picture]
  (let [transform (frame/make-transform [0.0 1.0] [0.0 0.0] [1.0 1.0])]
    (fn [frame] (picture (transform frame)))))


(defn rotate-180 [picture]
  (flip-horz (flip-vert picture)))

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

(defn split [first-transform second-transform]
  (letfn [(splitter [picture n]
            (if (= n 0)
              picture
              (let [smaller (splitter picture (dec n))]
                (first-transform picture (second-transform smaller smaller)))))]
    splitter))

(def right-split (split beside below))
(def up-split (split below beside))

(defn square-of-four [tl-transform tr-transform bl-transform br-transform]
  (fn [picture]
    (let [top (beside (tl-transform picture) (tr-transform picture))
          bottom (beside (bl-transform picture) (br-transform picture))]
      (below bottom top))))


(defn corner-split [picture n]
  (if (= n 0)
    picture
    (let [up (up-split picture (dec n))
          right (right-split picture (dec n))
          top-left (beside up up)
          bottom-right (below right right)
          corner (corner-split picture (dec n))]
      (beside
        (below picture top-left)
        (below bottom-right corner)))))

(defn square-limit [picture n]
  (let [quadrant (square-of-four flip-horz id rotate-180 flip-vert)]
    (quadrant (corner-split picture n))))