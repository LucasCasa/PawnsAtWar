'use strict';

define([], function() {
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
            '/login': {
                templateUrl: 'views/login/LoginCtrl.html',
                controller: 'LoginCtrl'
            },
            '/trade': {
                templateUrl: 'views/trade/tradeCtrl.html',
                controller: 'tradeCtrl'
            },
            '/messages': {
                templateUrl: 'views/messages/messagesCtrl.html',
                controller: 'messagesCtrl'
            },
            '/ranking': {
                templateUrl: 'views/ranking/RankingCtrl.html',
                controller: 'RankingCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
