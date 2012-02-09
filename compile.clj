(require '[cljs.closure :as closure])

(closure/build "src/cljs/cassowary.cljs" {:optimizations :advanced
                                          :output-dir "out/advanced/"
                                          :output-to "out/advanced/compiled.js"
                                          :libs [""]})

(closure/build "src/cljs/cassowary.cljs" {:optimizations :simple
                                          :output-dir "out/simple/"
                                          :output-to "out/simple/compiled.js"
                                          :libs [""]})


