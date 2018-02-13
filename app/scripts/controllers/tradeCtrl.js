define(['PawnsAtWar', 'services/ApiService', 'directives/resource'], function(PawnsAtWar) {

  'use strict';
  PawnsAtWar.controller('tradeCtrl', function ($scope, ApiService) {
    $scope.showNewOffer = false;
    $scope.errorMessage = undefined;

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
        $scope.updateResources();
        $scope.tradeError = undefined;
      }, function(error){
        $scope.errorMessage = error.data.errorId;
      });
    };
    $scope.removeOffer = function(id) {
      ApiService.removeOffer(id).then(function(response){
        $scope.getTrade();
        $scope.updateResources();
        $scope.tradeError = undefined;
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      });
    };

    $scope.acceptOffer = function (id) {
      ApiService.acceptOffer(id).then(function (response) {
        $scope.getTrade();
        $scope.updateResources();
        $scope.tradeError = undefined;
        //Refresh reso?
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      });
    };

    $scope.updateResources = function () {
      ApiService.getResources().then(function (response) {
        $scope.res = response;
      });
    };

    $scope.updateResources();

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
