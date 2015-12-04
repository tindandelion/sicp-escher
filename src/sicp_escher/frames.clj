(ns sicp-escher.frame)

(defn- add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn- scale-vect [[x y] factor]
  [(* factor x) (* factor y)])

(defn make-frame [origin edge1 edge2]
  {:origin origin :e1 edge1 :e2 edge2})

(defn map-vector [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect
      (scale-vect e1 x)
      (scale-vect e2 y))))

(defn flip-vert [{[org-x org-y] :origin e1 :e1 [e2-x e2-y] :e2}]
  {:origin [org-x (+ org-y e2-y)]
   :e1 e1
   :e2 [e2-x (- e2-y)]})

(defn split-vert [frame]
  [frame frame])


