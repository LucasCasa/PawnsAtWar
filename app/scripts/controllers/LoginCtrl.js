'use strict'
define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    PawnsAtWar.controller('LoginCtrl', function($scope, ApiService) {
      $scope.loginUsername = '';
      $scope.loginPassword = '';
        $scope.login = function () {
          ApiService.login($scope.loginUsername, $scope.loginPassword)
        }
    });

});
