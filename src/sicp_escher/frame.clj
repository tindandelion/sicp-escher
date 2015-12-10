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

(defn make-transform [origin corner-left corner-btm]
  (fn [frame]
    (let [new-origin (map-vector frame origin)]
      {:origin new-origin
       :e1     (sub-vect (map-vector frame corner-left) new-origin)
       :e2     (sub-vect (map-vector frame corner-btm) new-origin)})))

(defn scale [frame factor]
  (-> frame
      (update :e1 scale-vect factor)
      (update :e2 scale-vect factor)))

(defn- shift-by-x [frame increment]
  (update frame :origin add-vect [increment 0]))

(defn- shift-by-y [frame increment]
  (update frame :origin add-vect [0 increment]))


(defn split-horz [frame]
  (let [scaled (scale frame [0.5 1])
        shift-x ((:e1 scaled) 0)]
    [scaled (shift-by-x scaled shift-x)]))

(defn split-vert [frame]
  (let [scaled (scale frame [1 0.5])
        shift-y ((:e2 scaled) 1)]
    [scaled (shift-by-y scaled shift-y)]))





