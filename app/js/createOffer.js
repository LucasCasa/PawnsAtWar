$(document).ready(function() {
    $('#register').attr('disabled', 'disabled');
    $('.quantity').each(function(i, obj) {
        if(!$(this).parents('.resBar').length) {
            $(this).hide();
        }
    });
});

function selectRadioGet(type) {
    $('input[name=giveType]').each(function(i) {

        if($(this).attr('value') == type){
            if($(this).is(':checked')){ 
                var index = 0;
                if($(this).attr('value') == 0)
                    index = 1;
                $('input[name=giveType]').each(function(i) {
                    if($(this).attr('value') != type){
                        $(this).prop("checked", true);
                        return;
                    }
                });
            }
            $(this).removeAttr('checked');
        }
        
    });
    checkSumbitAvailability();
};

function selectRadioGive(type) {
    $('input[name=getType]').each(function(i) {
        if($(this).attr('value') == type){
            if($(this).is(':checked')){
                var index = 0;
                if($(this).attr('value') == 0)
                    index = 1;
                $('input[name=getType]').each(function(i) {
                    if($(this).attr('value') != type){
                        $(this).prop("checked", true);
                        return;
                    }
                });
            }
            $(this).removeAttr('checked');
        }

    });
    checkSumbitAvailability();
};

function checkAnyGet(ignore){
    $('input[name=getType]').each(function(i) {
        if($(this).attr('value') != ignore){
            alert("checking");
            $(this).attr('checked',true);
            return;
        }
    });
};

function checkAnyGive(ignore){
    $('input[name=giveType]').each(function(i) {
        if($(this).attr('value') != ignore){
            alert("checking");
            $(this).attr('checked',true);
            return;
        }
    });
};

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
        if($('input[name=giveType]:checked').val() == $('input[name=getType]:checked').val()){
            empty = true;
            $('#sameType').css("display", "block");
        }else{
            $('#sameType').css("display", "none");
        }
    }

    if (empty) {
        $('#register').attr('disabled', 'disabled');
    } else {
        $('#register').removeAttr('disabled');
    }

    // Remove possible existing warning
    $('#insAmount').css("display","none");
};