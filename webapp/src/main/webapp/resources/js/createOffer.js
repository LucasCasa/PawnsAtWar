$(document).ready(function() {
    $('#register').attr('disabled', 'disabled');
    $('.quantity').each(function(i, obj) {
        if(!$(this).parents('.resBar').length) {
            $(this).hide();
        }
    });
});


function checkSumbitAvailability() {

    var empty = false;
    // Input labels have values
    $('input').each(function() {
    	var numChecked
        if ($(this).val() == '') {
            empty = true;
        }
    });

    // Radios are checked
    if($('input[name=giveType]:checked').length==0 || $('input[name=getType]:checked').length==0){
    	empty = true;
    }else{
        // Different resources are selected
        if($('input[name=giveType]:checked').val() == $('input[name=getType]:checked').val())
            empty = true;
    }

    if (empty) {
        $('#register').attr('disabled', 'disabled');
    } else {
        $('#register').removeAttr('disabled');
    }
}