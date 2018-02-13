define(['PawnsAtWar'], function(PawnsAtWar) {

  'use strict';
  PawnsAtWar.service('ApiService', function($q, $http) {

    this.login = function (user, pass) {
      var credentials = {username: user, password: pass};
      var res = $q.defer();
      $http.post('api/login', credentials).success(function (result, status, headers) {
        if(status >= 400)
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

    this.myPosition = function () {
      var result = $q.defer();

      $http.get('api/map').then(function(response){
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

    this.register = function(user, pass, email) {
      var result = $q.defer();
      var body = {username: user, password: pass, email: email};
      $http.post('api/users', body).then(function(response){
        if(response.status >= 400){
          return result.reject(response);
        } else{
          return result.resolve(response);
        }
      }, function(error){
        return result.reject(error);
      });
      return result.promise;
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
      var body = {offer:{type: giveType, amount: giveAmount}, receive:{type:getType, amount: getAmount}};
      $http.post('api/commerce', body).then(function(response){
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

    this.removeOffer = function(id) {
      var result = $q.defer();
      $http.delete('api/commerce/trade/' + id).then(function(response){
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

    this.acceptOffer = function(id) {
      var result = $q.defer();
      $http.post('api/commerce/trade/' + id, {}).then(function(response){
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
    this.getResources = function () {
      var result = $q.defer();
      $http.get('api/resources/').then(function(response){
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
    this.getBuilding = function (x, y) {
      var result = $q.defer();
      $http.get('api/buildings/' + x + '/' + y).then(function(response){
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

    this.getBuildings = function () {
      var result = $q.defer();
      $http.get('api/buildings').then(function(response){
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

    this.build = function(x, y, type) {
      var result = $q.defer();
      $http.post('api/buildings',{type:type, position:{x:x, y:y}}).then(function(response){
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

    this.demolish = function(x, y) {
      var result = $q.defer();
      $http.delete('api/buildings/' + x + '/' + y).then(function(response){
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

    this.levelUp = function (x, y) {
      var result = $q.defer();
      $http.put('api/buildings/' + x + '/' + y,{}).then(function(response){
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

    this.getAlerts = function () {
      var result = $q.defer();
      $http.get('api/alerts').then(function(response){
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

    this.getMessages = function () {
      var result = $q.defer();

      $http.get('api/messages').then(function(response){
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

    this.createMessage = function (giveTo, giveSubject, giveMessage) {
      var result = $q.defer();
      var body = {to:giveTo, subject:giveSubject,message:giveMessage};

      $http.post('api/messages', body).then(function (response) {
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

    this.deleteMessage = function(id){
      var result = $q.defer();
      $http.delete('api/messages/' + id).then(function (response) {
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

    this.answerMessage = function (id) {
      var result = $q.defer();
      $http.put('api/messages/' + id).then(function (response) {
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

  });

});
