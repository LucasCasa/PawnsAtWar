define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('messagesCtrl', function($scope, ApiService) {

      $scope.getMessages = function () {
          ApiService.getMessages().then(function (response) {
              $scope.messages = response;
          })
      };

      $scope.createMessage = function () {
        ApiService.createMessage($scope.giveTo, $scope.giveSubject, $scope.giveMessage).then(function (response) {

        }, function (error) {
          $scope.MessageError = true;
        });
      };

      $scope.deleteMessage = function (id) {
        ApiService.deleteMessage(id).then(function (response) {

        }, function (error) {
          alert("ERROR" + error.status);
        });
      };

      $scope.answerMessage = function (id) {
        ApiService.answerMessage(id).then(function (response) {
        }, function (error) {
          alert("ERROR" + error.status);
        });
      }


    });


});
