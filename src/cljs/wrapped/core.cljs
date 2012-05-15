(ns wrapped.core
  (:use-macros [c2.util :only [p pp]])
  (:use [c2.maths :only [median extent]])
  (:require [c2.dom :as dom]
            [c2.scale :as scale]))

(def !timings (atom []))


(defn render [html-str]
  (set! (.-innerHTML (dom/select "body"))
        html-str)


  (let [timings (group-by :name @!timings)
        s (scale/linear :domain (extent (->> (vals timings)
                                             (map #(map :time %))
                                             (map median)))
                        :range [100 0])]
    (doseq [[id ts] timings]

      (let [$el (dom/select (str "#" id))
            lightness (s (median (map :time ts)))]
        (dom/style $el {:background-color (str "hsl(0,0%," lightness "%)")
                        :color (if (> lightness 50) "black" "white")})))))
