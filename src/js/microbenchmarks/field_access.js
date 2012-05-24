var a_map = {a: 1};

profile({n:10000, group:"field access"}, function(){
    a_map["a"];
});

profile({n:10000, group:"field access"}, function(){
    a_map.a;
});
