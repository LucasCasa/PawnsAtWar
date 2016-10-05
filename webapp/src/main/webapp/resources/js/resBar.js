var intervalID = setInterval(function(){
	$('.quantity').each(function(i, obj) {
		if($(this).parents('.resBar').length) {
			$(this).text(parseInt(obj.textContent) + 1);
		}
	});
}, 1000);