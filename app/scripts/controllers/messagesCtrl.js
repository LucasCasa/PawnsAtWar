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

      $scope.answer = function (id,from, subject) {

        $scope.answerMessage(id);
        $scope.getMessages();

        $scope.giveTo = ''+from;
        $scope.giveSubject = 'RE:' + subject;

        var message = document.getElementById('message');
        if(message){
          console.log('hello world');
          console.log(message);
          message.focus();
        }


      };

      $scope.answerMessage = function (id) {
        ApiService.answerMessage(id).then(function (response) {
        }, function (error) {
          alert("ERROR" + error.status);
        });
      };


      $scope.getMessages();

      $scope.activePosition = -1;
      $scope.messageDetails = function(id) {
        //$scope.isVisible = $scope.isVisible == 0 ? true : false;
        $scope.activePosition = $scope.activePosition == id ? -1 : id;

      };

      $scope.reduceCounter = function (maxLength, messageLength) {
        var counter = document.getElementById('counter');

        var value = maxLength - messageLength;

        if(value < 0){
          counter.setAttribute('value','0');
        }else{
          counter.setAttribute('value', (value).toString());
        }
      };


    });



});
