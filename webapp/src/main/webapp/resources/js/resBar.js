var intervalID = setInterval(function(){
	$('.quantity').each(function(i, obj) {
		$(this).text(parseInt(obj.textContent) + 1);
	});
}, 1000);