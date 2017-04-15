(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('AdminController', AdminController);

    AdminController.$inject = ['$cookies','$rootScope','LoginService','$location'];
    
    function AdminController($cookies,$rootScope, LoginService,$location) {
        var vm = this;        
        vm.logout = logout;
        vm.user = "test";
        loadCurrentUser();

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }
        
        function logout() {
            vm.dataLoading = true;
            LoginService.Logout(function (response) {
                if(response) {
                    $location.path('/');
                    vm.dataLoading = false;
                }
            });     
        }
    }
})();