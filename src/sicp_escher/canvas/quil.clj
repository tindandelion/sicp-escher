(ns sicp-escher.canvas.quil
  (:require [sicp-escher.canvas :as canvas]
            [quil.core :as quil]))

(defn- null-transform [canvas picture]
  (picture canvas))

(defn- nullify-transform [canvas]
  (assoc canvas :transform null-transform))

(defn- create-transform [transformer-fn]
  (fn [canvas picture]
    (quil/push-matrix)
    (try
      (transformer-fn)
      (picture (nullify-transform canvas))
      (finally (quil/pop-matrix)))))

(defn- remember-transform [canvas action]
  (assoc canvas :transform (create-transform action)))

(defn- apply-transform-and-draw [canvas picture]
  (let [transform (:transform canvas)]
    (transform canvas picture)))

(defrecord QuilCanvas [stroke-weight transform]
  canvas/Canvas

  (scale [this factor-x factor-y]
    (-> this
        (update :stroke-weight #(/ % factor-x))
        (remember-transform #(quil/scale factor-x factor-y))))

  (move [this factor-x factor-y]
    (remember-transform this #(quil/translate factor-x factor-y)))

  (rot-ccw [this]
    (letfn [(rot-around-center []
              (quil/translate 0.5 0.5)
              (quil/rotate quil/HALF-PI)
              (quil/translate -0.5 -0.5))]
      (remember-transform this rot-around-center)))

  (draw [this picture]
    (quil/stroke-weight (:stroke-weight this))
    (apply-transform-and-draw this picture))

  (line [_ start end]
    (quil/line start end)))

(defn draw [picture]
  (let [initial (QuilCanvas. 1 null-transform)]
    (canvas/draw initial picture)))
