(function () {
  'use strict';

  var app = angular.module("SolInvictus");
  app.controller('LoginController',LoginController);

  LoginController.$inject = ['LoginService']
  function LoginController(LoginService) {
    var vm = this;

    vm.login = login;

    (function initController() {
      AuthService.ClearCredentials();
    })

    //On login function
    function login() {
      vm.dataLoading = true;

      AuthService.Login(vm.username, vm.password, vm.userProfileId, function(response) {}) {
      }
    }
  }




















})();
