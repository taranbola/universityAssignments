myApp = angular.module('OAuthDemoApp',['ui.router','ngResource']);
myApp.config(function($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: 'assets/partials/login.html',
            controller: 'LoginController'
        })
        .state('secure', {
            url: '/secure',
            templateUrl: 'assets/partials/secure.html',
            controller: 'SecureController'
        });
    $urlRouterProvider.otherwise('/login');
});

myApp.factory('api',function($resource) {
  accessToken = JSON.parse(window.localStorage.getItem("instagram")).oauth.access_token;
  var photos = $resource('https://api.instagram.com/v1/users/self/media/recent/?access_token='+accessToken);
   return {
       get_photos: function(success, error){
          return photos.get();
        },
  };
});

myApp.controller("LoginController", function($scope) {
    var client_id="4214ca7bc5104ab8b1059fa8fb9861b0";
    var scope="public_content";
    var redirect_uri="http://localhost:8000/oauth_callback.html";
    var response_type="token";
    $scope.login = function() {
      window.location.href = "https://api.imgur.com/oauth2/authorize?client_id=" + client_id + "&response_type=token"
    }
});

myApp.controller("SecureController", function($scope, api) {
    $scope.accessToken = JSON.parse(window.localStorage.getItem("instagram")).oauth.access_token;
    response = api.get_photos()
    response.$promise.then(function onSuccess(data) {
      // access data from 'response'
      $scope.photos = data.data;
    },
    function onFail(data) {
    // handle failure
    });
});
