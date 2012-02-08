(require '[cljs.closure :as closure])

;;This doesn't work, need to use :libs
;;The reason Pinot got away with using goog.jar is because goog/deps.js is a hardcoded lib in the ClojureScript compiler.
#_(closure/build "src/cljs/test-jar.cljs" {:optimizations :simple
                                         :output-dir "no-libs/"
                                         :output-to "no-libs/compiled.js"})


(closure/build "src/cljs/test-jar.cljs" {:optimizations :simple
                                         :output-dir "with-libs/"
                                         :output-to "with-libs/compiled.js"
                                         ;;Empty string works just as well as list of JS files
                                         ;;Comment on clojurescript/src/clj/cljs/closure.clj#js-dependencies says that :libs works with path to directories, so "" must be resolving to the JAR root.
                                         :libs [""] 
                                         ;; :libs ["Naked.js"
                                         ;;        "NakedScoped.js"
                                         ;;        "WithScope.js"
                                         ;;        "WithoutScope.js"]
                                         })
