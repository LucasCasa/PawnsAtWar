define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('RankingCtrl', function($rootScope, $window, $scope, $http, ApiService) {
      if($rootScope.isGameOver) {
        $window.location.href = '#!/gameover';
      }
      ApiService.playersWithScore().then(function(response){
        $scope.players = response;
      }, function(error){

      });
    });
});
