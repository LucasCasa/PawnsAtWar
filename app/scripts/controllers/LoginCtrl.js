'use strict'
define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    PawnsAtWar.controller('LoginCtrl', function($window, $scope, ApiService) {
      $scope.loginUsername = '';
      $scope.loginPassword = '';
      $scope.registerUsername = '';
      $scope.registerPassword = '';
      $scope.registerEmail = '';
      $scope.registerRepeat = '';
      $scope.showError = false;

      $scope.login = function (user, pass) {
        user = user === undefined ?  $scope.loginUsername : user;
        pass = pass === undefined ?  $scope.loginPassword : pass;
        ApiService.login(user, pass).then(function(status) {
          if(status == 200) {
            $window.location.href = '#/map';
          } else if(status > 400) {
            $scope.showError = true
          }
        }).catch(function (error) {
          $scope.showError = true
        })
      };
      $scope.register = function () {
        if($scope.registerPassword == $scope.registerRepeat) {
          ApiService.register($scope.registerUsername, $scope.registerPassword, $scope.registerEmail).then(function(response) {
            $scope.login($scope.registerUsername, $scope.registerPassword);
          }, function(error) {
              $scope.showError = true;
          })
        }
      };

      jQuery('#message').hide();
      jQuery('#password, #confirm_password').on('keyup', function () {
        if (jQuery('#password').val() == jQuery('#confirm_password').val()) {
          jQuery('#message').hide();
        } else
          jQuery('#message').show();
      });
    });
});
