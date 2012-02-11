(require '[cljs.closure :as closure])

(closure/build "src/cljs" {:optimizations :simple
                           :output-dir "out/"
                           :output-to "out/compiled.js"})
