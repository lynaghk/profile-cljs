LS_KEY = "timings"

p = (x) ->
  console.log x
  x

window.get_timings = ->
  ls = localStorage[LS_KEY]
  if ls?
    JSON.parse(ls)
  else
    {}

timings = get_timings()

window.save_timings = ->
  localStorage[LS_KEY] = JSON.stringify timings

#add timing to DB; called by both JS and CLJS code
window.add_timing = (name, prn_src, type, dt) ->
  timings[name] or= {}
  timings[name][type] or= {}
  timings[name][type]["ts"] or= []

  timings[name][type]["src"] = prn_src
  timings[name][type]["ts"].push dt

#profiling fn for JS
window.profile = (name, fn) ->
  start = new Date()
  fn()
  dt = new Date() - start

  add_timing name, fn.toString(), "js", dt
