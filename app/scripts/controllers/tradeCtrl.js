define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

  'use strict';
  PawnsAtWar.controller('tradeCtrl', function ($scope, ApiService) {
    $scope.showNewOffer = false;
    $scope.tradeError = false;

    $scope.getTrade = function () {
        ApiService.getTrade().then(function(response){
          $scope.offers = response;
        })
    };

    $scope.getTrade();

    $scope.createOffer = function() {
      ApiService.createOffer($scope.giveType, $scope.giveAmount, $scope.getType, $scope.getAmount).then(function(response){
        $scope.getTrade();
        $scope.showNewOffer = false;
        $scope.giveAmount = '';
        $scope.getAmount = '';
        $scope.tradeError = false;
      }, function(error){
        $scope.tradeError = true;
      });
    };

    $scope.removeOffer = function(id) {
      ApiService.removeOffer(id).then(function(response){
        $scope.getTrade();
      }, function (error) {
        alert("ERROR" + error);
      });
    };

    $scope.setGet = function(val) {
      if($scope.giveType == val){
        $scope.giveType = (val == 0)?1:0;
      }
    };

    $scope.setGive = function(val) {
      if($scope.getType == val){
        $scope.getType = (val == 0)?1:0;
      }
    };
    $scope.invalidTrade = function() {
      return $scope.getType == undefined || $scope.giveType == undefined || $scope.getType == $scope.giveType || $scope.getAmount == undefined || $scope.giveAmount == undefined ||$scope.getAmount <= 0 || $scope.giveAmount <= 0;
    }
  });
});
