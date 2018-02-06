define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.directive('resource', function() {
        return {
            restrict: 'E',
            scope: {
              res: '@res'
            },
            templateUrl: 'views/resourceBar.html',
          controller: 'resourceController',
          bindToController: true
        }
    });

  PawnsAtWar.controller('resourceController', function (ApiService, $interval, $scope) {
    ApiService.getResources().then(function (response) {
      $scope.res = response
    });
    $interval(function() {
      ApiService.getResources().then(function (response) {
        $scope.res = response
      })
    }, 5000);
  });
});
