(function() {
  goog.provide('WithScope');
  goog.scope(function() {
    WithScope.echo = function(x){
      return "WithScope echo called with: " + x;
    };
  });
}).call(this);
