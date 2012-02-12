#!/bin/bash

CLJSC_CP=''
for next in src/clj: lib/*: vendor/clojurescript/lib/*: vendor/clojurescript/src/clj: vendor/clojurescript/src/cljs; do
  CLJSC_CP=$CLJSC_CP$next
done

rm -rf out
mkdir -p out
java -server -cp $CLJSC_CP clojure.main compile.clj

#phantomjs src/run.js
