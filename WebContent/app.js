var app = angular.module("SolInvictus", ["ngRoute"]);


app.config(function($routeProvider) {
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
    .otherwise({ redirectTo: '/login' });
});
