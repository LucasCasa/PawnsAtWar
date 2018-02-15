'use strict';
define(['PawnsAtWar','services/tileMapper', 'services/ApiService', 'directives/resource'], function(PawnsAtWar) {

    PawnsAtWar.controller('mapCtrl', function($rootScope, $scope, $window, $routeParams, $http, tileMapper, ApiService, $interval, authManager) {
      if($rootScope.isGameOver) {
        $window.location.href = '#!/gameover';
      }
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
      $scope.populateMapByUrl = function (url) {
        if(url == '#') return;
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
        if(ownerId === undefined) {
          return "";
        } else if(ownerId == playerId){
          return "friendly";
        } else {
          return 'hostile';
        }
      };

      $scope.getAlerts = function () {
        ApiService.getAlerts().then(function (response) {
          $scope.alerts = response;
          for(var i = 0; i< $scope.alerts.length;i++){
            $scope.alerts[i].timestamp = ($scope.alerts[i].timestamp - $scope.alerts[i].serverTimestamp) / 1000;
          }
        })
      };

      $scope.invalidPosition = function () {
        return $scope.mapX === undefined || $scope.mapY === undefined || $scope.mapX < 0 || $scope.mapX > 100 || $scope.mapY < 0 || $scope.mapY > 100
      };

      $scope.gotoPosition = function () {
        if(!$scope.invalidPosition())
          $scope.populateMap($scope.mapX, $scope.mapY);
      };

      $scope.calculateAlertParams = function(alert){
        return {x: alert.position.x, y: alert.position.y, name: tileMapper.getName(alert.type, alert.param1), count: alert.param2};
      };

      $interval(function () {
        if($rootScope.isGameOver || $scope.alerts == undefined) return;
        for (var i = 0; i < $scope.alerts.length; i++) {
          $scope.alerts[i].timestamp -= 1;
          if ($scope.alerts[i].timestamp <= 0) {
            $scope.reload();
          }
        }
      }, 1000);

      $scope.myPosition = function () {
        ApiService.myPosition().then(function (response) {
          $scope.map = response;
          for(var i = 0; i< $scope.map.tiles.length; i++){
            for(var j =0; j< $scope.map.tiles[0].length; j++) {
              $scope.map.tiles[i][j].type = tileMapper.getImage($scope.map.tiles[i][j].type);
            }
          }
        }, function (error) {
          if(error.data.errorId == "GAME_OVER"){
            $rootScope.isGameOver = true;
            $window.location.href = '#!/gameover';
          }
        });
      };

      $scope.reload = function () {
        if($routeParams.x != undefined && $routeParams.y != undefined) {
          $scope.populateMap($routeParams.x, $routeParams.y);
        }else if($scope.map === undefined){
          $scope.myPosition();
        }else{
          $scope.populateMap($scope.map.x, $scope.map.y);
        }

        ApiService.getResources().then(function (response) {
          $scope.res = response;
        });
        $scope.getAlerts();
      };

      $scope.reload();
    });


});
