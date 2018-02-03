define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.service('ApiService', function($http) {
        this.login = function (user, pass) {
          var credentials = {username: user, password: pass};
          $http.post('api/login', credentials).success(function (result, status, headers) {
            localStorage.setItem('token', headers('X-AUTH-TOKEN'));
          });
        };

        this.map = function (x, y) {

        };
    });

});
