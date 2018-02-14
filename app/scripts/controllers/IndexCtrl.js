'use strict';
define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

	PawnsAtWar.controller('IndexCtrl', function($scope, $interval, ApiService, authManager) {
		$scope.welcomeText = 'Welcome to your PawnsAtWar page';
    $scope.localStorage = localStorage;
    $scope.authManager = authManager;

    $interval(function () {
      if (authManager.isAuthenticated()) {
        ApiService.getMessageCount().then(function (response) {
          $scope.messageCount = response;
        })
      }
    }, 5000);
	});
});
