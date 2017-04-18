(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('AdminCompaniesController', AdminCompaniesController);

    AdminCompaniesController.$inject = ['$location','CompanyService', '$cookies', '$rootScope', 'LoginService'];
    
    function AdminCompaniesController($location,CompanyService, $cookies, $rootScope, LoginService) {

        
        var vm = this;

        vm.user = "test";
        vm.currentCompany = null;
        vm.currentCompanyNew = null;
        vm.updateCompanyStatus = null;
        vm.createdCompanyStatus = null;
        vm.newCompany = null;
        vm.searchCompany   = '';     
    
        vm.allCompanies = [];

    

        vm.setCurrentCompany = setCurrentCompany;
        vm.setCurrentCompanyforDelete = setCurrentCompanyforDelete;
        vm.updateCompany = updateCompany;
        vm.removeCompany = removeCompany;
        vm.createCompany = createCompany;
        vm.logout = logout;


        initController();

        function initController() {
        	loadCurrentUser();
            getAllCompanies();
        }

       
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCompany(company) {
            vm.createdCompanyStatus = null;
        	vm.updateCompanyStatus = null;

        	vm.currentCompany = company;
        	vm.currentCompanyNew = angular.copy(company);
            
        }

        function setCurrentCompanyforDelete(company) {
            vm.removeCompanyStatus = null;
        	vm.currentCompany = company;
        	vm.currentCompanyNew = angular.copy(company);
        	
        }

        function getAllCompanies() {
            vm.dataLoading = true;
            CompanyService.getAllCompanies(function (response) {
                if (!(response.status == 700 || response.status == 500 || response.status === undefined)) {
                    vm.companies = [];
                	vm.allCompanies = vm.companies.concat(response.data.company);                	                    
                } else {
                    vm.dataLoading = false;
                }
            });
        }


        function createCompany() {

            var company = {
                compName : vm.newCompany.compName,
                password : vm.newCompany.password,
                email: vm.newCompany.email
            };		
            vm.dataLoading = true;
            CompanyService.createCompany(company,function (response) {
            if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.createdCompanyStatus = "fail";   
                    
                } else {
                    vm.createdCompanyStatus = "success";
                    getAllCompanies();
                }
            });    
        }
        function updateCompany() {		
        	vm.dataLoading = true;
            CompanyService.updateCompany(vm.currentCompanyNew,	function (response) {
               if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.updateCompanyStatus = "fail";   
                	
                } else {
                    vm.currentCompany.password = vm.currentCompanyNew.password;
                	vm.currentCompany.email = vm.currentCompanyNew.email;
                	vm.updateCompanyStatus = "success";
                }
            });    
        }

        function removeCompany(id) {
            vm.dataLoading = true;        	
            CompanyService.removeCompany(id,function (response) {
                if (response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;
                    vm.dataLoading = false;
                    vm.removeCompanyStatus = "fail";      
                } else {
                    vm.removeCompanyStatus = "success";
                    getAllCompanies();
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