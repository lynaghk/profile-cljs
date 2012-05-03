profile "count up", ->
  [0..100000].forEach (i) -> i

profile "map inc", ->
  [0..100000].map (i) -> i+1

