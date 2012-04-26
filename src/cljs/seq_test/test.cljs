(ns seq-test.test
  (:use-macros [util.macros :only [profile p pp]]
               [iterate :only [iter]]))

(extend-type array
  ICollection
  (-conj [a x]
    (.push a x)
    a))

(let [n 100000
      l (range n)]
  (profile "counting first time"
           (doseq [i l]))
  
  (profile "counting second time"
           (doseq [i l]))
  
  (profile "count using loop/recur via iter rather than doseq"
           (iter {for i in l}))

  
  
  (let [a (profile "allocating js array" (js* "new Array(~{n})"))]
    (profile "doseq over js-array"
             (doseq [i a]))
    (profile "foreach over js-array"
             (.forEach a #())))


  (let [a (profile "realized seq into js array" (into (array) l))]
    (profile "doseq over js-array"
             (doseq [i a]))
    (profile "foreach over js-array"
             (.forEach a #())))

  ;; (profile "maps"
  ;;          (doseq [i (map #(hash-map :a 1 :b 2 :i %) l)]))

  ;; (profile "maps, destructured"
  ;;          (doseq [{:keys [a b i]} (map #(hash-map :a 1 :b 2 :i %) l)]))

  ;; (profile "vectors"
  ;;          (doseq [i (map #(vector 1 %) l)]))

  ;; (profile "vectors, destructured"
  ;;          (doseq [[i j] (map #(vector 1 %) l)]))



  )











