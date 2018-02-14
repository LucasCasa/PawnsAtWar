define(['PawnsAtWar','services/ApiService'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.controller('messagesCtrl', function($scope, ApiService, ModalService) {
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
        $scope.giveSubject = subject.includes("RE:")?subject:'RE:' + subject;

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

      //AutoComplete starts

      $scope.localSearch = function(str) {
        $scope.matches = [];
        ApiService.getUsers().then(function (response) {
         var  users = response;
          console.log(str);
          console.log(users);
        });

        // people.forEach(function(person) {
        //   var fullName = person.firstName + ' ' + person.surname;
        //   if ((person.firstName.toLowerCase().indexOf(str.toString().toLowerCase()) >= 0) ||
        //     (person.surname.toLowerCase().indexOf(str.toString().toLowerCase()) >= 0) ||
        //     (fullName.toLowerCase().indexOf(str.toString().toLowerCase()) >= 0)) {
        //     matches.push(person);
        //   }
        // });
        // return matches;
      };
      //AutoComplete ends


      $scope.showMessage = function (id, from, subject, message) {
        $scope.answerMessage(id);
        ModalService.showModal({
          templateUrl: 'views/messages/messageModal.html',
          controller: 'messageModalCtrl',
          preClose: function (modal) {
            modal.element.modal('hide');
          },
          inputs: {
            messageFrom: from,
            messageSubject: subject,
            messageDescription: message,
          }
        }).then(function (modal) {
          modal.element.modal();
          modal.close.then(function (result) {
            if(result){
              $scope.getMessages();
            }
          });
        });

      };

    });


PawnsAtWar.controller('messageModalCtrl', function ($scope, $element, ApiService, messageFrom, messageSubject, messageDescription, close) {
    $scope.messageFrom = messageFrom;
    $scope.messageSubject = messageSubject;
    $scope.messageDescription = messageDescription;

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

  });

});



