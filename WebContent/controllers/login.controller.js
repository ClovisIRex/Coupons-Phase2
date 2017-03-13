(function() {
var app = angular.module("SolInvictus", ["ngRoute"]);

app.controller('LoginController', ['$http', '$scope', '$window', function($http, $scope, $window){
  this.login = function () {
    var user = JSON.stringify($scope.user);
    $http.post('/Coupons-Phase2/rest/login', user)
  }

}])});

/*
  this.login = function (){
    var user = JSON.stringify($scope.user);
    $http.post('/Coupons-Phase2/rest/login', user).success(function(){
      if ($scope.user.type==1){
        $window.location.assign('/Coupons-Phase2/#admin');
      }
      else if($scope.user.type==2){
        $window.location.assign('/Coupons-Phase2/#company');
      }
      else{
        $window.location.assign('/Coupons-Phase2/#customer');
      }
    })

  }
}])
*/

//}])();
