var a_map = {a: 1};

profile("field access", function(){
  for (var i = 0; i <= 10000; i++){
    a_map["a"];
  }
});

profile("field access", function(){
  for (var i = 0; i <= 10000; i++){
    a_map.a;
  }
});
