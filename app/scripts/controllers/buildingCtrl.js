define(['PawnsAtWar', 'services/ApiService', 'directives/resource', 'services/tileMapper'], function (PawnsAtWar) {

  'use strict';
  PawnsAtWar.controller('buildingCtrl', function ($rootScope, $scope, $routeParams, ApiService, tileMapper, $translate, $window) {

    if($rootScope.isGameOver) {
      $window.location.href = '#!/gameover';
    }
    $scope.reload = function () {
      ApiService.getBuilding($routeParams.x, $routeParams.y).then(function (response) {
        $scope.building = response;
        $scope.buildings[1].cost = $scope.building.castleCost;
        $scope.canTrain = $scope.building.tile.owner != undefined && $scope.building.tile.owner.id == $scope.building.id && $scope.building.tile.type == 2 || $scope.building.tile.type == 3 || $scope.building.tile.type == 8
      });
      ApiService.getResources().then(function (response) {
        $scope.res = response;
      });
    };

    $scope.build = function (type) {
      ApiService.build($scope.building.tile.x, $scope.building.tile.y, type).then(function (response) {
        //$scope.reload();
        $scope.errorMessage = undefined;
        $window.location.href = '#!/map/' + $scope.building.tile.x + '/' + $scope.building.tile.y;
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      })
    };

    $scope.demolish = function () {
      if (confirm($translate.instant('WARNING_DEMOLISH'))) {
        ApiService.demolish($scope.building.tile.x, $scope.building.tile.y).then(function (response) {
          $scope.reload();
          $scope.errorMessage = undefined;
        }, function (error) {
          $scope.errorMessage = error.data.errorId;
        });
      }
    };

    $scope.levelUp = function () {
      ApiService.levelUp($scope.building.tile.x, $scope.building.tile.y).then(function (response) {
        $scope.reload();
        $scope.errorMessage = undefined;
        $window.location.href = '#!/map/' + $scope.building.tile.x + '/' + $scope.building.tile.y;
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      });
    };

    $scope.train = function () {
      ApiService.trainTroops($scope.troopAmount, $scope.building.tile.x, $scope.building.tile.y).then(function (response) {
        $window.location.href = '#!/map/' + $scope.building.tile.x + '/' + $scope.building.tile.y;
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      })
    };

    $scope.attack = function() {
      $window.location.href = '#!/armies?x=' + $scope.building.tile.x + '&y=' + $scope.building.tile.y;
    };

    $scope.canBuild = function () {
      if($scope.building === undefined)
        return false;
      if($scope.building.alreadyBuilding)
        return false;
      if($scope.building.tile.owner == null && (!$scope.building.canBuildCastle || $scope.building.tile.type == 5))
        return false;
      if($scope.building.tile.owner != null && $scope.building.tile.owner.id != $scope.building.id)
        return false;
      if($scope.building.tile.type != 0 && $scope.building.tile.type != 5)
        return false;
      return true;
    };
    $scope.canAttack = function () {
      return $scope.building !== undefined && $scope.building.tile.owner != null && $scope.building.tile.owner.id != $scope.building.id && $scope.building.tile.type != 5 && $scope.building.tile.type != 0;
    };
    $scope.canUpgrade = function () {
      return $scope.building !== undefined && !$scope.building.alreadyBuilding && $scope.building.tile.owner != null && $scope.building.tile.owner.id == $scope.building.id && $scope.building.tile.type != 0 && $scope.building.tile.type != 5;
    };

    $scope.canBuildThis = function (type) {
      if (type == 7) return false;
      if ($scope.building === undefined) return false;
      if (type == 0 || type == 5) return false;
      if (type == 1 && !$scope.building.canBuildCastle) return false;
      if (type == 4 && $scope.building.tile.type != 5) return false;
      if ($scope.building.tile.type == 5 && type != 4) return false;
      if ($scope.building.tile.owner == null && type != 1) return false;
      return true;
    };

    $scope.reload();
    $scope.buildings = [];
    for (var i = 0; i < 9; i++) {
      $scope.buildings[i] = {
        img: tileMapper.getPngImage(i),
        description: tileMapper.getDescription(i),
        cost: 1000,
        type: i
      };
    }

    $scope.getImage = tileMapper.getPngImage;
    $scope.getDescription = tileMapper.getDescription;

    $scope.range = function (min, max, step) {
      step = step || 1;
      var input = [];
      for (var i = min; i <= max; i += step) {
        input.push(i);
      }
      return input;
    };

    $scope.getBonusIcon = function (type) {
      switch (type) {
        case 1:
          return "images/silo.png";
        case 2:
        case 3:
        case 6:
        case 8:
          return "images/iconResFood.png";
        case 4:
          return "images/iconResGold.png";
        default :
          return "#";
      }
    };

    $scope.getBonusValue = function (type, level) {
      switch (type) {
        case 6:
        case 4:
          return level * 0.1;
        case 1:
          return 1000*level + Math.pow(3*level, 3);
        case 3:
          return 31 - level;
        case 2:
          return 51 - level;
        case 8:
          return 71 - level;
        default:
          return -1;
      }
    };

    $scope.getBonusName = function (type) {
      switch (type) {
        case 1:
          return 'EXTRA_STORAGE';
        case 2:
        case 3:
        case 8:
          return 'TROOP_COST';
        case 6:
          return 'FOOD_PER_SECOND';
        case 4:
          return 'GOLD_PER_SECOND';
        default:
          return '';
      }
    };
  });

});
