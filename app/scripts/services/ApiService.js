define(['PawnsAtWar'], function (PawnsAtWar) {

  'use strict';
  PawnsAtWar.service('ApiService', function ($q, $http) {

    this.login = function (user, pass) {
      var credentials = {username: user, password: pass};
      var res = $q.defer();
      $http.post('api/login', credentials).then(function (response) {
        if (status >= 400)
          return res.reject(response);

        localStorage.setItem('token', response.headers('X-AUTH-TOKEN'));
        return res.resolve(response)
      }, function (status) {
        res.reject(status);
      });
      return res.promise;
    };

    this.map = function (x, y) {
      return this.callApi('GET', 'api/map/x/y');
    };

    this.getMessages = function () {
      return this.callApi('GET', 'api/messages');
    };

    this.myPosition = function () {
      return this.callApi('GET', 'api/map');
    };

    this.createMessage = function (giveTo, giveSubject, giveMessage) {
      return this.callApi('POST', 'api/messages', {to: giveTo, subject: giveSubject, message: giveMessage});
    };

    this.myPosition = function () {
      return this.callApi('GET', 'api/map');
    };

    this.deleteMessage = function (id) {
      return this.callApi('DELETE', 'api/messages/' + id);
    };

    this.answerMessage = function (id) {
      return this.callApi('PUT', 'api/messages/' + id);
    };

    this.register = function (user, pass, email, locale) {
      return this.callApi('POST', 'api/users', {username: user, password: pass, email: email, locale: locale});
    };

    this.playersWithScore = function () {
      return this.callApi('GET', 'api/users/score');
    };

    this.getTrade = function () {
      return this.callApi('GET', 'api/commerce');
    };

    this.createOffer = function (giveType, giveAmount, getType, getAmount) {
      return this.callApi('POST', 'api/commerce',{offer: {type: giveType, amount: giveAmount}, receive: {type: getType, amount: getAmount}});
    };

    this.removeOffer = function (id) {
      return this.callApi('DELETE', 'api/commerce/trade' + id);
    };

    this.acceptOffer = function (id) {
      return this.callApi('POST', 'api/commerce/trade' + id, {});
    };
    this.getResources = function () {
      return this.callApi('GET', 'api/resources');
    };

    this.getBuilding = function (x, y) {
      return this.callApi('GET', 'api/buildings/' + x + '/' + y);
    };

    this.getBuildings = function () {
      return this.callApi('GET', 'api/buildings');
    };

    this.build = function (x, y, type) {
      return this.callApi('POST', 'api/buildings', {type: type, position: {x: x, y: y}});
    };

    this.demolish = function (x, y) {
      return this.callApi('DELETE', 'api/buildings/' + x + '/' + y)
    };

    this.levelUp = function (x, y) {
      return this.callApi('PUT', 'api/buildings/' + x + '/' + y, {});
    };

    this.getAlerts = function () {
      return this.callApi('GET', 'api/alerts');
    };

    this.trainTroops = function (amount, x, y) {
      return this.callApi('POST', 'api/armies/train', {amount: amount, point: {x: x, y: y}});
    };

    this.getArmies = function () {
      return this.callApi('GET', 'api/armies');
    };

    this.moveArmy = function (id, x, y) {
      return this.callApi('POST', 'api/armies/move', {armyId: id, point: {x: x, y: y}});
    };

    this.splitArmy = function (id, x, y, troops) {
      return this.callApi('POST', 'api/armies/split' , {armyId: id, point: {x: x, y: y}, troops: troops});
    };

    this.callApi = function (method, url, params) {
      var result = $q.defer();
      $http({
        method: method,
        url: url,
        data: params
      }).then(function (response) {
        if (response.status >= 400) {
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
