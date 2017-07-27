$("input[name=tripIds]").change(function(){
    var max= 2;
    if( $("input[name=tripIds]:checked").length == max ){
        $("input[name=tripIds]").attr('disabled', 'disabled');
        $("input[name=tripIds]:checked").removeAttr('disabled');
    }else{
         $("input[name=tripIds]").removeAttr('disabled');
    }
})