(function () {
  'use strict';

  var app = angular.module("SolInvictus");

  app.factory('LoginService',LoginService);

  LoginService.$inject = [$http];

  function LoginService($http) {
    var service = {};

    var currentUser;

    service.Login = Login;
    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;
    service.getCurrentUser = getCurrentUser;
    service.setCurrentUser = setCurrentUser;

    return service;

    function Login(user, pass, clientType, callback) {

        $http.post('/CouponsPhase2/rest/login/', { username: username, password: pass, clientType } )
            .then(function (response) {
                callback(response);
            });
    }
  }
})
