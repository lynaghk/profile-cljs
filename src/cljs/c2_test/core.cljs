(ns c2-test.core
  (:use-macros [util.macros :only [profile p pp]]))

(profile "count up"
         (doseq [i (range 100000)]))
