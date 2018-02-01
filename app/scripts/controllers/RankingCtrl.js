define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('RankingCtrl', function($scope, $http) {
      $http.get('api/users/score').then(function(response){
        $scope.players = response.data;
      });
    });

});
