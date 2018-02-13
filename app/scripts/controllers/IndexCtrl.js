'use strict';
define(['PawnsAtWar'], function(PawnsAtWar) {

	PawnsAtWar.controller('IndexCtrl', function($scope, authManager) {
		$scope.welcomeText = 'Welcome to your PawnsAtWar page';
    $scope.localStorage = localStorage;
    $scope.authManager = authManager;
	});
});
