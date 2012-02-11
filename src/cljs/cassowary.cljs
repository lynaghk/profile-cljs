(ns cassowary
  (:refer-clojure :exclude [+ - =])
  (:use-macros [util :only [profile p]])
  (:use [cassowary.core :only [+ - = cvar constrain! stay! simplex-solver]]))


(defn *print-fn* [x]
  (.log js/console x))


(let [height 200, width 800, max-radius 40
      solver  (simplex-solver)
      spacing (cvar 0) ;;The spacing between circles (to be solved for)
      circles (repeatedly 10 #(hash-map :r  (cvar (* max-radius (rand)))
                                        :cx (cvar 0)
                                        :cy (cvar (/ height 2))))]

  ;;The circle radii and vertical positions are constants
  (doseq [c circles]
    (stay! solver (:r c))
    (stay! solver (:cy c)))

  ;;Spacing between first circle and the wall
  (constrain! solver (= 0 (- (:cx (first circles))
                             (:r (first circles))
                             spacing)))

  ;;Spacing between each pair of neighboring circles
  (doseq [[left right] (partition 2 1 circles)]
    (constrain! solver (= spacing (- (:cx right)
                                     (:r right)
                                     (+ (:cx left) (:r left))))))

  ;;Spacing between last circle and the wall
  (constrain! solver (= spacing (- width
                                   (:cx (last circles))
                                   (:r (last circles)))))

  (p (.value (:cx (first circles))))

  )

