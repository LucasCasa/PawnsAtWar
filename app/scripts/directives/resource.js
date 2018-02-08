define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.directive('resource', function() {
        return {
            restrict: 'E',
            scope: {
              reso: '&res'
            },
            templateUrl: 'views/resourceBar.html',
          controller: 'resourceController',
          bindToController: true
        }
    });

  PawnsAtWar.controller('resourceController', function (ApiService, $interval, $scope) {
    $scope.addFood = 0;
    $scope.addGold = 0;
    $interval(function() {
      $scope.res = $scope.reso();
      $scope.addFood = Math.min($scope.res.resources[0].rate + $scope.addFood, $scope.res.limit - $scope.res.resources[0].amount);
      $scope.addGold = Math.min($scope.res.resources[1].rate + $scope.addFood, $scope.res.limit - $scope.res.resources[1].amount);
      $scope.res.resources[0].amount = $scope.res.resources[0].amount + $scope.addFood;
      $scope.res.resources[1].amount = $scope.res.resources[1].amount + $scope.addGold;
    }, 1000);
  });
});
