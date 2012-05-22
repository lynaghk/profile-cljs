(ns microbenchmarks.dom
  (:use-macros [util.macros :only [profile p pp]]
               [clojure.core.match.js :only [match]])
  (:require [goog.dom :as gdom]
            [clojure.string :as string]))

(def $test (.querySelector js/document "#test"))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;Test passthrough google closure

(profile {:group "dom" :n 10000}
         (let [$el (.createElementNS js/document
                                     "http://www.w3.org/1999/xhtml"
                                     "p")]
           (gdom/appendChild $test $el)
           (gdom/removeNode $el)))

(profile {:group "dom" :n 10000}
         (let [$el (.createElementNS js/document
                                     "http://www.w3.org/1999/xhtml"
                                     "p")]
           (.appendChild $test $el)
           (.removeChild $test $el)))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;Test looping over children

(dotimes [i 10000]
  (let [$el (.createElementNS js/document
                              "http://www.w3.org/1999/xhtml"
                              "p")]
    (.appendChild $test $el)))

(extend-type js/NodeList
           ISeqable
           (-seq [array] (array-seq array 0)))

(extend-type js/HTMLCollection
           ISeqable
           (-seq [array] (array-seq array 0)))

(profile {:group "dom"}
         (doseq [$d (.-children $test)]))

(profile {:group "dom"}
         (let [$children (.-children $test)
               n (.-length $children)]
           (loop [idx 0]
             (aget $children idx)
             (when (< idx n)
               (recur (inc idx))))))


(set! (.-innerHTML $test) "")


