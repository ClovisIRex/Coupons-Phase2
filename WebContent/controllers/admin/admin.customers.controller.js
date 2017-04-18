(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('AdminCustomersController', AdminCustomersController);

    AdminCustomersController.$inject = ['$location','CustomersService', '$cookies', '$rootScope', 'LoginService'];
    
    function AdminCustomersController($location,CustomersService, $cookies, $rootScope, LoginService) {

        
        var vm = this;

        vm.user = "test";
        vm.currentCustomer = null;
        vm.currentCustomerNew = null;
        vm.updateCustomerStatus = null;
        vm.createdCustomerStatus = null;
        vm.newCustomer = null;
        vm.searchCustomer   = '';     
    
        vm.allCustomers = [];

    

        vm.setCurrentCustomer = setCurrentCustomer;
        vm.setCurrentCustomerforDelete = setCurrentCustomerforDelete;
        vm.updateCustomer = updateCustomer;
        vm.removeCustomer = removeCustomer;
        vm.createCustomer = createCustomer;
        vm.logout = logout;


        initController();

        function initController() {
        	loadCurrentUser();
            getAllCustomers();
        }

       
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCustomer(Customer) {
        	vm.currentCustomer = Customer;
        	vm.currentCustomerNew = angular.copy(Customer);
        	vm.updateCustomerStatus = null;
        }

        function setCurrentCustomerforDelete(Customer) {
        	vm.currentCustomer = Customer;
        	vm.currentCustomerNew = angular.copy(Customer);
        	vm.removeCustomerStatus = null;
        }

        function getAllCustomers() {
            vm.dataLoading = true;
            CustomersService.getAllCustomers(function (response) {
                if (!(response.status == 700 || response.status == 500 || response.status === undefined)) {
                    vm.customers = [];
                	vm.allCustomers = vm.customers.concat(response.data.customer);                	                    
                } else {
                    vm.dataLoading = false;
                }
            });
        }


        function createCustomer() {

            var customer = {
                custName : vm.newCustomer.custName,
                password : vm.newCustomer.password
            };		
            vm.dataLoading = true;
            CustomersService.createCustomer(customer,function (response) {
            if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.createdCustomerStatus = "fail";   
                    
                } else {
                    vm.createdCustomerStatus = "success";
                    getAllCustomers();
                }
            });    
        }
        function updateCustomer() {		
        	vm.dataLoading = true;
            CustomersService.updateCustomer(vm.currentCustomerNew,	function (response) {
               if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.updateCustomerStatus = "fail";   
                	
                } else {
                    vm.currentCustomer.custName = vm.currentCustomerNew.custName;
                    vm.currentCustomer.password = vm.currentCustomerNew.password;
                	vm.updateCustomerStatus = "success";
                }
            });    
        }

        function removeCustomer(id) {
            vm.dataLoading = true;        	
            CustomersService.removeCustomer(id,function (response) {
                if (response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;
                    vm.dataLoading = false;
                    vm.removeCustomerStatus = "fail";      
                } else {
                    vm.removeCustomerStatus = "success";
                    getAllCustomers();
                }
            });        	    
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