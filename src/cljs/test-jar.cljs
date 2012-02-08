(ns test-jar
  (:require [Naked :as naked]
            [NakedScoped :as naked-scoped]
            [WithScope :as with-scope]
            [WithoutScope :as without-scope]))

(defn p [x] (.log js/console x))
(try
  (p (naked/echo "grr"))
  (catch js/Error e
    (p "Naked without scope: NOPE")))

(try
  (p (naked-scoped/echo "grr"))
  (catch js/Error e
    (p "NakedScoped without scope: NOPE")))

(try
  (p (with-scope/echo "grr"))
  (catch js/Error e
    (p "wrapped-with-scope: NOPE")))

(try
  (p (without-scope/echo "grr"))
  (catch js/Error e
    (p "wrapped-without-scope: NOPE")))
