profile("count up", function(){
  for (var i = 0; i <= 100000; i++){
    i;
  }
});


profile("map inc", function(){
  var arr = [];
  for (var i = 0; i <= 100000; i++){
    arr.push(i);
  }

  arr.map(function(i){return i+1;});
});
