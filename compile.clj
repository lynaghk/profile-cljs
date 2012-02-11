(require '[cljs.closure :as closure])

#_(closure/build "src/cljs/cassowary.cljs" {:optimizations :advanced
                                            :output-dir "out/advanced/"
                                            :output-to "out/advanced/compiled.js"
                                            :libs [""]})

(closure/build "src/cljs/cassowary.cljs" {:optimizations :simple
                                          :pretty-print true
                                          :output-dir "out/"
                                          :output-to "out/compiled.js"
                                          :libs [""]})


