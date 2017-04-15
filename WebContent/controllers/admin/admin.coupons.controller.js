(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('AdminCouponsController', AdminCouponsController);

    AdminCouponsController.$inject = ['$location','$cookies', '$rootScope','CouponService','LoginService'];
    
    function AdminCouponsController($location, $cookies, $rootScope,CouponService, LoginService) {
        
    	var vm = this;
        vm.user = null;
        vm.newCoupon = null;
                

        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.updateCouponStatus = null;
        vm.searchCoupon   = '';

        vm.allCoupons = [];


        vm.getAllCoupons = getAllCoupons;
        vm.updateCoupon = updateCoupon;
        vm.removeCoupon = removeCoupon;
        vm.setCurrentCoupon = setCurrentCoupon;
        vm.setCurrentCouponforDelete = setCurrentCouponforDelete;
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

        function setCurrentCouponforDelete(coupon) {
            vm.removeCouponStatus = null;
        	vm.currentCoupon = coupon;
        	vm.currentCouponNew = angular.copy(coupon);
        	
        }


        function getAllCoupons() {
            vm.dataLoading = true;
            CouponService.getAllCoupons(function (response) {
                if (response) {
                	vm.allCoupons = response.data.coupon;                	                    
                } else {
                    vm.dataLoading = false;
                }
            });
        }

        function updateCoupon() {

            vm.couponForUpdate = {
			  	 	"couponId"       :  0,
                    "couponEndDate" : "",
			    	"couponPrice"   : 0
			 };

            var yyyymmdd = moment(vm.currentCouponNew.couponEndDate).format('YYYYMMDD');
            vm.couponForUpdate.couponId = vm.currentCouponNew.couponId;
            vm.couponForUpdate.couponPrice = vm.currentCouponNew.couponPrice;
            vm.couponForUpdate.couponEndDate = yyyymmdd;
            vm.dataLoading = true;
            CouponService.updateCoupon(vm.couponForUpdate,	function (response) {
               if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.updateCouponStatus = "fail";   
                	
                } else {
                    vm.currentCoupon.couponPrice = vm.currentCouponNew.couponPrice;
                	vm.currentCoupon.couponEndDate = vm.currentCouponNew.couponEndDate;
                	vm.updateCouponStatus = "success";
                }
            });    
    		
        }

        function removeCoupon(id) {
            vm.dataLoading = true;        	
            CouponService.removeCoupon(id,function (response) {
                if (response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;
                    vm.dataLoading = false;
                    vm.removeCouponStatus = "fail";      
                } else {
                    vm.removeCouponStatus = "success";
                    getAllCoupons();
                }
            });        	    
        }

      
        
        /*
        
        function getCompanyCoupons() 
        {
            vm.dataLoading = true;
            CouponService.GetCompanyCoupons( function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.coupons = [];
                	if ( response.data.coupons !== undefined )
                	{
                		vm.coupons = vm.coupons.concat(response.data.coupons);
                	}
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };*/
        
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