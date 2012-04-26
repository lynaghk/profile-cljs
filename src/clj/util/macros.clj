(ns util.macros)


(defmacro p [x]
  `(let [res# ~x]
     (.log js/console res#)
     res#))

(defmacro pp [x]
  `(let [res# ~x]
     (.log js/console (prn-str res#))
     res#))

(defmacro profile [descr & body]
  `(let [start# (.getTime (js/Date.))
         ret# (do ~@body)]
     (p (str ~descr ": " (- (.getTime (js/Date.)) start#) " msecs"))
     ret#))
