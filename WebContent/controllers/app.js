var app = angular.module("SolInvictus", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "login/login.htm"
    })
    .when("/login", {
        templateUrl : "login/login.htm",
        controller: "LoginController",
        controllerAs: "vm"

    })
    .when("/register", {
        templateUrl : "register/register.htm"
    })
    .otherwise({ redirectTo: '/login' });
});
