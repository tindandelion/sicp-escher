(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn flip-vert [picture]
  (fn [frame] (picture (frame/flip-vert frame))))

(defn beside [left-picture right-picture]
  (fn [frame]
    (let [[left-frame right-frame] (frame/split-vert frame)]
      (left-picture left-frame)
      (right-picture right-frame))))