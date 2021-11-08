'use strict';

app.component('swagger', {
    controller: function ($location, $window, $state, $timeout) {
        let url = $location.absUrl().concat('-ui.html');
        $window.open(url, '_blank');
        $timeout(function () {
            window.location.replace($location.absUrl());
        }, 200);
    },
});