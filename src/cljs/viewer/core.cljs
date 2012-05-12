(ns viewer.core
  (:use-macros [c2.util :only [p pp]])
  (:use [c2.core :only [unify!]])
  (:require [c2.dom :as dom]
            [c2.event :as event]))

(def $res (dom/append! "#main" [:div#results]))
(def $reset (dom/select "#reset"))
(def $run (dom/select "#run"))

(defn grouped-timings []
  (group-by :group
            (js->clj (js/get_timings)
                     :keywordize-keys true)))

(defn run!
  "Runs benchmarks via iFrame, calling callback."
  [callback]
  (let [$iframe (dom/select "#sandbox")]
    (dom/attr $iframe {:src "run.html"})
    (dom/attr $run :disabled true)
    (set! (.-onload $iframe) (fn [e]
                               (dom/attr $run :disabled nil)
                               (callback e)))))

(defn draw! []
  (unify! $res (grouped-timings)
          (fn [[group timings]]
            [:div.group
             [:h3 group]
             [:table
              [:tbody
               (map (fn [[src timings]]
                      [:tr
                       [:td.time (pr-str (map :dt timings))]
                       [:td.code [:pre [:code src]]]])
                    (group-by :src timings))]]])
          :key-fn (fn [[group timings]] group)))


;;Buton handlers
(event/on-raw $reset :click
              (fn [e]
                (js/reset_timings)
                (draw!)))

(event/on-raw $run :click
              (fn [_]
                (run! (fn [_] (draw!)))))

;;initial draw
(draw!)

(js/hljs.initHighlightingOnLoad)
