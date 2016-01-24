/**
 * Created by wkm on 16-01-22.
 */

var modifyMenuApp = angular.module("modifyMenuApp",[],function($httpProvider){
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
modifyMenuApp.controller("modifyMenuController",function($scope,$http){
    //init form
    $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
    //close message
    $scope.showMessage = false;
    $scope.showWarnMessage = false;
    //load root
    $http.get('loadRootMenus').success(function(response){
        $scope.roots = response;
    });
    //load all menu
    $http.get('loadMenus').success(function(response){
        $scope.menus = response;
    });

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
    }

    //add root menu
    $scope.addRootMenu = function(){
        $scope.itemForm = {"id":"","parentId":"root","menuName":"","menuLink":"rootLink"};
    }

    //save menu
    $scope.saveMenuItem = function(){
        var data = "id=" + $scope.itemForm.id + "&parentId=" + $scope.itemForm.parentId +
            "&menuName=" + $scope.itemForm.menuName + "&menuLink=" + $scope.itemForm.menuLink;
        $http.post("/saveChildMenu",data).success(function(response){
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
    }

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
    }

    //load all menus
    $scope.loadAllMenus = function(){
        $http.get('/loadMenus').success(function(response){
            $scope.menus = response;
        });
    }
});
