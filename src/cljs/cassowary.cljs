(ns cassowary
  (:use-macros [util :only [profile p]])
  (:require [Cl :as Cl]
            [Cl.CL :as CL])
  
  #_(:use [cassowary.core :only [+ - = cvar constrain! stay! simplex-solver]]))


(defn *print-fn* [x]
  (.log js/console x))

(p Cl/version)
(p CL/GEQ)
(p (CL/approx 1 1))

#_(let [solver (simplex-solver)
      x  (cvar 0)
      two-x  (cvar 0)]

  (profile "solve simple constraint"
           (constrain! solver (= two-x (* 2 x)))
           (stay! solver x 10)
           (p (.value x))))
