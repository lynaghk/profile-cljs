(ns microbenchmarks.seqs
  (:use-macros [util.macros :only [profile p pp]]))

(profile "count up"
         (doseq [i (range 100000)]))

(profile "map inc"
         (doall (map inc (range 100000))))
