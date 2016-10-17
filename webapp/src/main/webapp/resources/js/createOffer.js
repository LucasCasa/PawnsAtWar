$(document).ready(function() {
    $('.quantity').each(function(i, obj) {
        if(!$(this).parents('.resBar').length) {
            $(this).hide();
        }
    });
});

(function() {
    $('input').keyup(handleClick());
})()

function handleClick() {
    var empty = false;
    $('input').each(function() {
    	var numChecked
        if ($(this).val() == '') {
            empty = true;
        }
    });

    if($('input[name=giveType]:checked').length==0 || $('input[name=getType]:checked').length==0){
    	empty = true;
    }

    if (empty) {
        $('#register').attr('disabled', 'disabled');
    } else {
        $('#register').removeAttr('disabled');
    }
}