(function () {
	'use strict';

	angular
	.module('SolInvictus')
	.factory('CouponService', CouponService);

	CouponService.$inject = [ '$http', '$cookies', '$rootScope', '$timeout'];
	function CouponService($http, $cookies, $rootScope, $timeout) {
		var service = {};

		service.createCoupon = createCoupon;
        service.removeCoupon = removeCoupon;
        service.updateCoupon = updateCoupon;
        service.purchaseCoupon = purchaseCoupon;
		service.getCouponsByType = getCouponsByType;
		service.getCouponsByCompanyId = getCouponsByCompanyId;
		service.getCouponsByCustomerId = getCouponsByCustomerId;
        service.getAllCoupons = getAllCoupons;
		service.getAllPurchasedCouponsbyType 	= getAllPurchasedCouponsbyType;
		service.getAllPurchasedCouponsbyPrice 	= getAllPurchasedCouponsbyPrice;
		service.getAllPurchasedCoupons = getAllPurchasedCoupons;
        
		
		return service;	
        	
		function createCoupon(coupon ,callback) {
			$http.post('/CouponsPhase2/rest/api/coupon', coupon)
			.then(function (response) {
				callback(response);
			});
		}	

        function removeCoupon(couponId ,callback) {
			$http.delete('/CouponsPhase2/rest/api/coupon/' + couponId)
			.then(function (response) {
				callback(response);
			});
		}	

        function updateCoupon(coupon ,callback) {
			$http.put('/CouponsPhase2/rest/api/coupon', coupon)
			.then(function (response) {
				callback(response);
			});
		}

        function purchaseCoupon(details ,callback) {
			$http.post('/CouponsPhase2/rest/api/coupon/purchase', details)
			.then(function (response) {
				callback(response);
			});
		}

        function getCouponsByType(couponType, callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/'+ couponType)
			.then(function (response) {
				callback(response);
			});
		}

        function getCouponsByCompanyId(companyId, callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/CompanyId/'+ companyId)
			.then(function (response) {
				callback(response);
			});
		}

        function getCouponsByCustomerId(customerId, callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/CustomerId/'+ customerId)
			.then(function (response) {
				callback(response);
			});
		}

		function getAllCoupons(callback) {
			$http.get('/CouponsPhase2/rest/api/coupon')
			.then(function (response) {
				callback(response);
			});
		}

		function getAllPurchasedCouponsbyType(couponType,callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/purchase/type/' + couponType)
			.then(function (response) {
				callback(response);
			});
		}

		function getAllPurchasedCouponsbyPrice(couponPrice,callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/purchase/price/' + couponPrice)
			.then(function (response) {
				callback(response);
			});
		}

		function getAllPurchasedCoupons(callback) {
			$http.get('/CouponsPhase2/rest/api/coupon/purchase')
			.then(function (response) {
				callback(response);
			});
		}
	}
})();
