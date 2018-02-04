define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.service('ApiService', function($q, $http) {
        this.login = function (user, pass) {
          var credentials = {username: user, password: pass};
          var res = $q.defer();
          $http.post('api/login', credentials).success(function (result, status, headers) {
            if(status > 400)
              return res.reject(status);

            if(status == 200)
              localStorage.setItem('token', headers('X-AUTH-TOKEN'));

              return res.resolve(status)
          }).error(function (data, status) {
            res.reject(status);
          });
          return res.promise;
        };

        this.map = function (x, y) {

        };

        this.register = function(user, pass, email) {

        }
    });

});
