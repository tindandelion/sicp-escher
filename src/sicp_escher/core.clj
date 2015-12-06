(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn flip-vert [picture]
  (fn [frame] (picture (frame/flip-vert frame))))

(defn flip-horz [picture]
  (fn [frame] (picture (frame/flip-horz frame))))

(defn beside [picture1 picture2]
  (fn [frame]
    (let [[left right] (frame/split-horz frame)]
      (picture1 left)
      (picture2 right))))

(defn below [picture1 picture2]
  (fn [frame]
    (let [[upper lower] (frame/split-vert frame)]
      (picture1 upper)
      (picture2 lower))))

(defn right-split [picture n]
  (if (= n 0)
    picture
    (let [smaller (right-split picture (dec n))]
      (beside picture (below smaller smaller)))))

(defn up-split [picture n]
  (if (= n 0)
    picture
    (let [smaller (up-split picture (dec n))]
      (below (beside smaller smaller) picture))))

(defn corner-split [picture n]
  (if (= n 0)
    picture
    (let [up (up-split picture (dec n))
          right (right-split picture (dec n))
          top-left (beside up up)
          bottom-right (below right right)
          corner (corner-split picture (dec n))]
      (beside
        (below top-left picture)
        (below corner bottom-right)))))

(defn square-limit [picture n]
  (let [quarter (corner-split picture n)
        half (beside (flip-horz quarter) quarter)]
    (below half (flip-vert half))))