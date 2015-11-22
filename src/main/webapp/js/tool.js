/**
 * Created by wkm on 15-11-22.
 */

var toolApp = angular.module("toolApp",[],function($httpProvider){
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});

toolApp.controller("toolController",function($scope,$http){

    $scope.showBody = {"index":0,"message":"","error":false};

    $scope.inputData = "测试";

    $scope.imageResource = {"src":"","index":0,"alt":"空"};

    $scope.showQRTool = function(){
        if($scope.showBody.index == 1){
            $scope.showBody.index = 0;
            return;
        }
        $scope.showBody.index = 1;
    };

    $scope.showImageMessage = function (){
        var data = "resource=" + $scope.inputData;
        $http.post("/tools/image",data).success(function(response){
            var result = response;
            if(result.success){
                $scope.showBody.error = false;
                $scope.showBody.message = result.message;
                $scope.imageResource.src = result.src;
                $scope.imageResource.alt = result.alt;
            }else {
                $scope.showBody.error = true;
                $scope.showBody.message = result.message;
            }
        });
    };



});
