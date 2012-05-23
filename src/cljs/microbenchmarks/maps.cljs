(ns microbenchmarks.maps
  (:use-macros [util.macros :only [profile p pp]]))

(def a-map {:a 1 :b 2 :c 3})
(defrecord TestRecord [a b c])
(def a-record (TestRecord. 1 2 3))
(def a-set #{:a :b :c :d :e})

(profile {:group "field access" :n 10000} (:a a-map))
(profile {:group "field access" :n 10000} (a-map :a))
(profile {:group "field access" :n 10000} (get a-map :a))
(profile {:group "field access" :n 10000} (contains? a-map :a))

(profile {:group "field access" :n 10000} (:a a-record))
(profile {:group "field access" :n 10000} (a-record :a))
(profile {:group "field access" :n 10000} (get a-record :a))
(profile {:group "field access" :n 10000} (contains? a-record :a))

(profile {:group "field access" :n 10000} (:a a-set))
(profile {:group "field access" :n 10000} (a-set :a))
(profile {:group "field access" :n 10000} (contains? a-set :a))

(profile {:group "destructuring" :n 10000}
         (let [{:keys [a b c]} a-map]))
(profile {:group "destructuring" :n 10000}
         (let [{:keys [a b c]} a-record]))
