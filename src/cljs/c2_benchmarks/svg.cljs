(ns c2-benchmarks.svg
  (:use-macros [util.macros :only [profile p pp]])
  (:require [goog.dom :as gdom]
            [c2.dom :as dom]
            [c2.scale :as scale]
            [singult.core :as singult]
            [clojure.string :as str]))



(def height 400)
(def width 500)
(def num-bins 100)
(def bin-width (/ num-bins))

(def data (let [scale-x (scale/linear :range [0 width])
                scale-y (scale/linear :range [0 height])
                dx (- (scale-x bin-width) (scale-x 0))]
            
            (for [x (range num-bins)]
              (let [count (rand-int 100)]
                [(scale-x x)
                 (- (scale-x (+ x bin-width))
                    (scale-x x))
                 (scale-y count)]))))

(defn histogram* [data]
  [:div.histogram
   [:svg {:width width :height height}
    [:g.distribution
     (for [[x width height] data]
       [:rect.bar {:x x
                   :width width
                   :height height}])]]])


(defn histogram2* [data]
  [:div.histogram
   [:svg {:width width :height height}
    [:g.distribution
     [:line
      {:d (str/join "L"
                    (for [[x width height] data]
                      (str x ",0"
                           "l0," height
                           "l" width "," 0
                           "l" 0 "," (- height)))
                    )}]]]])


(let [$grr (dom/append! "body" [:div#grr])]
  (profile {:group "svg" :n 20}
           (singult/merge! $grr
                           [:div#grr
                            (histogram* data)])))


(let [$grr (dom/append! "body" [:div#grr])]
  (profile {:group "svg" :n 20}
           (singult/merge! $grr
                           [:div#grr
                            (histogram2* data)])))
