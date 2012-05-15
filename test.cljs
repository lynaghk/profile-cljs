(ns test
  (:use-macros [util.macros :only [wrap-form]])
  (:require [wrapped.core :as core]))

(defn grr [x]
  (inc x))

(dotimes [i 10]
  (grr i))
