define(['PawnsAtWar','services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('messagesCtrl', function($scope, ApiService) {

      $scope.getMessages = function () {
          ApiService.getMessages().then(function (response) {
              $scope.messages = response;
          })
      };

      $scope.createMessage = function () {
        console.log($scope.giveMessage);
        ApiService.createMessage($scope.giveTo, $scope.giveSubject, $scope.giveMessage).then(function (response) {

        }, function (error) {
          $scope.MessageError = true;
        });
      };

      $scope.deleteMessage = function (id) {
        ApiService.deleteMessage(id).then(function (response) {
          $scope.getMessages();
        }, function (error) {
          alert("ERROR" + error.status);
        });
      };

      $scope.answerMessage = function (id) {
        ApiService.answerMessage(id).then(function (response) {
        }, function (error) {
          alert("ERROR" + error.status);
        });
      };

      $scope.getMessages();

      $scope.getMessage = function () {
        ApiService.getMessage().then(function(response){
          $scope.offers = response;
        })
      };

      $scope.getMessage();


      $scope.activePosition = -1;
      $scope.toggleDetails = function(id) {
        //$scope.isVisible = $scope.isVisible == 0 ? true : false;
        $scope.activePosition = $scope.activePosition == id ? -1 : id;

      };

      
    });



});
