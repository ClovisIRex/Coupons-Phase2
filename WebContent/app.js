(function () { 
	'use strict';
	angular
	.module("SolInvictus", ['ngRoute', 'ngCookies'])
	.config(config)
	.run(run);

	config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];
	function config($routeProvider, $locationProvider, $httpProvider) {
		$locationProvider.hashPrefix('');

		$httpProvider.defaults.withCredentials = true;

		$routeProvider

		/*
		---------LOGIN + REGISTER VIEWS--------
		*/
		.when("/login", {
			controller: "LoginController",
			templateUrl : "login/login.htm",
			controllerAs: "vm"
		})
		.when("/register.home", {
			templateUrl : "register/register.home.htm"
		})
		.when('/register.customer', {
			controller: 'RegisterController',
			templateUrl: 'register/register.customer.htm',
			controllerAs: 'vm'
			})
		.when('/register.company', {
			controller: 'RegisterController',
			templateUrl: 'register/register.company.htm',
			controllerAs: 'vm'
		})

		/*
		---------ADMIN VIEWS--------
		*/
		.when('/admin.home', {
				controller: 'AdminController',
				templateUrl: 'admin/admin.home.view.htm',
				controllerAs: 'vm'
			})

			.when('/admin.companies', {
				controller: 'AdminCompaniesController',
				templateUrl: 'admin/admin.companies.view.htm',
				controllerAs: 'vm'
			})

			.when('/admin.customers', {
				controller: 'AdminCustomersController',
				templateUrl: 'admin/admin.customers.view.htm',
				controllerAs: 'vm'
			})

			.when('/admin.coupons', {
				controller: 'AdminCouponsController',
				templateUrl: 'admin/admin.coupons.view.htm',
				controllerAs: 'vm'
			})
		/*
			---------COMPANY VIEWS--------
		*/
		.when('/company.home', {
				controller: 'CompanyController',
				templateUrl: 'company/company.home.view.htm',
				controllerAs: 'vm'
			})

		.when('/company.coupons', {
				controller: 'CompanyCouponsController',
				templateUrl: 'company/company.coupons.view.htm',
				controllerAs: 'vm'
			})
		
		/*
			---------CUSTOMER VIEWS--------
		*/
		.when('/customer.home', {
				controller: 'CustomerController',
				templateUrl: 'customer/customer.home.view.htm',
				controllerAs: 'vm'
			})
			
		.when('/customer.buy', {
				controller: 'CustomerCouponsController',
				templateUrl: 'customer/customer.buy.coupons.view.htm',
				controllerAs: 'vm'
			})

		.when('/customer.purchases', {
				controller: 'CustomerPurchasesController',
				templateUrl: 'customer/customer.my.coupons.view.htm',
				controllerAs: 'vm'
			})


		.otherwise({ redirectTo: '/login' });
	}
	
	
		run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
		function run($rootScope,$location,$cookies, $http) {
			// keep user logged in after page refresh

			$rootScope.globals = $cookies.getObject('globals') || {};
			if ($rootScope.globals.currentUser) {
				$http.defaults.headers.common['.Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
			}
		}
})();
	



