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
		.when("/", {
			templateUrl : "login/login.htm"
		})
		.when("/login", {
			controller: "LoginController",
			templateUrl : "login/login.htm",
			controllerAs: "vm"
		})
		.when("/register.home", {
			templateUrl : "register/register.home.htm"
		})
		.when('/register.customer', {
				controller: 'RegisterCustomerController',
				templateUrl: 'register/register.customer.htm',
				controllerAs: 'vm'
			})

			.when('/register.company', {
				controller: 'RegisterCompanyController',
				templateUrl: 'register/register.company.htm',
				controllerAs: 'vm'
			})

		/*
		---------ADMIN VIEWS--------
		*/
		.when('/admin.home', {
				//controller: 'AdminController',
				templateUrl: 'admin/shit.htm',
				//controllerAs: 'vm'
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
		.otherwise({ redirectTo: '/login' });
	}
	
		run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
		function run($rootScope,$location,$cookies, $http) {
			// keep user logged in after page refresh

			$rootScope.globals = $cookies.getObject('globals') || {};
			if ($rootScope.globals.currentUser) {
				$http.defaults.headers.common['.Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
			}

			/*$rootScope.$on('$locationChangeStart', function (event, next, current) {
				// redirect to login page if not logged in and trying to access a restricted page
				var restrictedPage = $.inArray($location.path(), ['/register', '/login', '/register.home', '/register.customer', '/register.company']) === -1;
				var loggedIn = $rootScope.globals.currentUser;
				if (restrictedPage && !loggedIn) {
					$location.path('/login');
				}
			});*/
		}
})();
	



