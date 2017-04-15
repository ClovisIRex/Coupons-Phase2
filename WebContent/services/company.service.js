(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .factory('CompanyService', CompanyService);

    CompanyService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function CompanyService($http, $cookies, $rootScope, $timeout) {

    	var service = {};

        
        service.createCompany = createCompany;
        service.removeCompany = removeCompany;
        service.updateCompany = updateCompany;
        service.getCompany = getCompany;
        service.getAllCompanies = getAllCompanies;
        
        return service;

        function updateCompany(company , callback) {
            $http.put('/CouponsPhase2/rest/api/company' ,company)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }


        function removeCompany(id , callback) {
            $http.delete('/CouponsPhase2/rest/api/company/' + id)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }

        function createCompany(company , callback) {
            $http.post('/CouponsPhase2/rest/api/company/',company)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });;
        }

        function getCompany(companyId, callback) {
            $http.get('/CouponsPhase2/rest/api/company/' + companyId)
                .then(function (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(response);
                });
        }

        function getAllCompanies(callback) {
            $http.get('/CouponsPhase2/rest/api/company')
                .then(function  OnSuccess (response) {
                    callback(response);
                }).catch(function onError(response) {
                    callback(false);
                });
        }
    }

})();
