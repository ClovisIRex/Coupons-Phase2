(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope','$location','$cookies','LoginService'];
    function LoginController($scope,$location, $cookies, LoginService) {
        var vm = this;

        vm.login = login;
        

        (function initController() {
            // reset login status
            LoginService.ClearCredentials();
        })();

        function login() {
            vm.dataLoading = true;
            LoginService.Login(vm.username, vm.password,vm.userProfileId,function (response) {
                if(response) {
                    LoginService.SetCredentials(vm.username, vm.password);
                    LoginService.setCurrentUser({username: vm.username});
                    switch(vm.userProfileId) {
                        case "1":
                            $location.path('/admin.home');
                            break;
                        case "2":
                            $location.path('/company.home');
                            break;
                        case "3":
                            $location.path('/customer.home');
                            break;
                        default:
                            $location.path('/');
                        }
                } else {
                    vm.dataLoading = false;
                }
            });     
        }
    }
})();

  

