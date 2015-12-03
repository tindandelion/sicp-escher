(ns sicp-escher.vectors)

(defn add-vect [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn scale-vect [[x y] factor]
  [(* factor x) (* factor y)])


