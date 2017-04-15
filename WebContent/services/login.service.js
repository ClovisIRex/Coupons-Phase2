(function () {
  'use strict';

angular
        .module('SolInvictus')
        .factory('LoginService', LoginService);

  LoginService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];

  function LoginService($http, $cookies, $rootScope, $timeout) {
    var service = {};

    var currentUser;

    service.Login = Login;
    service.Logout = Logout;
    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;
    service.getCurrentUser = getCurrentUser;
    service.setCurrentUser = setCurrentUser;

    return service;

    function Login(user, password, userID, callback) {
        $http.post('/CouponsPhase2/rest/login/', { username: user,
                                                   password: password,
                                                   clientType: userID })
            .then(function onSuccess(response) {
                callback(response);
            }).catch(function onError(response) {
              alert("Failed. System error code: " + response.data.internalErrorCode);
              callback(false);
            });
    }

    function Logout(callback) {
          $http.delete('/CouponsPhase2/rest/login/')
            .then(function onSuccess(response) {
                callback(response);
            }).catch(function onError(response) {
              alert("Failed. System error code: " + response);
              callback(false);
            });
        }

    function ClearCredentials() {
      $rootScope.globals = {};
      $cookies.remove('globals');
      $http.defaults.headers.common.Authorization = 'Basic';
    }

    function getCurrentUser() {
      	return $rootScope.globals.currentUser; 
    }

    function setCurrentUser(inputUser) {
      currentUser = inputUser;
      $rootScope.globals = {currentUser: inputUser};
    }

    function SetCredentials(username, password) {

      var authdata = username + ':' + password;
      // set default auth header for http requests
      $http.defaults.headers.common['.Authorization'] = 'Basic ' + authdata;

     
     /* // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
      var cookieExp = new Date();
      cookieExp.setDate(cookieExp.getDate() + 7);
      $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
      */
    }
  }
})();
