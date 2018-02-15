'use strict'
define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

    PawnsAtWar.controller('LoginCtrl', function($rootScope, $window, $scope, ApiService, $translate) {
      $rootScope.isGameOver = false;
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
        ApiService.login(user, pass).then(function(response) {
          $window.location.href = '#!/map';
        }, function (error) {
          $scope.showError = true
        });
      };
      $scope.register = function () {
        if($scope.registerPassword == $scope.registerRepeat) {
          ApiService.register($scope.registerUsername, $scope.registerPassword, $scope.registerEmail, $translate.resolveClientLocale()).then(function(response) {
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
