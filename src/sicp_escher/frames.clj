(ns sicp-escher.frame)

(defn- add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn- scale-vect [[x y] [factor-x factor-y]]
  [(* factor-x x) (* factor-y y)])

(defn make-frame [origin edge1 edge2]
  {:origin origin :e1 edge1 :e2 edge2})

(defn map-vector [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect
      (scale-vect e1 [x x])
      (scale-vect e2 [y y]))))

(defn flip-vert [{origin :origin e1 :e1 [e2-x e2-y] :e2}]
  {:origin (add-vect origin [0 e2-y])
   :e1     e1
   :e2     (scale-vect [e2-x e2-y] [1 -1])})

(defn scale [{origin :origin e1 :e1 e2 :e2} factor]
  {:origin origin
   :e1     (scale-vect e1 factor)
   :e2     (scale-vect e2 factor)})

(defn move-by [frame increment]
  (update frame :origin add-vect increment))





