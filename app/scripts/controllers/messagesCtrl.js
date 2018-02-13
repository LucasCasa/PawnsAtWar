define(['PawnsAtWar','services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('messagesCtrl', function($scope, ApiService) {
      $scope.noUserError = false;
      $scope.noSubjectError = false;
      $scope.noMessageError = false;

      $scope.giveTo = '';
      $scope.giveSubject = '';
      $scope.giveMessage = '';

      $scope.getMessages = function () {
          ApiService.getMessages().then(function (response) {
              $scope.messages = response;
          })
      };

      $scope.createMessage = function () {

        if($scope.giveTo == ''){
          $scope.noUserError = true;
        } else {
          $scope.noUserError = false;
        }
        if($scope.giveSubject == ''){
          $scope.noSubjectError = true;
        } else {
          $scope.noSubjectError = false
        }
        if($scope.giveMessage == ''){
          $scope.noMessageError = true;
        } else {
          $scope.noMessageError = false;
        }
        if($scope.giveTo == '' || $scope.giveSubject == '' || $scope.giveMessage == ''){
          return;
        }

        ApiService.createMessage($scope.giveTo, $scope.giveSubject, $scope.giveMessage).then(function (response) {
          $scope.getMessages();
          $scope.giveTo = '';
          $scope.giveSubject = '';
          $scope.giveMessage = '';
          var counter = document.getElementById('counter');
          counter.setAttribute('value','1024');
          $scope.errorMessage = undefined;
        }, function (error) {
          $scope.errorMessage = error.data.errorId;
        });
      };

      $scope.getMessages();

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
          message.focus();
        }


      };

      $scope.answerMessage = function (id) {
        ApiService.answerMessage(id).then(function (response) {
          $scope.getMessages();
        }, function (error) {
          alert("ERROR" + error.status);
        });
      };

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
