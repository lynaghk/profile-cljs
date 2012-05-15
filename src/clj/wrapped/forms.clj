(ns wrapped.forms
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

#_(defmacro wrap-form
  "Evaluates expr and returns result.
   Emits the time it took with metadata about the form itself to the output stream that is the current value of *gprof-reporter*."
  [form name]
  `(let [start# (. java.lang.System nanoTime)
         res# ~form]
     (prn {:name ~name
           :time (* 1e-6 (- (. java.lang.System nanoTime) start#))})
     res#))

(defmacro wrap-form
  "Evaluates expr and returns result.
   Writes the time it took with form name to global !timing atom, assumed to exist"
  [form name]
  `(let [start# (.getTime (js/Date.))
         res# ~form]
     (swap! ~'wrapped.core/!timings #(conj % {:name ~name
                                             :time (- (.getTime (js/Date.)) start#)}))
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

(defn munge [forms]
  (walk/postwalk (fn [x]
                   (if (annotate? x)
                     (wrap-form x (nth-form)) 
                     x))
                 forms
                 #_(map walk/macroexpand-all forms)))


(defn munge-file!
  "Wraps all forms in f, returning str of forms"
  ([f]
     (reset! !n 0)
     (join "\n" (munge (read-file (LineNumberingPushbackReader. (reader f)))))))



(defn render-form [x]
  (cond
   (seq? x)      [:span {:id (when (annotate? x) (nth-form))}
                  "(" (interpose "&nbsp;" (map render-form x)) ")"]
   :else (hutil/escape-html (str x))))

(defn forms->html [forms]
  (reset! !n 0)
  (html
   (map (fn [x] [:p (render-form x)])
        forms)))

(defn file->html [f]
  (forms->html (read-file (LineNumberingPushbackReader. (reader f)))))



(defn profile-cljs! [f]
  (let [out (munge-file! f)
        out-html (file->html f)]

    (spit "src/cljs/wrapped/test.cljs"
          (str out "\n\n"
               (prn-str `(core/render ~out-html))))))


(defmacro profile-forms[& forms]
  `(do
     ~@(munge forms)
     (core/render ~(forms->html forms))))





(comment
  (set! *print-meta* false)
  #_(profile! f)

  (profile-cljs! "test.cljs")
  (munge-file "test.cljs")


  ;;for use with clj code...
  (defn profile! [f]
    (let [sw (java.io.StringWriter.)]
      (binding [*out* sw]
        (load-file (munge-file! f)))
      (map read-string (split (str sw) #"\n"))))


  ;;core match bug?
  ;;removing the first clause lets second clause match, but when the first is left in we get nil
  (match ['(df x [y] 3)]
         [([(:or 'def 'defn) & _] :seq)] 1
         [(f :when list?)] 2
         :else 11))
