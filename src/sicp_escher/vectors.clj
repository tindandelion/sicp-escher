(ns sicp-escher.vectors)

(defn add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn scale-vect [[x y] factor]
  [(* factor x) (* factor y)])

(defn map-to-frame [{origin :origin e1 :e1 e2 :e2} [x y]]
  (add-vect
    origin
    (add-vect (scale-vect e1 x) (scale-vect e2 y))))



