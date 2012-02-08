(ns test-jar
  (:require [Naked :as naked]
            [WithScope :as with-scope]
            [WithoutScope :as without-scope]))

(defn p [x] (.log js/console x))

(p (naked/echo "grr"))
(p (with-scope/echo "grr"))
(p (without-scope/echo "grr"))
