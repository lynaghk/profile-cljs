(ns test)

(defn grr [x]
  (inc x))

(dotimes [i 10]
  (grr i))
