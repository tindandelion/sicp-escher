(ns sicp-escher.frame)

(defn- add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn- scale-vect [[x y] [factor-x factor-y]]
  [(* factor-x x) (* factor-y y)])

(defn map-vector [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect
      (scale-vect e1 [x x])
      (scale-vect e2 [y y]))))

(defn scale [frame factor]
  (-> frame
      (update :e1 scale-vect factor)
      (update :e2 scale-vect factor)))

(defn- shift-by-x [frame increment]
  (update frame :origin add-vect [increment 0]))

(defn- shift-by-y [frame increment]
  (update frame :origin add-vect [0 increment]))

(defn flip-vert [frame]
  (-> frame
      (shift-by-y ((:e2 frame) 1))
      (update :e2 scale-vect [1 -1])))

(defn flip-horz [frame]
  (-> frame
      (shift-by-x ((:e1 frame) 0))
      (update :e1 scale-vect [-1 1])))


(defn split-horz [frame]
  (let [scaled (scale frame [0.5 1])
        shift-x ((:e1 scaled) 0)]
    [scaled (shift-by-x scaled shift-x)]))

(defn split-vert [frame]
  (let [scaled (scale frame [1 0.5])
        shift-y ((:e2 scaled) 1)]
    [scaled (shift-by-y scaled shift-y)]))





