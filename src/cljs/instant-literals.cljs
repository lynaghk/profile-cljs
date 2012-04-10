;;Look at this another time...
(comment
  (defn pad [n] (if (< n 10)
                  (str "0" n)
                  (str n)))

  (defn iso-date [js-date]
    (str (pad (.getUTCFullYear js-date)) "-"
         (pad (inc (.getUTCMonth js-date))) "-"
         (pad (.getUTCDate js-date))
         "T"
         (pad (.getUTCHours js-date)) ":"
         (pad (.getUTCMinutes js-date)) ":"
         (pad (.getUTCSeconds js-date))
         "Z"))
  
  (extend-type js/Date
    IPrintable
    (-pr-seq [js-date]
      (list (str "#inst \"" (iso-date js-date) "\"")))))
