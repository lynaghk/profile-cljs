(ns microbenchmarks.maps
  (:use-macros [util.macros :only [profile p pp]]))

(def a-map {:a 1})
(defrecord TestRecord [a])
(def a-record (TestRecord. 1))

(profile {:group "field access" :n 10000} (:a a-map))
(profile {:group "field access" :n 10000} (a-map :a))
(profile {:group "field access" :n 10000} (get a-map :a))
(profile {:group "field access" :n 10000} (:a a-record))
