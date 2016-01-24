/**
 * Created by wkm on 16-01-22.
 */

var indexApp = angular.module("indexApp",[],function($httpProvider){
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
indexApp.controller("loadMenuController",function($scope,$http){
    $http.get('loadMenus').success(function(response){
        $scope.menus = response;
    });
});
