(ns microbenchmarks.dom
  (:use-macros [util.macros :only [profile p pp]]
               [clojure.core.match.js :only [match]])
  (:require [goog.dom :as gdom]
            [clojure.string :as string]))

(def $test (.querySelector js/document "#test"))

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


