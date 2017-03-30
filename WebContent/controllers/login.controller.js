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
            
            LoginService.Login(vm.username, vm.password,vm.userProfileId, function (response) { 
                if(response.status === 700) {
                    alert("shit");
                    vm.dataLoading = false;
                }	
                if (response.status === 200) {
                    LoginService.SetCredentials(vm.username, vm.password);

                    switch(vm.userProfileId) {
                        case "1":
                            vm.dataLoading = false;
                            $location.path('/admin.home');
                            break;
                        case "2":
                            vm.dataLoading = false;
                            $location.path('/company.home');
                            break;
                        case "3":
                             vm.dataLoading = false;
                            $location.path('/customer.home');
                            break;
                        default:
                            vm.dataLoading = false;
                            $location.path('/');
                           
                        }     
                } 
                else {
                    alert("Login failed");
                    vm.dataLoading = false;
                }
            });
        }
    }

})();

  

