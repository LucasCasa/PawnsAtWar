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
          case 8: return 'images/STABLE.jpg';
          default: return '#';
        }
      };

      this.getPngImage = function (type) {
        switch (type){
          case 0: return 'images/EMPTY.png';
          case 1: return 'images/CASTLE.png';
          case 2: return 'images/ARCHERY.png';
          case 3: return 'images/BARRACKS.png';
          case 4: return 'images/GOLD.png';
          case 5: return 'images/TERR_GOLD.png';
          case 6: return 'images/MILL.png';
          case 8: return 'images/STABLE.png';
          default: return '#';
        }
      };
      this.getDescription = function (type) {
        switch (type) {
          case 0: return 'DESC_EMPTY';
          case 1: return 'DESC_CASTLE';
          case 2: return 'DESC_ARCHERY';
          case 3: return 'DESC_BARRACKS';
          case 4: return 'DESC_GOLD';
          case 5: return 'DESC_TERR_GOLD';
          case 6: return 'DESC_MILL';
          case 8: return 'DESC_STABLE';
          default: return '#';
        }
      }
    });

});
