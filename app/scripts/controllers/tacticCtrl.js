define(['PawnsAtWar','services/ApiService', 'services/tileMapper'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('tacticCtrl', function($scope, $routeParams, ApiService, tileMapper) {

      $scope.myBuildings = function () {
        $scope.buildings = [];

        ApiService.getBuildings().then(function (response) {
            var aux = response;
            for (var i = 0; i < response.length; i++) {
              $scope.buildings[i] = {
                img: tileMapper.getPngImage(aux[i].type),
                description: tileMapper.getDescription(aux[i].type),
                x: aux[i].x,
                y: aux[i].y,
                type: tileMapper.getName('BUILD', aux[i].type),
                army: '',
                amount: '',
                level: aux[i].level
              };
            }

        });

        ApiService.getArmies().then(function (response) {
          var armies = response;
          var position = null;
          console.log(armies);
          console.log(armies[0].troops[0].quantity);

          for (var i = 0; i< armies.length; i++) {
            position = armies[i].position;
            for(var j = 0; j< armies[i].troops.length; j++){
                for( var k =0; k < $scope.buildings.length; k++){
                  if($scope.buildings[k].x == position.x &&  $scope.buildings[k].y == position.y){
                    $scope.buildings[k].army = $scope.armyInfo(armies[i].troops[0].type);
                    $scope.buildings[k].amount = armies[i].troops[0].quantity;
                  }
                }
            }
          }
        });
      };

      $scope.armyInfo = function (type) {
        return tileMapper.getName('RECRUIT', type);

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
