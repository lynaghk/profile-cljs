(ns dom
  (:use-macros [util :only [p profile]]))

(def xhtml "http://www.w3.org/1999/xhtml")
(def body (.-body js/document))

(profile "Add 100 <p> using doseq"
         (doseq [i (range 100)]
           (.appendChild body
                         (.createElementNS js/document xhtml "p"))))
