(ns viewer.core
  (:use-macros [c2.util :only [p pp]])
  (:use [c2.core :only [unify!]])
  (:require [c2.dom :as dom]))

(def $res (dom/append! "#main" [:div#results]))

(def timings (js->clj (js/get_timings)
                      :keywordize-keys true))

(unify! $res (pp (group-by :group timings))
        (fn [[group timings]]
          [:div.group
           [:h3 group]
           [:table
            [:tbody
             (map (fn [[src timings]]
                    [:tr
                     [:td.time (pr-str (map :dt timings))]
                     [:td.code [:pre [:code src]]]])
                  (group-by :src timings))]]]))

(js/hljs.initHighlightingOnLoad)
