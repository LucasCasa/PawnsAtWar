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

      $scope.login = function () {
        ApiService.login($scope.loginUsername, $scope.loginPassword).then(function(status) {
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
          ApiService.register($scope.registerUsername, $scope.registerPassword, $scope.registerEmail)
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
