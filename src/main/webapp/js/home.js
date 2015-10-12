/**
 * Created by wkm on 15-10-11.
 */

var topMenu = angular.module("topMenu",[],function($httpProvider){
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
});
topMenu.controller("loadMenuController",function($scope,$http){
    $scope.manageShow = false;
    $scope.showMessage = false;
    $scope.showWarnMessage = false;
    $scope.showLink = false;
    $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
    $http.get('loadMenus').success(function(response){
        $scope.menus = response;
    });
    $scope.showMangeMenu = function(){
        $scope.manageShow = !$scope.manageShow;
    }
    $scope.editMenuItem = function(itemId){
        $scope.showWarnMessage = false;
        $http.get('/loadChildMenu/' + itemId).success(function(response){
            var result = response;
            if(result.isSuccess){
                $scope.itemForm = result.data;
                $scope.showItemForm = true;
                $scope.showLink = true;
            } else {
                $scope.warnMessage = result.message;
                $scope.showWarnMessage = true;
            }
        });

    }
    $scope.saveMenuItem = function(){
        var data = "id=" + $scope.itemForm.id + "&parentId=" + $scope.itemForm.parentId +
            "&menuName=" + $scope.itemForm.menuName + "&menuLink=" + $scope.itemForm.menuLink;
        $http.post("/saveChildMenu",data).success(function(response){
            var result = response;
            if(result.isSuccess){
                $scope.message = result.message;
                $scope.showMessage = true;
                $http.get('/loadMenus').success(function(response){
                    $scope.menus = response;
                });
            } else {
                $scope.warnMessage = result.message;
                $scope.showWarnMessage = true;
            }
        });
    }
    $scope.deleteMenuItem = function(itemId){
        $http.post("/deleteMenu/" + itemId).success(function(response){
            var result = response;
            if(result.isSuccess){
                $scope.message = result.message;
                $scope.showMessage = true;
                $http.get('/loadMenus').success(function(response){
                    $scope.menus = response;
                });
                $scope.showItemForm = false;
            } else {
                $scope.warnMessage = result.message;
                $scope.showWarnMessage = true;
            }
        });
    }
    $scope.cancelEditMenuItem = function(){
        $scope.showItemForm = false;
    }
    $scope.addRootMenu = function(){
        $scope.showItemForm = true;
        $scope.showLink = false;
        $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
        $scope.itemForm.parentId="root";
        $scope.itemForm.menuLink="rootLink";
    }
    $scope.addChildMenu = function(parentId){
        $scope.showItemForm = true;
        $scope.itemForm = {"id":"","parentId":"","menuName":"","menuLink":""};
        $scope.itemForm.parentId = parentId;
        $scope.showLink = true;
    }
});
