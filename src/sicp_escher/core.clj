(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn id [picture] picture)

(defn flip-vert [picture]
  (fn [frame] (picture (frame/flip-vert frame))))

(defn flip-horz [picture]
  (fn [frame] (picture (frame/flip-horz frame))))

(defn rotate180 [picture]
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

(defn right-split [picture n]
  (if (= n 0)
    picture
    (let [smaller (right-split picture (dec n))]
      (beside picture (below smaller smaller)))))

(defn up-split [picture n]
  (if (= n 0)
    picture
    (let [smaller (up-split picture (dec n))]
      (below picture (beside smaller smaller)))))

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

(defn square-of-four [top-left top-right bottom-left bottom-right]
  (fn [picture]
    (let [top (beside (top-left picture) (top-right picture))
          bottom (beside (bottom-left picture) (bottom-right picture))]
      (below bottom top))))

(def flipped-pairs (square-of-four id flip-vert id flip-vert))

(def quadrant (square-of-four flip-horz id rotate180 flip-vert))

(defn square-limit [picture n]
  (quadrant (corner-split picture n)))