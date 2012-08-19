(ns viewer.core
  (:use-macros [c2.util :only [p pp]])
  (:use [c2.core :only [unify]]
        [c2.maths :only [median]])
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

  ;;C2 gets tripped up by the code highligher changing the markup, so for now just clear everything on redraws.
  (set! (.-innerHTML $res) "")

  (dom/append! $res
               [:div
                (for [[group timings] (grouped-timings)]
                  [:div.group
                   [:h3 group]
                   [:table
                    [:tbody
                     (for [[src ts] (group-by :src timings)]
                       [:tr

                        [:td.time
                         (for [[mode ts] (group-by :mode ts)]
                           [:span {:class (or mode "js")} (median (map :dt ts))])]

                        [:td.code [:pre [:code src]]]])]]])])

  (doseq [$el (dom/select-all "code")]
    (js/hljs.highlightBlock $el)))


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


[:div.group [:h3 "vectors"]
 [:table [:tbody

          [:tr
           [:td.time
            [:div {:class "simple"} 186]
            [:div {:class "advanced"} 48]]
           [:td.code [:pre [:code "(first v)"]]]]
          [:tr [:td.time ([:div {:class "simple"} 19] [:div {:class "advanced"} 7])] [:td.code [:pre [:code "(satisfies? ISeq v)"]]]]
          [:tr [:td.time ([:div {:class "simple"} 183] [:div {:class "advanced"} 57])] [:td.code [:pre [:code "(rest v)"]]]] [:tr [:td.time ([:div {:class "simple"} 207] [:div {:class "advanced"} 68])] [:td.code [:pre [:code "(next v)"]]]]]]]
