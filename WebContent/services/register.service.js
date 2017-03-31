(function () {
    'use strict';

    angular
    .module('SolInvictus')
    .factory('RegisterService',RegisterService);

    RegisterService.$inject = ['$http', '$rootScope', '$timeout'];

    function RegisterService($http, $rootScope,$timeout) {

        var service = {};

        service.createCompany = createCompany;
        service.createCustomer = createCustomer;

        return service;

        function createCompany(company, callback) {

            $http.post('/CouponsPhase2/rest/register/company',company).then(function onSuccess(response) {
                            callback(response);
                        }).catch(function onError(response) {
                            alert("Failed. System error code: " + response.data.internalErrorCode);
                            callback(false);
                        }); 
        }

        function createCustomer(customer, callback) {

            $http.post('/CouponsPhase2/rest/register/customer',customer).then(function onSuccess(response) {
                callback(response);
            }).catch(function onError(response) {
                alert("Failed. System error code: " + response.data.internalErrorCode);
                callback(false);
            });
        }
    }
})();
