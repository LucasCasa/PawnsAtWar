define(['PawnsAtWar', 'services/ApiService', 'directives/resource', 'services/tileMapper'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('buildingCtrl', function($scope, $routeParams, ApiService, tileMapper) {

      $scope.reload = function () {
        ApiService.getBuilding($routeParams.x, $routeParams.y).then(function (response) {
          $scope.building = response;
          $scope.buildings[1].cost = $scope.building.castleCost;
        });
        ApiService.getResources().then(function (response) {
          $scope.res = response;
        });
      };

      $scope.build = function (type) {
        ApiService.build($scope.building.tile.x, $scope.building.tile.y, type).then(function (response) {
          $scope.reload();
        }, function (error) {
          //ERROR HANDLING
        })
      };

      $scope.demolish = function() {
        ApiService.demolish($scope.building.tile.x, $scope.building.tile.y).then(function (response) {
          $scope.reload()
        },function (error) {
          //ERROR HANDLING
        });
      };

      $scope.levelUp = function () {
        ApiService.levelUp($scope.building.tile.x, $scope.building.tile.y).then(function (response) {
          $scope.reload();
        }, function (error) {
          //ERROR HANDLING
        })
      };

      $scope.canBuild = function () {
        return $scope.building !== undefined && !$scope.building.alreadyBuilding && $scope.building.tile.owner.id == $scope.building.id && ($scope.building.tile.type == 0 || $scope.building.tile.type == 5);
      };
      $scope.canAttack = function () {
        return $scope.building !== undefined && $scope.building.tile.owner != null && $scope.building.tile.owner != $scope.building.id && $scope.building.tile.type != 5 && $scope.building.tile.type != 0;
      };
      $scope.canUpgrade = function () {
        return $scope.building !== undefined && $scope.building.tile.owner != null && $scope.building.tile.owner.id == $scope.building.id && $scope.building.tile.type != 0 && $scope.building.tile.type != 5;
      };

      $scope.canBuildThis = function (type) {
        if(type == 7) return false;
        if($scope.building === undefined) return false;
        if(type == 0 || type == 5) return false;
        if(type == 1 && !$scope.building.canBuildCastle) return false;
        if(type == 4 && $scope.building.tile.type != 5) return false;
        if($scope.building.tile.type == 5 && type != 4) return false;
        if($scope.building.tile.owner == null && type != 1) return false;
        return true;
      };

      $scope.reload();
      $scope.buildings = [];
      for(var i = 0; i<9;i++){
        $scope.buildings[i] = {img:tileMapper.getPngImage(i), description:tileMapper.getDescription(i), cost:1000, type:i};
      }

      $scope.getImage = tileMapper.getPngImage;
      $scope.getDescription = tileMapper.getDescription;

      $scope.range = function(min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
          input.push(i);
        }
        return input;
      };
    });

});
