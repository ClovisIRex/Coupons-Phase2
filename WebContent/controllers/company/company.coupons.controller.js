(function () {
    'use strict';

    angular
        .module('SolInvictus')
        .controller('CompanyCouponsController', CompanyCouponsController);

    CompanyCouponsController.$inject = ['$location','$cookies', '$rootScope','CouponService','LoginService'];
    
    function CompanyCouponsController($location, $cookies, $rootScope, CouponService, LoginService) {
        
    	var vm = this;
        vm.user = null;
        vm.newCoupon = null;
                

        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.updateCouponStatus = null;
        vm.searchCoupon   = '';

        vm.allCoupons = [];


        vm.getAllCoupons = getAllCoupons;
        vm.createCoupon = createCoupon;
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
            var name = $rootScope.globals.currentUser.username;
            CouponService.getCouponsByCompanyName(name,function (response) {
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

        function createCoupon() {

             var coupon = {
                companyName : $rootScope.globals.currentUser.username,
                couponTitle : vm.newCoupon.couponTitle,
                couponType  : vm.newCoupon.couponType,
                couponStartDate: moment(vm.newCoupon.couponStartDate,'YYYYMMDD').unix(),
                couponEndDate: moment(vm.newCoupon.couponEndDate,'YYYYMMDD').unix(),
                couponsInStock: vm.newCoupon.couponsInStock,
                couponMessage : vm.newCoupon.couponMessage,
                couponPrice   : vm.newCoupon.couponPrice                
            };		

            vm.dataLoading = true;
            CouponService.createCoupon(coupon,function (response) {
            if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.createdCouponStatus = "fail";   
                    
                } else {
                    vm.createdCouponStatus = "success";
                    getAllCoupons();
                }
            });    
            
        }

        function updateCoupon() {

            vm.couponForUpdate = {
                    "companyName" : $rootScope.globals.currentUser.username,
			  	 	"couponId"       :  0,
                    "couponEndDate" : "",
			    	"couponPrice"   : 0
			 };

            var yyyymmdd = moment(vm.currentCouponNew.couponEndDate,'YYYYMMDD').unix();
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
                    getAllCoupons();
                }
            });    
    		
        }

        function removeCoupon(id) {
            vm.dataLoading = true; 
            var name = $rootScope.globals.currentUser.username;        	
            CouponService.removeCoupon(name,id,function (response) {
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
