'use strict';

define([], function() {
    return {
        defaultRoutePath: '/',
        routes: {
            '/': {
                templateUrl: '/views/home.html',
                controller: 'HomeCtrl'
            },
            '/map': {
                templateUrl: '/views/map/mapCtrl.html',
                controller: 'mapCtrl'
            },
            '/login': {
                templateUrl: '/views/login/LoginCtrl.html',
                controller: 'LoginCtrl'
            }
            /* ===== yeoman hook ===== */
            /* Do not remove these commented lines! Needed for auto-generation */
        }
    };
});
