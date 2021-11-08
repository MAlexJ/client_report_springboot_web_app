'use strict';

let app = angular.module('app', ['ui.router', 'ngSanitize']);

app.config(function ($locationProvider, $stateProvider, $urlRouterProvider) {
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('');
    let states = [
        {
            name: 'home',
            url: '/',
            component: 'home'
        },
        {
            name: 'report',
            url: '/report',
            component: 'report'
        },
        {
            name: 'swagger',
            url: '/swagger',
            component: 'swagger'
        }
    ];

    states.forEach(function (state) {
        $stateProvider.state(state);
    });
    $urlRouterProvider.otherwise("/");
});