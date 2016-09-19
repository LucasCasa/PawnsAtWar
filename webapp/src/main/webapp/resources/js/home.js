/**
 * Created by lucas on 18/09/16.
 */
function redir() {
    console.log( window.location.pathname + "?x=" + $('#Xval').val() + "&y="+ $('#Yval').val());
    window.location = window.location.pathname + "?x=" + $('#Xval').val() + "&y="+ $('#Yval').val();
};