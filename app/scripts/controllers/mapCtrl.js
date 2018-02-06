'use strict';
define(['PawnsAtWar','services/tileMapper', 'services/ApiService', 'directives/resource'], function(PawnsAtWar) {

    PawnsAtWar.controller('mapCtrl', function($scope, $http, tileMapper, ApiService) {

      $scope.populateMap = function(x, y) {
        $http.get('api/map/' + x +'/' + y).then(function(response){
          $scope.map = response.data;
          for(var i = 0; i< $scope.map.tiles.length; i++){
            for(var j =0; j< $scope.map.tiles[0].length; j++) {
              $scope.map.tiles[i][j].type = tileMapper.getImage($scope.map.tiles[i][j].type);
            }
          }
        });
      };

      $scope.getClass = function (ownerId, playerId) {
        if(ownerId == -1) {
          return "";
        } else if(ownerId == playerId){
          return "friendly";
        } else {
          return 'hostile';
        }
      };

      $scope.populateMap(50,50);
      $scope.invalidPosition = function () {
        return $scope.mapX === undefined || $scope.mapY === undefined || $scope.mapX < 0 || $scope.mapX > 100 || $scope.mapY < 0 || $scope.mapY > 100
      };

      $scope.gotoPosition = function () {
        $scope.populateMap($scope.mapX, $scope.mapY);
      };
    });


});
