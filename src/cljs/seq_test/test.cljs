(ns seq-test.test
  (:use-macros [util.macros :only [profile p pp]]))

(profile "count to 100,000"
         (doall (range 100000)))

(profile "100,000, counting"
         (doseq [i (range 100000)]))

(profile "100,000 maps"
         (doseq [i (map #(hash-map :a 1 :b 2 :i %) (range 100000))]))

(profile "100,000 maps, destructured"
         (doseq [{:keys [a b i]} (map #(hash-map :a 1 :b 2 :i %) (range 100000))]))

(profile "100,000 vectors"
         (doseq [i (map #(vector 1 %) (range 100000))]))

(profile "100,000 vectors, destructured"
         (doseq [[i j] (map #(vector 1 %) (range 100000))]))
