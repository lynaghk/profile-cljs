(defproject profile-cljs "0.0.1-SNAPSHOT"
  :description "Compare ClojureScript & JS performance"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.match "0.2.0-alpha9"]

                 [com.keminglabs/c2 "0.1.1-SNAPSHOT"]
                 [com.keminglabs/vomnibus "0.3.0"]]

  :plugins [[lein-cljsbuild "0.1.9"]]
  
  :cljsbuild {:builds {:simple {:source-path "src/cljs/microbenchmarks"
                                :compiler {:output-to "public/simple.js"
                                           :optimizations :simple}}
                       :advanced {:source-path "src/cljs/microbenchmarks"
                                  :compiler {:output-to "public/advanced.js"
                                             :optimizations :advanced}}
                       :viewer {:source-path "src/cljs/viewer"
                                :compiler {:output-to "public/viewer.js"
                                             :optimizations :simple}}}}
  :source-paths ["src/clj" "src/cljs"
                 ;;If you want to reference local ClojureScript checkout instead of whatever lein cljsbuild is using
                 ;;"vendor/clojurescript/src/clj" "vendor/clojurescript/src/cljs"
                 ]

  :min-lein-version "2.0.0")
