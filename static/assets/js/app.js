(function() {
  'use strict';

  angular
    .module('app', ['selectize', 'NgSwitchery', 'inspinia', 'common', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngMessages', 'ngResource', 'ui.bootstrap', 'datePicker', 'ngTable', "pascalprecht.translate", 'ui.select', 'smart-table'])
    .config(function(stConfig) {
        stConfig.pagination.template = '/components/_c-st-pagination.html';
    });

})();
