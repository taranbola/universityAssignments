myApp = angular.module('OAuthDemoApp',['ui.router','ngResource']);
myApp.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('login', {
    url: '/login',
    templateUrl: 'assets/partials/login.html',
    controller: 'LoginController'

  })
  .state('fav', {
    url: '/fav',
    templateUrl: 'assets/partials/fav.html',
    controller: 'FavoriteController'
  })
  .state('secure', {
    url: '/secure',
    templateUrl: 'assets/partials/secure.html',
    controller: 'SecureController'
  });
  $urlRouterProvider.otherwise('/login');
  // creates the route for the url and the partial
});

myApp.factory('api',function($resource) {
  //will get the access token and the username
  accessToken = JSON.parse(window.localStorage.getItem("imgur")).oauth.access_token;
  username = JSON.parse(window.localStorage.getItem("imgur")).oauth.account_username;
  //these are photos that are found from the api url.
  var viral = $resource('https://api.imgur.com/3/gallery/r/cakes/0.json/?client_id=f21cf17adc5c0fe');
  var photos = $resource('https://api.imgur.com/3/account/' + username + '/images/', {}, {
    get: {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + accessToken
      }
    }
  });

  var fav = $resource('https://api.imgur.com/3/account/'+ username + '/favorites/', {}, {
    get: {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + accessToken
      }
    }
  });
  //returns the photos to the controllers.
  return {
    get_photos: function(success, error){
      return photos.get();
    },
    get_viral: function(success, error){
      return viral.get();
    },
    get_fav: function(success, error){
      return fav.get();
    }
  };

});

myApp.controller("LoginController", function($scope, api) {
  //will allow you to login to the api when pressed and redirects to imgur
  var client_id="f21cf17adc5c0fe";
  var redirect_uri="http://localhost:8000/oauth_callback.html";
  response = api.get_viral()
  response.$promise.then(function onSuccess(data) {
    // access data from 'response'
    $scope.viral = data.data;
  },
  function onFail(data) {
    // handle failure
  }),
  $scope.login = function() {
    window.location.href = "https://api.imgur.com/oauth2/authorize?client_id=" + client_id + "&response_type=token"
  }
});

myApp.controller("SecureController", function($scope, api) {
  //will output user info and their pictures they uploaded
  $scope.username = JSON.parse(window.localStorage.getItem("imgur")).oauth.account_username;
  $scope.expire = JSON.parse(window.localStorage.getItem("imgur")).oauth.expires_in;
  $scope.account = JSON.parse(window.localStorage.getItem("imgur")).oauth.account_id;
  response = api.get_photos()
  response.$promise.then(function onSuccess(data) {
    // access data from 'response'
    $scope.photos = data.data;
  },
  function onFail(data) {
    // handle failure
  });
});

myApp.controller("FavoriteController", function($scope, api) {
  //will show the favorited pictures.
  response = api.get_fav()
  response.$promise.then(function onSuccess(data) {
    // access data from 'response'
    $scope.fav = data.data;
  },
  function onFail(data) {
    // handle failure
  });
});
