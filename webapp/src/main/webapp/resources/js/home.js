/**
 * Created by lucas on 18/09/16.
 */
function redir() {
    var hidden = false;;
    if(!isNumber($('#Xval').val()) || !isNumber($('#Yval').val())){

        hidden = !hidden;
        if(hidden) {
            document.getElementById('goToDir').style.visibility = 'hidden';

        } else {
            document.getElementById('goToDir').style.visibility = 'visible';

        }

    }else{
            console.log(window.location.pathname + "?x=" + $('#Xval').val() + "&y=" + $('#Yval').val());
            window.location = window.location.pathname + "?x=" + $('#Xval').val() + "&y=" + $('#Yval').val();
        }

};

function move(x, y){
    var range = 3;
    if(x<range || x>=100-range || y<range || y>=100-range){
        console.log("invalid position");
    }else{
        window.location = window.location.pathname + "?x=" + x + "&y=" + y;
    }
}

function isNumber(s)
{
    return s.match("^[0-9]+");
};

function toMap() {
    window.location = "/webapp/map";

};


function describe(){

    var elemento = document.createElement("img");
    document.getElementById("elemDescription").appendChild(elemento);
    elemento.src = '/webapp/resources/images/archery.png';
};

function deleteDescription(){
    var d = document.getElementById("elemDescription");

    while (d.firstChild) {
        d.removeChild(d.firstChild);
    }
}
