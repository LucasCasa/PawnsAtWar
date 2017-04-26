/**
 * Created by lucas on 24/11/16.
 */
var intervalID = setInterval(function(){
    $('.timeR').each(function(i, obj) {
        value = parseInt($(this).text());
            $(this).text(value -1);
        if($(this).text() == 0){
            location.reload(false);
        }
        if($(this).text() <0){
            $(this).text(0);
        }
    });
}, 1000);