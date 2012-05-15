(ns test
  (:use-macros [util.macros :only [wrap-form]]
               
               [clojure.core.match.js :only [match]])
  (:require [wrapped.core :as core]
            
            [clojure.string :as string]))


(defn canonicalize
  "Convert hiccup vectors into maps suitable for rendering.
   Hiccup vectors will be converted to maps of {:tag :attr :children}.
   Strings will be passed through and numbers coerced to strings.
   Based on Pinot's html/normalize-element."
  [x]
  (match [x]
         [(str :when string?)] str
         [(n   :when number?)] (str n)
         [(m   :when map?)] m ;;todo, actually check to make sure map has nsp, tag, attr, and children keys
         ;;todo, make explicit match here for attr map and clean up crazy Pinot logic below
         [[tag & content]]   (let [[_ tag id class] (re-matches re-tag (name tag))
                                   [nsp tag]     (let [[nsp t] (string/split tag #":")
                                                       ns-xmlns (xmlns (keyword nsp))]
                                                   (if t
                                                     [(or ns-xmlns nsp) (keyword t)]
                                                     (let [tag (keyword nsp)]
                                                       [(if (svg-tags tag)
                                                          (:svg xmlns)
                                                          (:xhtml xmlns))
                                                        tag])))
                                   tag-attrs        (into {}
                                                          (filter #(not (nil? (second %)))
                                                                  {:id (or id nil)
                                                                   :class (if class (string/replace class #"\." " "))}))
                                   map-attrs        (first content)]

                               (let [[attr raw-children] (if (map? map-attrs)
                                                           [(merge-with #(str %1 " " %2) tag-attrs map-attrs)
                                                            (next content)]
                                                           [tag-attrs content])
                                     ;;Explode children seqs in place
                                     children (mapcat #(if (and (not (vector? %)) (seq? %))
                                                         (map canonicalize %)
                                                         [(canonicalize %)])
                                                      raw-children)]
                                 {:nsp nsp :tag tag :attr attr :children children}))))

(defn grr [x]
  (inc x))

(dotimes [i 10]
  (grr i))
