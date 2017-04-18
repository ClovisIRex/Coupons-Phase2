(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .factory('CustomersService', CustomersService);

    CustomersService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function CustomersService($http, $cookies, $rootScope, $timeout) {

    	var service = {};

        service.createCustomer = createCustomer;
        service.removeCustomer = removeCustomer;
        service.updateCustomer = updateCustomer;
        service.getCustomer = getCustomer;
        service.getAllCustomers = getAllCustomers;
        
        return service;

        function createCustomer(customer, callback) {            
        	$http.post('/CouponsPhase2/rest/api/customer', customer)
        	.then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }

        function removeCustomer(customerId , callback) {
            $http.delete('/CouponsPhase2/rest/api/customer/' + customerId)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }

        function updateCustomer(customer ,callback) {
            $http.put('/CouponsPhase2/rest/api/customer' ,customer)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }
        
        function getCustomer(customerId ,callback) {
            $http.get('/CouponsPhase2/rest/api/customer/' + customerId)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }
        
        function getAllCustomers(callback) {
            $http.get('/CouponsPhase2/rest/api/customer')
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }
    }

})();
