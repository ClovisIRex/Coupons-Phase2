  function createCoupon() {
            var coupon = {
                couponTitle : vm.newCoupon.couponTitle,
                couponStartDate : vm.newCoupon.couponStartDate,
                couponEndDate : vm.newCoupon.couponEndDate,
                couponsInStock : vm.newCoupon.couponsInStock,
                couponType : vm.newCoupon.couponType,
                couponMessage : vm.newCoupon.couponsMessage,
                couponPrice: vm.newCoupon.couponPrice,
                companyID: vm.newCoupon.companyID
            };
            vm.dataLoading = true;
            CouponService.createCoupon(coupon,function (response) {
            if(response.status == 700 || response.status == 500 || response.status === undefined) {
                    vm.errorMessage = response.data.message;
                    vm.errorCode = response.data.internalErrorCode;     
                    vm.dataLoading = false;
                    vm.createdCompanyStatus = "fail";   
                    
                } else {
                    vm.createdCompanyStatus = "success";
                    getAllCoupons();
                }
            });    
        }   