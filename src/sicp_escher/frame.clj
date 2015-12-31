(ns sicp-escher.frame)

(defn- add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn- scale-vect [[x y] [factor-x factor-y]]
  [(* factor-x x) (* factor-y y)])

(defn- sub-vect [[x1 y1] [x2 y2]]
  [(- x1 x2) (- y1 y2)])

(defn map-vector [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect
      (scale-vect e1 [x x])
      (scale-vect e2 [y y]))))

(defn frame-transform [origin corner-left corner-btm]
  (fn [frame]
    (let [new-origin (map-vector frame origin)]
      {:origin new-origin
       :e1     (sub-vect (map-vector frame corner-left) new-origin)
       :e2     (sub-vect (map-vector frame corner-btm) new-origin)})))

(defprotocol Transformable
  (transform [this {:keys [origin corner-1 corner-2]}]))

