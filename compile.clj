(require '[cljs.closure :as closure])

(closure/build "src/cljs" {:optimizations :simple
                           :output-dir "out/"
                           :output-to "out/compiled.js"
                           :libs ["Cassowary.js"
                                  "underscore.js"
                                  "jshashset.js"
                                  "Variable.js"
                                  "SymbolicWeight.js"
                                  "Strength.js"
                                  "Constraint.js"
                                  "jshashtable.js"
                                  "Errors.js"
                                  "LinearConstraint.js"
                                  "Tableau.js"
                                  "Point.js"
                                  "CL.js"
                                  "EditInfo.js"
                                  "LinearExpression.js"
                                  "SimplexSolver.js"]
                           })
