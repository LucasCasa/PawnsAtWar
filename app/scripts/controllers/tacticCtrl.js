define(['PawnsAtWar','services/ApiService', 'services/tileMapper', 'directives/resource'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('tacticCtrl', function($rootScope, $scope, $window, $routeParams, ApiService, tileMapper) {
      if($rootScope.isGameOver) {
        $window.location.href = '#!/gameover';
      }
      $scope.tileMapper = tileMapper;
      $scope.myBuildings = function () {
        $scope.buildings = [];

        ApiService.getBuildings().then(function (response) {
          $scope.buildings = response;
          ApiService.getArmies().then(function (response) {
            var armies = response;
            var position = null;

            for (var i = 0; i< armies.length; i++) {
              position = armies[i].position;
              for(var j = 0; j< armies[i].troops.length; j++){
                for( var k =0; k < $scope.buildings.length; k++){
                  if($scope.buildings[k].tile.x == position.x &&  $scope.buildings[k].tile.y == position.y){
                    $scope.buildings[k].army = armies[i];
                  }
                }
              }
            }
          });
        });
        ApiService.getResources().then(function (response) {
          $scope.res = response;
        })
      };

      $scope.armyInfo = function (type) {
        return tileMapper.getName('RECRUIT', type);

      };

      $scope.getTroopImage = function (type) {
        switch (type){
          case 0: return 'images/warrior.png';
          case 1: return 'images/archer.png';
          case 2: return 'images/horseman.png';
        }
      };

      $scope.myBuildings();

      $scope.levelUp = function (x, y) {
        ApiService.levelUp(x, y).then(function (response) {
          $scope.myBuildings();
          $scope.successMessage = 'SUCCESS';
          $scope.errorMessage = undefined;
        }, function (error) {
          $scope.errorMessage = error.data.errorId;
          $scope.successMessage = undefined;
        });
      };

      $scope.goto = function (x, y) {
        $window.location.href = '#!/building/' + x + '/' + y;
      };

    });

});
