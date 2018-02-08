define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('RankingCtrl', function($scope, $http, ApiService) {
      ApiService.playersWithScore().then(function(response){
        $scope.players = response;
      }, function(error){

      });
    });
});
