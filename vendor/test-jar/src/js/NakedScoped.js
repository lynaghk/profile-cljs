goog.provide('NakedScoped');
goog.scope(function(){
  NakedScoped.echo = function(x){
    return "NakedScoped echo called with: " + x;
  };
});
