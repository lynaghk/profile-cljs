(ns cassowary-demo
  (:refer-clojure :exclude [+ - * =])
  (:use [cassowary.core :only [+ - = * cvar value constrain! unconstrain! simplex-solver]]))

(defn *print-fn* [x] (.log js/console x))

(let [solver  (simplex-solver)
      vars    (for [_ (range 5)] (cvar 0))]

  ;;Relation between consecutive pairs of variables
  (doseq [[a b] (partition 2 1 vars)]
    (constrain! solver (= b (* 2 a))))
  
  (let [c1 (= 1 (first vars))
        c2 (= -5 (first vars))]
    
    (constrain! solver c1)
    (print (pr-str (map value vars))) ;=> (1 2 4 8 16)
    
    (unconstrain! solver c1)
    (constrain! solver c2)
    (print (pr-str (map value vars))))) ;=> (-5 -10 -20 -40 -80)




#_(let [height 200, width 800, max-radius 40
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

  (p (value (:cx (first circles))))

  )

