(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$location', '$rootScope'];
    function RegisterController($location, $rootScope) {
        var vm = this;

        vm.register = register;

        
        function register() {
            vm.dataLoading = true;
            CompanysService.CreateCompany(	vm.user.companyName, 
            							vm.user.loginName, 
            							vm.user.loginPassword, 
            							vm.user.companyName, 
            							vm.user.companyEmail, 
            							function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                        //$location.path('/login');  
                        login(vm.user.loginName, vm.user.loginPassword);
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };
        
        
        function login( loginName, loginPassword ) 
        {
            LoginService.Login(loginName, loginPassword, function (response) {
            	            	
                if (response.data.userId !== "0" ) 
                {                	
                    LoginService.setCurrentUser( response.data );
                    
                    if (response.data.userProfileId === "2" )
                    {
                        $location.path('/company.home');
                    }
                    else
                	{
                        $location.path('/');                	
                	}
                } 
                else 
                {
                    FlashService.Error("Login failed");
                    vm.dataLoading = false;
                }
            });
        };
        
        
        
    }// RegisterCompanyController
})(); // function ()
