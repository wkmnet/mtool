/**
 * Created by wukm on 16-5-20.
 */

toolApp.controller("ToolsController",function($scope,$http,$timeout){
    $scope.bodyConfig = {"index":0};
    $scope.alertConfig = {"show":false,"message":""};
    $scope.unicode = {"source":"","target":"","type":0}
    $scope.convert = function(type){
        $scope.unicode.type = type;
        $http.post("/convert",$scope.unicode).success(function(response){
            if(response.success){
                $scope.unicode = response.data;
                $scope.alertMessage(response.message);
            } else {
                $scope.alertMessage(response.message);
            }
        }).error(function(data, status, headers, config){
            $scope.alertMessage(data + data);
        });
    };

    $scope.alertMessage = function(message){
        $scope.alertConfig.message = message;
        $scope.alertConfig.show = true;
        $timeout(function(){
            $scope.alertConfig.show = false;
        },3000);
    };
});