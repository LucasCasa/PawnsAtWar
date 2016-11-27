/**
 * Created by lucas on 24/11/16.
 */
var intervalID = setInterval(function(){
    $('.timeR').each(function(i, obj) {
        value = parseInt($(this).text());
            $(this).text(value -1);
        if($(this).text() == -1){
            location.reload(false);
        }
    });
}, 1000);