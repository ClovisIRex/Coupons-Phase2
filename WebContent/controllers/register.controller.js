(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$location', '$rootScope','RegisterService'];
    function RegisterController($location, $rootScope, RegisterService) {
        var vm = this;

        vm.registerCompany = registerCompany;
        vm.registerCustomer = registerCustomer;
        
        function registerCompany() {
            vm.dataLoading = true;
            var company = {
                compName : vm.companyName,
                password : vm.companyPassword,
                email: vm.companyEmail
            };
            RegisterService.createCompany(company, function (response) {
                if (response)  {
                    $location.path('/login');
                } else {
                    vm.dataLoading = false;
                }
            });
        }

        function registerCustomer() {
            vm.dataLoading = true;
            var customer = {
                custName : vm.customerName,
                password : vm.customerPassword
            };
            RegisterService.createCustomer(customer, function (response) {
                if (response)  {
                    $location.path('/login');
                } else {
                    vm.dataLoading = false;
                }
            });
        }
    }
})(); 
