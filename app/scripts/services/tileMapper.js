define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.service('tileMapper', function() {
      this.getImage = function (type) {
        switch (type){
          case 0: return 'images/EMPTY.jpg';
          case 1: return 'images/CASTLE.jpg';
          case 2: return 'images/ARCHERY.jpg';
          case 3: return 'images/BARRACKS.jpg';
          case 4: return 'images/GOLD.jpg';
          case 5: return 'images/TERR_GOLD.jpg';
          case 6: return 'images/MILL.jpg';
          case 7: return 'images/STABLE.jpg';
          default: return '#';
        }
      }
    });

});
