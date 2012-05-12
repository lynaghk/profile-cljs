LS_KEY = "timings"

p = (x) ->
  console.log x
  x

window.get_timings = ->
  ls = localStorage[LS_KEY]
  if ls?
    JSON.parse(ls)
  else
    []

timings = get_timings()

window.save_timings = ->
  localStorage[LS_KEY] = JSON.stringify timings

window.reset_timings = ->
  localStorage.removeItem LS_KEY

#add timing to DB; called by both JS and CLJS code
window.add_timing = (timing) ->
  timings.push timing

#profiling fn for JS
window.profile = (opts, fn) ->
  start = new Date()
  fn()
  if typeof opts == "string"
    opts =
      group: opts

  opts["dt"] = new Date() - start
  opts["lang"] = "js"
  opts["src"] = fn.toString()
  add_timing opts

