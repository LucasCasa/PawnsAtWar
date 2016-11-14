/**
 * Created by lucas on 18/09/16.
 */

function redir() {

    if(!isNumber($('#Xval').val()) || !isNumber($('#Yval').val())){

        $('.form-inline').addClass('has-error');
        $('#error').show();

    }else{
        window.location = window.location.pathname + "?x=" + $('#Xval').val() + "&y=" + $('#Yval').val();
    }


};

function move(x, y){
    var range = parseInt($('#Map').attr('range'));

    if(x<range || x>=100-range || y<range || y>=100-range){
        console.log("invalid position");
    }else{
        window.location = window.location.pathname + "?x=" + x + "&y=" + y;
    }
};



$(document).ready(function(){
    $('#Xval').keypress(function(e){
      if(e.keyCode==13)
      $('#gotoDir').click();
    });
    $('#Yval').keypress(function(e){
      if(e.keyCode==13)
      $('#gotoDir').click();
    });
});



function isNumber(s)
{
    return s.match("^[0-9]+");
};

function toMap() {
    window.location = "/webapp/map";
};

