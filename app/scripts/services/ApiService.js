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

        };

        this.playersWithScore = function() {
          var result = $q.defer();
          $http.get('api/users/score').then(function(response){
            if (response.status >= 400) {
              console.log(response.status);
              return result.reject(response);
            }
            return result.resolve(response.data);
          }, function(error) {
            return result.reject(error);
          });
          return result.promise;
        };

        this.getTrade = function() {
          var result = $q.defer();
          $http.get('api/commerce').then(function(response){
            if (response.status >= 400) {
              console.log(response.status);
              return result.reject(response);
            }
            return result.resolve(response.data);
          }, function (error) {
            return result.reject(error);
          });
          return result.promise;
        };

        this.createOffer = function(giveType, giveAmount, getType, getAmount) {
          var result = $q.defer();
          var body = {offer:{type: giveAmount, amount: giveAmount}, receive:{type:getType, amount: getAmount}};
          $http.post('api/commerce/trade', body).then(function(response){
            if (response.status >= 400) {
              console.log(response.status);
              return result.reject(response);
            }
            return result.resolve(response.data);
          }, function (error) {
            return result.reject(error);
          });
          return result.promise;
        }
    });

});
