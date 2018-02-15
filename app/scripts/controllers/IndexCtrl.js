'use strict';
define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

	PawnsAtWar.controller('IndexCtrl', function($rootScope, $scope, $interval, ApiService, authManager) {
		$scope.welcomeText = 'Welcome to your PawnsAtWar page';
    $scope.localStorage = localStorage;
    $scope.authManager = authManager;
    $rootScope.isGameOver = false;

    $interval(function () {
      if (authManager.isAuthenticated()) {
        ApiService.getMessageCount().then(function (response) {
          $scope.messageCount = response;
        })
      }
    }, 5000);

    $scope.shouldShowNav = function () {
      return authManager.isAuthenticated() && !$rootScope.isGameOver;
    }
	});
});
