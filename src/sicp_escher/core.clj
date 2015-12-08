(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn id [picture] picture)

(defn flip-vert [picture]
  (fn [frame] (picture (frame/flip-vert frame))))

(defn flip-horz [picture]
  (fn [frame] (picture (frame/flip-horz frame))))

(defn rotate-180 [picture]
  (flip-vert (flip-horz picture)))

(defn beside [left-pic right-pic]
  (fn [frame]
    (let [[left right] (frame/split-horz frame)]
      (left-pic left)
      (right-pic right))))

(defn below [lower-pic upper-pic]
  (fn [frame]
    (let [[upper lower] (frame/split-vert frame)]
      (upper-pic upper)
      (lower-pic lower))))

(defn split [first-transform second-transform]
  (letfn [(splitter [picture n]
            (if (= n 0)
              picture
              (let [smaller (splitter picture (dec n))]
                (first-transform picture (second-transform smaller smaller)))))]
    splitter))

(defn square-of-four [tl-transform tr-transform bl-transform br-transform]
  (fn [picture]
    (let [top (beside (tl-transform picture) (tr-transform picture))
          bottom (beside (bl-transform picture) (br-transform picture))]
      (below bottom top))))

(def right-split (split beside below))
(def up-split (split below beside))

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