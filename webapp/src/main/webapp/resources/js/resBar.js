var intervalID = setInterval(function(){
	var curr_val = $('.quantity').text();
	var new_val = parseInt(curr_val)+1;
	$('.quantity').text(new_val);  
}, 1000);