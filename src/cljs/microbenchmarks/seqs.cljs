(ns microbenchmarks.seqs
  (:use-macros [util.macros :only [profile p pp]]))

(profile "count up"
         (doseq [i (range 100000)]))

(profile "map inc"
         (doall (map inc (range 100000))))

(let [l (list 1 2 3)]
  (profile {:group "lists" :n 100000} (satisfies? ISeq l))
  (profile {:group "lists" :n 100000} (first l))
  (profile {:group "lists" :n 100000} (-first l))
  (profile {:group "lists" :n 100000} (rest l))
  (profile {:group "lists" :n 100000} (next l)))

(let [v [1 2 3]]
  (profile {:group "vectors" :n 100000} (satisfies? ISeq v))
  (profile {:group "vectors" :n 100000} (first v))
  (profile {:group "vectors" :n 100000} (rest v))
  (profile {:group "vectors" :n 100000} (next v)))

;; Regular expression that parses a CSS-style id and class from a tag name. From Weavejester's Hiccup.
(def re-tag #"([^\s\.#]+)(?:#([^\s\.#]+))?(?:\.([^\s#]+))?")
(profile {:group "regex" :n 100000} (re-matches re-tag "div#with-id.and-class"))
(profile {:group "regex" :n 100000} (.match "div#with-id.and-class" re-tag))


