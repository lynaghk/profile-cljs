(function() {
  goog.provide('WithoutScope');
  echo = function(x){
    return "WithoutScope echo called with: " + x;
  };
}).call(this);
