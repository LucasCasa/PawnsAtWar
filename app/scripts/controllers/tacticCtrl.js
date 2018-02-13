define(['PawnsAtWar','services/ApiService', 'services/tileMapper'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('tacticCtrl', function($scope, $routeParams, ApiService, tileMapper) {

      $scope.myBuildings = function () {
        ApiService.getBuildings().then(function (response) {
            var aux = response;
          console.log(aux);
            $scope.buildings = [];
            for (var i = 0; i < response.length; i++) {
              $scope.buildings[i] = {
                img: tileMapper.getPngImage(aux[i].type),
                description: tileMapper.getDescription(aux[i].type),
                x: aux[i].x,
                y: aux[i].y,
                type: aux[i].type,
                army: aux[i].type,
                level: aux[i].level
              };
            }

        });
      };

      $scope.myBuildings();

      $scope.reload = function () {
        ApiService.getBuilding($routeParams.x, $routeParams.y).then(function (response) {
          $scope.building = response;
          $scope.buildings[1].cost = $scope.building.castleCost;
        });
        ApiService.getResources().then(function (response) {
          $scope.res = response;
        });
      };

      $scope.levelUp = function (x, y) {
        ApiService.levelUp(x, y).then(function (response) {
          $scope.reload();
        }, function (error) {
          //ERROR HANDLING
        });
      };

      $scope.canBuild = function () {
        return $scope.building !== undefined && !$scope.building.alreadyBuilding && $scope.building.tile.owner.id == $scope.building.id && ($scope.building.tile.type == 0 || $scope.building.tile.type == 5);
      };
      $scope.canAttack = function () {
        return $scope.building !== undefined && $scope.building.tile.owner != null && $scope.building.tile.owner.id != $scope.building.id && $scope.building.tile.type != 5 && $scope.building.tile.type != 0;
      };
      $scope.canUpgrade = function () {
        return $scope.building !== undefined && !$scope.building.alreadyBuilding && $scope.building.tile.owner != null && $scope.building.tile.owner.id == $scope.building.id && $scope.building.tile.type != 0 && $scope.building.tile.type != 5;
      };


      $scope.reload();

    });

});
