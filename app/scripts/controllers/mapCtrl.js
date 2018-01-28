'use strict';
define(['PawnsAtWar'], function(PawnsAtWar) {

    PawnsAtWar.controller('mapCtrl', function($scope) {
    	$scope.startx = 10;
    	$scope.starty = 30;
      	$scope.rows = [[{'name':'CASTLE.jpg'},{'name':'EMPTY.jpg'},{'name':'EMPTY.jpg'}],[{'name':'EMPTY.jpg'},{'name':'CASTLE.jpg'},{'name':'EMPTY.jpg'}],[{'name':'EMPTY.jpg'},{'name':'EMPTY.jpg'},{'name':'CASTLE.jpg'}]];
    });

});
