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
        controller: 'mapCtrl'
      },
      '/map/:x/:y': {
        templateUrl: 'views/map/mapCtrl.html',
        controller: 'mapCtrl'
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
      },
      '/armies': {
        templateUrl: 'views/armies/armiesCtrl.html',
        controller: 'armiesCtrl'
      },
      '/tactic': {
        templateUrl: 'views/tactic/tacticCtrl.html',
        controller: 'tacticCtrl'
      }
            /* ===== yeoman hook ===== */
      /* Do not remove these commented lines! Needed for auto-generation */
    }
  };
});
