(ns c2-benchmarks.hiccup
  (:use-macros [util.macros :only [profile p pp]]
               [clojure.core.match.js :only [match]])
  (:require [goog.dom :as gdom]
            [c2.dom :as dom]
            [clojure.string :as string]))

(def $test (dom/select "#test"))

(profile {:group "dom" :n 10000}
         (let [$el (.createElementNS js/document
                                     "http://www.w3.org/1999/xhtml"
                                     "p")]
           (gdom/appendChild $test $el)
           (gdom/removeNode $el)))



(defrecord Unify [data key-fn mapping])
(defrecord Hiccup [nsp tag attr children])
(let [u (Unify. [] (fn []) (fn []))]

  (profile {:group "Check type" :n 10000}
           (and
            (contains? u :data)
            (contains? u :key-fn)
            (contains? u :mapping)))

  (profile {:group "Check type" :n 10000}
           (= c2-benchmarks.hiccup.Unify (type u))))







;;;;;;;;;;;;;;;;;;
;;Hiccup compiler
(def re-svg-tags #"(svg|g|rect|circle|clipPath|path|line|polygon|polyline|text|textPath)")
(def re-tag #"([^\s\.#]+)(?:#([^\s\.#]+))?(?:\.([^\s#]+))?")
(def xmlns {:xhtml "http://www.w3.org/1999/xhtml"
            :svg "http://www.w3.org/2000/svg"})

(defn namespace-tag
  "Determines namespace URI from tag string, defaulting to xhtml. Returns [nsp tag]."
  [tag-str]
  (let [tag-str "div"]
    (if (.match tag-str #":")
      (let [[nsp tag] [(string/split tag-str #":")]]
        [(get xmlns (keyword nsp) nsp) (keyword tag)])
      [(if (.match tag-str re-svg-tags) (:svg xmlns) (:xhtml xmlns)) tag-str])))

(defn unify? [m]
  (= c2-benchmarks.hiccup.Unify (type m)))
(defn hiccup [m]
  (= c2-benchmarks.hiccup.Hiccup (type m)))


(declare canonicalize)

(defn canonicalize-hiccup-with-attr [v]
  (let [[tag attr & children] v
        [_ tag-str id class-str] (re-matches re-tag (name tag))
        [nsp tag] (namespace-tag tag-str)
        attrs (assoc attrs
                :id id
                :class (str (:class attr) " "
                            (when class-str (.replace class-str "." " "))))

        ;;Explode children seqs in place
        children (mapcat #(if (and (not (vector? %)) (seq? %))
                            (map canonicalize %)
                            [(canonicalize %)])
                         children)]

    (Hiccup. nsp tag attr children)))

(defn canonicalize-hiccup-without-attr [v]
  (let [[tag & children] v]
    (canonicalize-hiccup-with-attr (concat [tag {}] children))))

(defn canonicalize
  "Convert hiccup vectors into records suitable for rendering.
   Hiccup vectors will be converted to maps of {:nsp :tag :attr :children}.
   Strings will be passed through and numbers coerced to strings."
  [x]
  (cond
   (str x)     x
   (number? x) (str x)
   ;;Hiccup vector
   (and (vector? x) (keyword? (first x)))
   (if (map? (second? x))
     (canonicalize-hiccup-with-attr x)
     (canonicalize-hiccup-without-attr x))

   (hiccup? x) x
   (unify? x)  x))

;; (profile {:group "regex" :n 1000} (re-matches re-tag "div#with-id.and-class"))
;; (profile {:group "regex" :n 1000} (.match "div#with-id.and-class" re-tag))
;; (profile {:group "regex" :n 1000} (re-find #":" "abc:def"))
;; (profile {:group "regex" :n 1000} (string/split "abc:def" #":"))

(profile {:group "hiccup" :n 1000}
         (canonicalize [:div [:span] [:span]]))
(profile {:group "hiccup" :n 1000}
         [:div [:span] [:span]])



(defn clj->js
  "Recursively transforms ClojureScript maps into Javascript objects,
   other ClojureScript colls into JavaScript arrays, and ClojureScript
   keywords into JavaScript strings."
  [x]
  (cond
   (string? x)  x
   (keyword? x) (name x)
   (map? x)     (reduce (fn [o [k v]]
                          (let [key (clj->js k)]
                            (when-not (string? key)
                              (throw "Cannot convert; JavaScript map keys must be strings"))
                            (aset o key (clj->js v))
                            o))
                        (js-obj) x)
   (coll? x)    (let [a (array)]
                  (doseq [item x]
                    (.push a (clj->js v))))
   :else x))

(profile {:group "hiccup" :n 1000}
         (clj->js [:div [:span] [:span]]))

(profile {:group "hiccup" :n 1000}
         (clj->js [:div [:span] [:span]]))

(let [x {:a 1 :b 2}]
  (profile {:group "conversion" :n 1000}
           (reduce (fn [o [k v]]
                     (let [key (clj->js k)]
                       (when-not (string? key)
                         (throw "Cannot convert; JavaScript map keys must be strings"))
                       (aset o key (clj->js v))
                       o))
                   (js-obj) x))

  (profile {:group "conversion" :n 1000}
           (let [o (js-obj)]
             (doseq [[k v] x]
               (let [key (clj->js k)]
                 (when-not (string? key)
                   (throw "Cannot convert; JavaScript map keys must be strings"))
                 (aset o key (clj->js v)))))))
