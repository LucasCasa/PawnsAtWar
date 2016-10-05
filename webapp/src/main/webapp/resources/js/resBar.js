var intervalID = setInterval(function(){
	alert("maggie");
	$('.resBar').('.quantity').each(function(i, obj) {
		$(this).text(parseInt(obj.textContent) + 1);
	});
}, 1000);