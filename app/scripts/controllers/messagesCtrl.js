define(['PawnsAtWar','services/ApiService', 'angular-auto-complete'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('messagesCtrl', function($rootScope, $window,  $scope, ApiService, ModalService) {
      if($rootScope.isGameOver) {
        $window.location.href = '#!/gameover';
      }
      $scope.messageSent = false;
      $scope.noUserError = false;
      $scope.noSubjectError = false;
      $scope.noMessageError = false;
      $scope.to = 'pepito';
      $scope.giveTo = '';
      $scope.giveSubject = '';
      $scope.giveMessage = '';
      $scope.show = true;
      $scope.cancelButton = false;
      $scope.title= false;
      $scope.divCreate = false;

      $scope.getMessages = function () {
          ApiService.getMessages().then(function (response) {
              $scope.messages = response;
          })
      };

      $scope.createOrCancelMessage = function () {
        if($scope.show){
          $scope.clearInput();
          $scope.show = false;
          $scope.title= true;
          $scope.cancelButton = true;
          $scope.divCreate = true;
        }else{
          $scope.messageSent = false;
          $scope.show = true;
          $scope.title= false;
          $scope.cancelButton = false;
          $scope.divCreate = false;
          $scope.noUserError=false;
          $scope.noSubjectError=false;
          $scope.noMessageError=false;
        }

      };

      $scope.createMessage = function () {
        if($scope.giveTo == undefined || $scope.giveTo.originalObject == undefined || $scope.giveTo.originalObject.username == undefined){
          $scope.noUserError = true;
        } else {
          $scope.noUserError = false;
        }
        if($scope.giveSubject == ''){
          $scope.noSubjectError = true;

        } else {
          if($scope.giveSubject.includes("RE:")){
            $scope.noUserError=false;
          }
          $scope.noSubjectError = false
        }
        if($scope.giveMessage == ''){
          $scope.noMessageError = true;
        } else {
          $scope.noMessageError = false;
        }
        if($scope.noUserError || $scope.giveSubject == '' || $scope.giveMessage == ''){
          return;
        }
        console.log($scope.giveTo.title);
        ApiService.createMessage($scope.giveTo.originalObject.username, $scope.giveSubject, $scope.giveMessage).then(function (response) {
          $scope.getMessages();
          $scope.clearInput();
          $scope.giveSubject = '';
          $scope.giveMessage = '';
          $scope.messageSent = true;
          var counter = document.getElementById('counter');
          counter.setAttribute('value','1024');
          $scope.errorMessage = undefined;
        }, function (error) {
          $scope.errorMessage = error.data.errorId;
          $scope.messageSent = false;
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

        $scope.show = false;
        $scope.title= true;
        $scope.cancelButton = true;
        $scope.divCreate = true;
        $scope.noUserError = false;
        $scope.noSubjectError=false;
        $scope.noMessageError = false;

        var message = document.getElementById('message');
        if(message){
          message.focus();
        }

        $scope.changeInput(from);
        $scope.giveSubject = ('RE:' + subject).substring(0, 49);
      };

      $scope.showMessage = function (id, from, subject, message, sent) {
        if (!sent)
          $scope.answerMessage(id);

        ModalService.showModal({
          templateUrl: 'views/messages/messageModal.html',
          controller: 'messageModalCtrl',
          preClose: function (modal) {
            modal.element.modal('hide');
          },
          inputs: {
            messageId: id,
            messageFrom: from,
            messageSubject: subject,
            messageDescription: message
          }
        }).then(function (modal) {
          modal.element.modal();
          modal.close.then(function (result) {
            if(result.response){
              $scope.answer(result.id, result.username, result.subject);
            }
          });
        });

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


      $scope.changeInput = function (from) {
        $scope.$broadcast('angucomplete-alt:changeInput', 'player', {username:from});
      };

      $scope.clearInput= function () {
        $scope.$broadcast('angucomplete-alt:clearInput', 'player');
        $scope.giveSubject='';
        $scope.giveMessage='';
      };

      $scope.autoCompleteOptions = {
        minimumChars:2,
        data: function(searchText) {
          ApiService.getUsers(searchText).then(function (response) {
            return _.pluck(response.users, 'name');
          })
        }
      }
    });

  PawnsAtWar.controller('messageModalCtrl', function ($scope, $element, ApiService, messageId, messageFrom, messageSubject, messageDescription, close) {
    $scope.messageFrom = messageFrom;
    $scope.messageSubject = messageSubject;
    $scope.messageDescription = messageDescription;

    //  This close function doesn't need to use jQuery or bootstrap, because
    //  the button has the 'data-dismiss' attribute.
    $scope.close = function (success) {
      $element.modal('hide');
      close({response:true, id:messageId, username: messageFrom, subject: messageSubject}, 500); // close, but give 500ms for bootstrap to animate
    };

    //  This cancel function must use the bootstrap, 'modal' function because
    //  the doesn't have the 'data-dismiss' attribute.
    $scope.cancel = function () {

      //  Manually hide the modal.
      $element.modal('hide');

      //  Now call close, returning control to the caller.
      close({response:false}, 500); // close, but give 500ms for bootstrap to animate
    };

  });
});
