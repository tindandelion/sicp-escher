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
(def rotate-ccw-90 (transformer-fn [0.0 1.0] [0.0 0.0] [1.0 1.0]))

(defn scale [factor picture]
  (let [transformer (transformer-fn [0.0 0.0] [factor 0.0] [0.0 factor])]
    (transformer picture)))

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