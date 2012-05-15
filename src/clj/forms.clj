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
(defn nth-form [] (str "form-" (swap! !n inc)))

(defmacro wrap-form
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
         [([(:or 'ns 'def 'defn 'defmacro
                 :use :require :use-macros ;;need to figure out how to get postwalk to ignore entire branches...
                 )  & _] :seq)] false
                    :else (list? form)))

(defn munge [f]
  (walk/postwalk (fn [x]
                   (if (annotate? x)
                     `(~'wrap-form ~x ~(nth-form))
                     x))
                 (read-file (LineNumberingPushbackReader. (reader f)))))

(defn munge-file!
  "Wraps all forms in f, returning str of forms"
  ([f]
     (reset! !n 0)
     (join "\n" (munge f))))

(defn profile! [f]
  (let [sw (java.io.StringWriter.)]
    (binding [*out* sw]
      (load-file (munge-file! f)))
    (map read-string (split (str sw) #"\n"))))

(defn render-form [x]
  (cond
   (seq? x)      [:span {:id (when (annotate? x) (nth-form))}
                  "(" (interpose "&nbsp;" (map render-form x)) ")"]
   :else (hutil/escape-html (str x))))

(defn file->html [f]
  (reset! !n 0)
  (html
   (map (fn [x] [:p (render-form x)])
        (read-file (LineNumberingPushbackReader. (reader f))))))


(comment
  (set! *print-meta* false)
  #_(profile! f)

  (def f "test.cljs")
  (let [out (munge-file f)
        out-html (file->html f)]
    
    (spit "src/cljs/wrapped/test.cljs"
          (str out "\n\n"
               (prn-str `(core/render ~out-html)))))
  




  ;;core match bug?
  ;;removing the first clause lets second clause match, but when the first is left in we get nil
  (match ['(df x [y] 3)]
         [([(:or 'def 'defn) & _] :seq)] 1
         [(f :when list?)] 2
         :else 11))
