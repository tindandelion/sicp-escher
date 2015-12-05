(ns sicp-escher.core
  (:require [sicp-escher.frame :as frame]))

(defn flip-vert [picture]
  (fn [frame] (picture (frame/flip-vert frame))))

(defn scale [picture factor]
  (fn [frame]
    (picture (frame/scale frame factor))))

(defn move-by [picture increment]
  (fn [frame]
    (picture (frame/move-by frame increment))))