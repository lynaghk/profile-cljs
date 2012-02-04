(ns util)

(defmacro profile [descr & body]
  `(let [start# (.getTime (js/Date.) ())
         ret# (do ~@body)]
    (print (str ~descr ": " (- (.getTime (js/Date.) ()) start#) " msecs"))
    ret#))

(defmacro p [x]
  `(do (.log js/console ~x)
       ~x))
