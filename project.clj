(defproject profile-cljs "0.0.1-SNAPSHOT"
  :description "Look at ClojureScript speed with respect to DOM manipulation."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/core.match "0.2.0-alpha9"]
                 
                 [com.keminglabs/c2 "0.1.0-beta2-SNAPSHOT"]
                 [com.keminglabs/vomnibus "0.3.0"]]
  
  :plugins [[lein-cljsbuild "0.1.8"]]
  :cljsbuild {:builds {:test {:source-path "src/cljs/join_test"
                              :compiler {:output-to "public/main.js"
                                         :optimizations :simple
                                         :pretty-print true}}}}
  :source-paths ["src/clj" "src/cljs"
                 ;;If you want to reference local ClojureScript checkout instead of whatever lein cljsbuild is using
                 "vendor/clojurescript/src/clj" "vendor/clojurescript/src/cljs"]
  
  :min-lein-version "2.0.0")
