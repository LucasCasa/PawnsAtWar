define(['PawnsAtWar'], function(PawnsAtWar) {

    'use strict';
    PawnsAtWar.service('tileMapper', function($translate) {
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
          default: return '';
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
          default: return '';
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
          default: return '';
        }
      };

      this.getName = function (type, value) {
        if(type == 'BUILD' || type == 'UPGRADE'){
          switch (value) {
            case 1: return $translate.instant('CASTLE');
            case 2: return $translate.instant('ARCHERY');
            case 3: return $translate.instant('BARRACKS');
            case 4: return $translate.instant('GOLD');
            case 6: return $translate.instant('MILL');
            case 8: return $translate.instant('STABLE');
          }
        }else if(type == 'RECRUIT') {
          switch (value) {
            case 0: return $translate.instant('WARRIOR');
            case 1: return $translate.instant('ARCHER');
            case 2: return $translate.instant('HORSEMAN');
          }
        }
        return '';
      }
    });

});
