(ns sicp-escher.data.svg
  (:require [quil.core :as quil])
  (:require [sicp-escher.transforms :refer :all]))

(defn svg [filename]
  (let [shape (quil/load-shape filename)]
    (fn [_] (quil/shape shape 0 0 1.0 1.0))))

(defn t [] (svg "resources/t.svg"))

(defn u []
  (let [q (svg "resources/q.svg")]
    (cycled-quartet (rot-ccw q))))
