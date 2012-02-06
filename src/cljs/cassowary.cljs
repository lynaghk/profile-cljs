(ns cassowary
  (:use-macros [util :only [profile p]])
  (:require [Cl :as _]
            [Cl.SimplexSolver :as _]
)
  #_(:use [cassowary.core :only [+ - = cvar constrain! stay! simplex-solver]]))


(defn *print-fn* [x]
  (.log js/console x))


(p  (Cl.SimplexSolver.))

#_(let [solver (simplex-solver)
      x  (cvar 0)
      two-x  (cvar 0)]

  (profile "solve simple constraint"
           (constrain! solver (= two-x (* 2 x)))
           (stay! solver x 10)
           (p (.value x))))
