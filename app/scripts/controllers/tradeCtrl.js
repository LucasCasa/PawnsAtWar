define(['PawnsAtWar', 'services/ApiService'], function(PawnsAtWar) {

  'use strict';
  PawnsAtWar.controller('tradeCtrl', function ($scope, ApiService) {
    $scope.showNewOffer = false;
    $scope.myTrade = [];
    $scope.otherTrade = [];

    $scope.getTrade = function () {

    };

    $scope.createOffer = function() {
      ApiService.createOffer($scope.giveType, $scope.giveAmount, $scope.getType, $scope.getAmount).then(function(response){

      }, function(error){

      });
    };

    //CREATE OFFER JS CODE
    jQuery(document).ready(function () {
      jQuery('#register').attr('disabled', 'disabled');
      jQuery('.quantity').each(function (i, obj) {
        if (!jQuery(this).parents('.resBar').length) {
          jQuery(this).hide();
        }
      });
    });

    $scope.selectRadioGet = function(type) {
      jQuery('input[name=giveType]').each(function (i) {

        if (jQuery(this).attr('value') == type) {
          if (jQuery(this).is(':checked')) {
            var index = 0;
            if (jQuery(this).attr('value') == 0)
              index = 1;
            jQuery('input[name=giveType]').each(function (i) {
              if (jQuery(this).attr('value') != type) {
                jQuery(this).prop("checked", true);
                return;
              }
            });
          }
          jQuery(this).removeAttr('checked');
        }

      });
      $scope.checkSumbitAvailability();
    };

    $scope.selectRadioGive = function(type) {
      jQuery('input[name=getType]').each(function (i) {
        if (jQuery(this).attr('value') == type) {
          if (jQuery(this).is(':checked')) {
            var index = 0;
            if (jQuery(this).attr('value') == 0)
              index = 1;
            jQuery('input[name=getType]').each(function (i) {
              if (jQuery(this).attr('value') != type) {
                jQuery(this).prop("checked", true);
                return;
              }
            });
          }
          jQuery(this).removeAttr('checked');
        }

      });
      $scope.checkSumbitAvailability();
    };

    $scope.checkAnyGet = function(ignore) {
      jQuery('input[name=getType]').each(function (i) {
        if (jQuery(this).attr('value') != ignore) {
          alert("checking");
          jQuery(this).attr('checked', true);
          return;
        }
      });
    };

    $scope.checkAnyGive = function(ignore) {
      jQuery('input[name=giveType]').each(function (i) {
        if (jQuery(this).attr('value') != ignore) {
          alert("checking");
          jQuery(this).attr('checked', true);
          return;
        }
      });
    };

    $scope.checkSumbitAvailability = function() {
      var empty = false;
      // Input labels have values
      jQuery('input').each(function () {
        var numChecked
        if (jQuery(this).val() == '') {
          empty = true;
        }
      });

      // Radios are checked
      if (jQuery('input[name=giveType]:checked').length == 0 || jQuery('input[name=getType]:checked').length == 0) {
        empty = true;
      } else {
        // Different resources are selected
        if (jQuery('input[name=giveType]:checked').val() == jQuery('input[name=getType]:checked').val()) {
          empty = true;
          jQuery('#sameType').css("display", "block");
        } else {
          jQuery('#sameType').css("display", "none");
        }
      }

      if (empty) {
        jQuery('#register').attr('disabled', 'disabled');
      } else {
        jQuery('#register').removeAttr('disabled');
      }

      // Remove possible existing warning
      jQuery('#insAmount').css("display", "none");
    };

  });
});
