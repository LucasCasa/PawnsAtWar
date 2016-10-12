$(document).ready(function() {
    $('.quantity').each(function(i, obj) {
        if(!$(this).parents('.resBar').length) {
            $(this).hide();
        }
    });
});