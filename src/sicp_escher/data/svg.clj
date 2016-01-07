(ns sicp-escher.data.svg
  (:require [quil.core :as quil]))

(defn svg [filename]
  (let [shape (quil/load-shape filename)]
    (fn [_] (quil/shape shape 0 0 1.0 1.0))))

(defn t []
  (svg "resources/t.svg"))
