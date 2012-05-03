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
         ret# (do ~@body)
         dt# (- (.getTime (js/Date.)) start#)]
     
     #_(p (str ~descr ": " dt# " msecs"))
     
     (js/add_timing ~descr
                    ~(pr-str body)
                    "cljs"
                    dt#)
     ret#))
