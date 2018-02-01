'use strict';
define(['PawnsAtWar'], function(PawnsAtWar) {

    PawnsAtWar.controller('mapCtrl', function($scope, $http) {
      var getImage = function (type) {
        switch (type){
          case 0: return 'images/EMPTY.jpg';
          case 1: return 'images/CASTLE.jpg';
          case 2: return 'images/ARCHERY.jpg';
          case 3: return 'images/BARRACKS.jpg';
          case 4: return 'images/GOLD.jpg';
          case 5: return 'images/TERR_GOLD.jpg';
          case 6: return 'images/MILL.jpg';
          case 7: return 'images/STABLE.jpg';
          default: return '#';
        }
      };
      $scope.populateMap = function(url) {
        $http.get(url).then(function(response){
          $scope.map = response.data;
          for(i = 0; i< $scope.map.tiles.length; i++){
            for(j =0; j< $scope.map.tiles[0].length; j++) {
              $scope.map.tiles[i][j].type = getImage($scope.map.tiles[i][j].type);
            }
          }
        });
      };
      $scope.populateMap('api/map/50/50');
    });

});
