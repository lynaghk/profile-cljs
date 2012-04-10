(require '[cljs.closure :as closure])

(closure/build "src/cljs/grr.cljs" {:optimizations :simple
                                    :pretty-print true
                                    :output-dir "out/"
                                    :output-to "out/compiled.js"})

