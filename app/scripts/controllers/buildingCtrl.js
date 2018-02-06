define(['PawnsAtWar', 'services/ApiService', 'directives/resource', 'services/tileMapper'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('buildingCtrl', function($scope, $routeParams, ApiService, tileMapper) {
      ApiService.getBuilding($routeParams.x, $routeParams.y).then(function (response) {
        $scope.building = response;
      });

      ApiService.getResources().then(function (response) {
        $scope.res = response;
      });
        $scope.buildings = [];
      for(var i = 0; i<7;i++){
        $scope.buildings[i] = {img:tileMapper.getImage(i), description:tileMapper.getDescription(i), cost:1000};
      }

    });

});
