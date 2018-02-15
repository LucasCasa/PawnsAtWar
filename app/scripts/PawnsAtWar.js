'use strict';
define(['routes',
    'services/dependencyResolverFor',
    'i18n/i18nLoader!',
    'angular',
    'angular-route',
    'bootstrap',
    'angular-translate',
    'angular-jwt',
    'angular-local-storage',
    'angular-modal-service',
    'angucomplete-alt',
  ],
  function (config, dependencyResolverFor, i18n) {
    var PawnsAtWar = angular.module('PawnsAtWar', [
      'ngRoute',
      'pascalprecht.translate',
      'angular-jwt',
      'angularModalService',
      'angucomplete-alt'
    ]);
    PawnsAtWar
      .config(
        ['$routeProvider',
          '$controllerProvider',
          '$compileProvider',
          '$filterProvider',
          '$provide',
          '$translateProvider',
          '$httpProvider',
          'jwtOptionsProvider',
          function ($routeProvider, $controllerProvider, $compileProvider, $filterProvider, $provide, $translateProvider, $httpProvider, jwtOptionsProvider) {

            PawnsAtWar.controller = $controllerProvider.register;
            PawnsAtWar.directive = $compileProvider.directive;
            PawnsAtWar.filter = $filterProvider.register;
            PawnsAtWar.factory = $provide.factory;
            PawnsAtWar.service = $provide.service;

            if (config.routes !== undefined) {
              angular.forEach(config.routes, function (route, path) {
                $routeProvider.when(path, {
                  templateUrl: route.templateUrl,
                  resolve: dependencyResolverFor(['controllers/' + route.controller]),
                  controller: route.controller,
                  gaPageTitle: route.gaPageTitle
                });
              });
            }
            if (config.defaultRoutePath !== undefined) {
              $routeProvider.otherwise({redirectTo: config.defaultRoutePath});
            }

            $translateProvider.translations('preferredLanguage', i18n);
            $translateProvider.preferredLanguage('preferredLanguage');
            $translateProvider.useSanitizeValueStrategy('escape');

            //jwt config
            $httpProvider.interceptors.push('jwtInterceptor');
            jwtOptionsProvider.config({
              unauthenticatedRedirectPath: '#!/login',
              tokenGetter: function () {
                return localStorage.getItem('token')
              },
              authHeader: 'X-AUTH-TOKEN',
              authPrefix: ''
            });
          }]);
    PawnsAtWar.run(function ($rootScope, $location, authManager, $window) {
      authManager.checkAuthOnRefresh();
      //authManager.redirectWhenUnauthenticated();
      $rootScope.$on('$routeChangeStart', function (event, next, current) {
        // if logged in and trying to access loggin => redirect to map
        if (authManager.isAuthenticated() && ($location.url().includes('login'))) {
          $window.location.href = '#!/map'
        }
        // if route requires auth and user is not logged in
        if (!authManager.isAuthenticated() && ($location.url != '#!/login')) {
          $window.location.href = '#!/login'
        }

      });
    });


    return PawnsAtWar;
  }
);
