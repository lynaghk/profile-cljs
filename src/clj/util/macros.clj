(ns util.macros
  (:use [clojure.string :only [join]]))


(defmacro p [x]
  `(let [res# ~x]
     (.log js/console res#)
     res#))

(defmacro pp [x]
  `(let [res# ~x]
     (.log js/console (prn-str res#))
     res#))

(defmacro profile [opts & body]
  (let [timing (merge (if (string? opts) {:group opts} opts)
                    {:lang "cljs"
                     :src (join "\n" (map pr-str body))})]
    `(let [start# (.getTime (js/Date.))
           ret# (do ~@body)
           dt# (- (.getTime (js/Date.)) start#)]
       
       (js/add_timing (apply ~'js-obj
                             (flatten (map (fn [[k# v#]] [(name k#) v#])
                                           (assoc ~timing :dt dt#)))))
       ret#)))
