'use strict';

define([], function () {
  return {
    defaultRoutePath: '/',
    routes: {
      '/': {
        templateUrl: 'views/home.html',
        controller: 'HomeCtrl'
      },
      '/map': {
        templateUrl: 'views/map/mapCtrl.html',
        controller: 'mapCtrl',
        data: {
          requiresLogin: true
        }
      },
      '/login': {
        templateUrl: 'views/login/LoginCtrl.html',
        controller: 'LoginCtrl'
      },
      '/trade': {
        templateUrl: 'views/trade/tradeCtrl.html',
        controller: 'tradeCtrl',
        data: {
          requiresLogin: true
        }
      },
      '/messages': {
        templateUrl: 'views/messages/messagesCtrl.html',
        controller: 'messagesCtrl',
        data: {
          requiresLogin: true
        }
      },
      '/ranking': {
        templateUrl: 'views/ranking/RankingCtrl.html',
        controller: 'RankingCtrl',
        data: {
          requiresLogin: true
        }
      },
      '/building/:x/:y': {
                templateUrl: 'views/building/buildingCtrl.html',
                controller: 'buildingCtrl'
            }
            /* ===== yeoman hook ===== */
      /* Do not remove these commented lines! Needed for auto-generation */
    }
  };
});
