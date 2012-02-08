(function() {
  goog.provide('WithScope');
  goog.scope(function() {
    echo = function(x){
      return "WithScope echo called with: " + x;
    };
  });
}).call(this);
