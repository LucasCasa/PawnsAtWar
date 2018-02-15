define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('gameover', function($rootScope, $window, $scope, authManager, ApiService) {
      if(!$rootScope.isGameOver) $window.location.href = '#!/map';
      $scope.logout = function() {
        authManager.unauthenticate();
        localStorage.removeItem('token');
        $window.location.href = '#!/login';
      };

      $scope.startAgain = function () {
        ApiService.startAgain().then(function (response) {
          $rootScope.isGameOver = false;
          $window.location.href = '#!/map';
        }, function (error) {
          $scope.errorMessage = error.data.errorId;
        })
      }


    });

});
