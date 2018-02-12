define(['PawnsAtWar', 'services/ApiService'], function (PawnsAtWar) {

  'use strict';
  PawnsAtWar.controller('armiesCtrl', ['$scope', 'ModalService', 'ApiService', function ($scope, ModalService, ApiService) {
    $scope.x = [];
    $scope.y = [];
    $scope.getArmies = function () {
      ApiService.getArmies().then(function (response) {
        $scope.armies = response;
      });
    };

    $scope.getArmies();

    $scope.getAmountForType = function (army, type) {
      for (var i = 0; i < army.troops.length; i++) {
        if (army.troops[i].type == type) {
          return army.troops[i].quantity;
        }
      }
      return '-';
    };

    $scope.move = function (id) {
      ApiService.moveArmy($scope.armies[id].id, $scope.x[id], $scope.y[id]).then(function (response) {
        $scope.armies[id].available = false;
      }, function (error) {
        console.log(error);
        $scope.errorMessage = error.data.errorId;
      })
    };

    $scope.getPos = function(army) {
      return {x: army.position.x, y: army.position.y}
    };

    $scope.showSplit = function (id) {

      ModalService.showModal({
        templateUrl: 'views/armies/splitModal.html',
        controller: 'splitModalCtrl',
        preClose: function (modal) {
          modal.element.modal('hide');
        },
        inputs: {
          warriorAmount: $scope.getAmountForType($scope.armies[id], 0),
          archerAmount: $scope.getAmountForType($scope.armies[id], 1),
          horsemenAmount: $scope.getAmountForType($scope.armies[id], 2),
          armyId: $scope.armies[id].id
        }
      }).then(function (modal) {
        modal.element.modal();
        modal.close.then(function (result) {
          if(result){
            $scope.getArmies();
          }
        });
      });

    };
  }]);

  PawnsAtWar.controller('splitModalCtrl', function ($scope, $element, ApiService, warriorAmount, archerAmount, horsemenAmount, armyId, close) {
    $scope.warriorAmount = warriorAmount;
    $scope.archerAmount = archerAmount;
    $scope.horsemenAmount = horsemenAmount;

    //  This close function doesn't need to use jQuery or bootstrap, because
    //  the button has the 'data-dismiss' attribute.
    $scope.close = function (success) {
      $element.modal('hide');
      close(success, 500); // close, but give 500ms for bootstrap to animate
    };

    //  This cancel function must use the bootstrap, 'modal' function because
    //  the doesn't have the 'data-dismiss' attribute.
    $scope.cancel = function () {

      //  Manually hide the modal.
      $element.modal('hide');

      //  Now call close, returning control to the caller.
      close(false, 500); // close, but give 500ms for bootstrap to animate
    };

    $scope.limit = function (id) {
      switch (id) {
        case 0:
          $scope.wc = parseInt(Math.min(warriorAmount, Math.max(0, $scope.wc)));
          break;
        case 1:
          $scope.ac = parseInt(Math.min(archerAmount, Math.max(0, $scope.ac)));
          break;
        case 2:
          $scope.hc = parseInt(Math.min(horsemenAmount, Math.max(0, $scope.hc)));
          break;
      }
    };

    $scope.split = function () {
      var troops = [];
      var index = 0;
      if($scope.wc > 0){
        troops[index] = {type:0, amount: $scope.wc};
        index = index + 1;
      }
      if($scope.ac > 0){
        troops[index] = {type:1, amount: $scope.ac};
        index = index + 1;
      }
      if($scope.hc > 0){
        troops[index] = {type:2, amount: $scope.hc};
      }

      ApiService.splitArmy(armyId, $scope.x, $scope.y, troops).then(function (response) {
        $scope.close(true);
      }, function (error) {
        $scope.errorMessage = error.data.errorId;
      });
    }

  });
});
