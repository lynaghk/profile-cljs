(function() {
  goog.provide('WithoutScope');
  WithoutScope.echo = function(x){
    return "WithoutScope echo called with: " + x;
  };
}).call(this);
