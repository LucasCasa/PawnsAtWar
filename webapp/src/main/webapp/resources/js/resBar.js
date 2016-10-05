var intervalID = setInterval(function(){
	$('.quantity').each(function(i, obj) {
		if($(this).parents('.resBar').length) {
			$(this).text(parseInt(obj.textContent) + parseInt($(this).attr('id')));
		}
	});
}, 1000);