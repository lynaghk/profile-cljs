(require '[cljs.closure :as closure])

(closure/build "src/cljs/test1.cljs" {:optimizations :simple
                                      :output-dir "out/"
                                      :output-to "out/compiled.js"})
