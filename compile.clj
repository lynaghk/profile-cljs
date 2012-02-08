(require '[cljs.closure :as closure])

(closure/build "src/cljs/test-jar.cljs" {:optimizations :simple
                                         :output-dir "no-libs/"
                                         :output-to "no-libs/compiled.js"})


(closure/build "src/cljs/test-jar.cljs" {:optimizations :simple
                                         :output-dir "with-libs/"
                                         :output-to "with-libs/compiled.js"
                                         :libs ["Naked.js"
                                                "WithScope.js"
                                                "WithoutScope.js"]})
