define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.directive('resource', function() {
        return {
            restrict: 'E',
            scope: {
              res: '='
            },
            templateUrl: 'views/resourceBar.html',
          controller: 'resourceController'
        }
    });

  PawnsAtWar.controller('resourceController', function ($interval, $scope) {
    $scope.addFood = 0;
    $scope.addGold = 0;
    $interval(function() {
      $scope.addFood = Math.min($scope.res.resources[0].rate, $scope.res.limit - $scope.res.resources[0].amount);
      $scope.addGold = Math.min($scope.res.resources[1].rate, $scope.res.limit - $scope.res.resources[1].amount);
      $scope.res.resources[0].amount += $scope.addFood;
      $scope.res.resources[1].amount += $scope.addGold;
    }, 1000);
  });
});
