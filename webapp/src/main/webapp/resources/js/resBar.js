var acum = [0.0,0.0];
var original = [0,0];
$('.quantity').each(function(i, obj) {
	if($(this).parents('.resBar').length) {
		original[i] = parseInt(obj.textContent);
	}
});

var intervalID = setInterval(function(){
	$('.quantity').each(function(i, obj) {
		if($(this).parents('.resBar').length && $(this).text() < $('#limit').text()) {
			acum[i] += parseFloat($(this).attr('id'));
			$(this).text(parseInt(original[i] +acum[i]));
		}else{
			$(this).text($('#limit').text());
		}
	});
}, 1000);