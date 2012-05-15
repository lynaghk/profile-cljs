(ns forms
  (:use [clojure.java.io :only [reader]]
        [clojure.core.match :only [match]]
        [clojure.string :only [join split]]
        [hiccup.core :only [html]])
  (:require [clojure.walk :as walk]
            [hiccup.util :as hutil])
  (:import [clojure.lang LineNumberingPushbackReader]))

;;Counter used to give a unique ID to each form
(def !n (atom 0))
(defn nth-form [] (swap! !n inc))

(defmacro wrap-time
  "Evaluates expr and returns result.
   Emits the time it took with metadata about the form itself to the output stream that is the current value of *gprof-reporter*."
  [form name]
  `(let [start# (. java.lang.System nanoTime)
         res# ~form]
     (prn {:name ~name
           :time (* 1e-6 (- (. java.lang.System nanoTime) start#))})
     res#))

;;taken from kibit
(defn read-file
  "Generate a lazy sequence of top level forms from a
  LineNumberingPushbackReader"
  [^LineNumberingPushbackReader r]
  (lazy-seq
   (let [form (read r false ::eof)]
     (when-not (= form ::eof)
       (cons form (read-file r))))))


(defn annotate? [form]
  (match [form]
         [([(:or 'ns 'def 'defn 'defmacro) & _] :seq)] false
         :else (list? form)))

(defn munge [f]
  (walk/postwalk (fn [x]
                   (if (annotate? x)
                     `(wrap-time ~x ~(nth-form))
                     x))
                 (read-file (LineNumberingPushbackReader. (reader f)))))

(defn profile! [f]
  (let [tmp-f "out.cljs"
        sw (java.io.StringWriter.)]
    (spit tmp-f (join "\n" (munge f)))
    (binding [*out* sw]
      (load-file tmp-f))
    (map read-string (split (str sw) #"\n"))))


(defn escape-html [s]
  (hutil/escape-html s))

(comment
  (set! *print-meta* false)
  (def f "test.cljs")
  (reset! !n 0)
  (profile! f)
  (reset! !n 0)

  (defn r [x]
    (cond
     (seq? x)      [:span {:style (when (annotate? x) "color: blue;")}
                    "(" (interpose "&nbsp;" (map r x)) ")"]
     :else (escape-html (str x))))

  (spit "grr.html"
        (html
         (map (fn [x] [:p (r x)])
              (read-file (LineNumberingPushbackReader. (reader f))))))

  ;;core match bug?
  ;;removing the first clause lets second clause match, but when the first is left in we get nil
  (match ['(df x [y] 3)]
         [([(:or 'def 'defn) & _] :seq)] 1
         [(f :when list?)] 2
         :else 11))
