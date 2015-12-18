(ns sicp-escher.data
  (:require [sicp-escher.frame :as frame]
            [sicp-escher.core :as core]))

(defn grid->picture [{:keys [dimen segments]} draw-line-fn]
  (letfn [(seg->picture [segments]
            (fn [frame]
              (doseq [[start end] segments]
                (draw-line-fn
                  (frame/map-vector frame start)
                  (frame/map-vector frame end)))))]
    (core/scale (/ 1 dimen) (seg->picture segments))))

(defn- grid [dimen segments]
  {:dimen dimen :segments segments})

(def p
  (grid 16
        [[[4 4] [6 0]] [[0 3] [3 4]] [[3 4] [0 8]]
         [[0 8] [0 3]] [[4 5] [7 6]] [[7 6] [4 10]]
         [[4 10] [4 5]] [[11 0] [10 4]] [[10 4] [8 8]]
         [[8 8] [4 13]] [[4 13] [0 16]] [[11 0] [14 2]]
         [[14 2] [16 2]] [[10 4] [13 5]] [[13 5] [16 4]]
         [[9 6] [12 7]] [[12 7] [16 6]] [[8 8] [12 9]]
         [[12 9] [16 8]] [[8 12] [16 10]] [[0 16] [6 15]]
         [[6 15] [8 16]] [[8 16] [12 12]] [[12 12] [16 12]]
         [[10 16] [12 14]] [[12 14] [16 13]] [[12 16] [13 15]]
         [[13 15] [16 14]] [[14 16] [16 15]]]))
