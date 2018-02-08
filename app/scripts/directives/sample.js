'use strict';
define(['PawnsAtWar'], function(PawnsAtWar) {

	PawnsAtWar.directive('sample', function() {
		return {
			restrict: 'E',
			template: '<span>Sample</span>'
		};
	});
});
