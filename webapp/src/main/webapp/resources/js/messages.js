/**
 * Created by root on 11/7/16.
 */
$(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.document.location = $(this).data("href");

    });


});


function textCounter(field,field2,maxlimit)
{
    var countfield = document.getElementById(field2);
    if ( field.value.length > maxlimit ) {
        field.value = field.value.substring( 0, maxlimit );
        return false;
    } else {
        countfield.value = maxlimit - field.value.length;
    }
}

