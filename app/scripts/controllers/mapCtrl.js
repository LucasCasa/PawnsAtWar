'use strict';
define(['PawnsAtWar','services/tileMapper'], function(PawnsAtWar) {

    PawnsAtWar.controller('mapCtrl', function($scope, $http, tileMapper) {

      $scope.populateMap = function(url) {
        $http.get(url).then(function(response){
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
          return ""
        } else if(ownerId == playerId){
          return "friendly";
        } else {
          return 'hostile';
        }
      };
      $scope.populateMap('api/map/50/50');
    });

});
