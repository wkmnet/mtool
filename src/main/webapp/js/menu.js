/**
 * Created by wkm on 16-01-22.
 */

var modifyMenuApp = angular.module("modifyMenuApp",["ngAnimate"]);

modifyMenuApp.controller("modifyMenuController",function($scope,$http,$timeout){
    //init form
    $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
    //close message
    $scope.alertConfig = {"show":false,"message":""};
    //load root
    $scope.loadRootMenus = function () {
        $http.get('/api/menu/show/root').success(function(response){
            $scope.roots = response;
        });
    };
    //load all menu
    $scope.loadMenus = function () {
        $http.get('/api/menu/show').success(function(response){
            $scope.menus = response;
        });
    };

    //edit menu
    $scope.editMenuItem = function(itemId){
        $http.get('/loadChildMenu/' + itemId).success(function(response){
            var result = response;
            if(result.isSuccess){
                $scope.itemForm = result.data;
                $scope.showMessage = true;
                $scope.message = result.message;
            } else {
                $scope.warnMessage = result.message;
                $scope.showWarnMessage = true;
            }
        });
    };

    $scope.cancelEditMenuItem = function(){
        $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
    };

    //add root menu
    $scope.addRootMenu = function(){
        $scope.itemForm = {"id":"","parentId":"root","menuName":"","menuLink":"rootLink"};
    };

    //add child menu
    $scope.addChildMenu = function(parentId){
        $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
        $scope.itemForm.parentId = parentId;
    };

    //save menu
    $scope.saveMenuItem = function(){
        $http.post('/api/menu/save',$scope.itemForm).success(function(response){
            if(response.success){
                $scope.loadAllMenus();
            }
            $scope.alertMessage(response.message);
        });
    };

    //delete menu with id
    $scope.deleteMenuItem = function(itemId){
        $http.post("/deleteMenu/" + itemId).success(function(response){
            var result = response;
            if(result.isSuccess){
                $scope.message = result.message;
                $scope.showMessage = true;
                $scope.loadAllMenus();
            } else {
                $scope.warnMessage = result.message;
                $scope.showWarnMessage = true;
            }
        });
    };

    //load all menus
    $scope.loadAllMenus = function(){
        $scope.loadRootMenus();
        $scope.loadMenus();
    };

    $scope.loadAllMenus();

    $scope.alertMessage = function(message){
        $scope.alertConfig.message = message;
        $scope.alertConfig.show = true;
        $timeout(function(){
            $scope.alertConfig.show = false;
        },3000);
    };
});
