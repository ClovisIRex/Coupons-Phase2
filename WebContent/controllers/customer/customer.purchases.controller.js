(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('CustomerPurchasesController', CustomerPurchasesController);

    CustomerPurchasesController.$inject = ['$location','$cookies', '$rootScope','CouponService','LoginService'];
    
    function CustomerPurchasesController($location, $cookies, $rootScope, CouponService, LoginService) {
        
    	var vm = this;
        vm.user = null;
        vm.newCoupon = null;
                

        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.searchCoupon   = '';

        vm.allCoupons = [];

        vm.getAllCoupons = getAllCoupons;
        vm.setCurrentCoupon = setCurrentCoupon;
		vm.logout = logout;


        initController();

        function initController() {
        	loadCurrentUser();
            getAllCoupons();
        }

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCoupon(coupon) {
            vm.updateCouponStatus = null;
        	vm.currentCoupon = coupon;
        	vm.currentCouponNew = angular.copy(coupon);

            var price = Math.floor ( vm.currentCouponNew.couponPrice ); 
			vm.currentCouponNew.couponPrice = price;      	
        }


        function getAllCoupons() {
            vm.dataLoading = true;
            var name = $rootScope.globals.currentUser.username;
            CouponService.getAllPurchasedCouponsbyCustomerName(name,function (response) {
                if (!(response.status == 700 || response.status == 500 || response.status === undefined)) {
                	vm.coupons = [];
                    var couponsArr = vm.coupons.concat(response.data.coupon);  
                    var index;
                    for(index =0; index < couponsArr.length; index ++) {
                        couponsArr[index].couponStartDate = moment.unix(couponsArr[index].couponStartDate).format('DD/MM/YYYY');
                        couponsArr[index].couponEndDate = moment.unix(couponsArr[index].couponEndDate).format('DD/MM/YYYY');
                    }
                    vm.allCoupons = couponsArr;    	      		                    
                } else {
                    vm.dataLoading = false;
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
