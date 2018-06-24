var myApp = angular.module('myApp', ['ngRoute','ngSanitize', 'mgcrea.ngStrap', 'ngResource']);

myApp.config(function($routeProvider,$locationProvider){    //This will config the different pages with different controllers loading.
  $routeProvider.when('/', {
    controller: 'indexCtrl',
    templateUrl: '/assets/partials/index.html'
  });

  $routeProvider.when('/add-task', {
          controller: 'addtaskCtrl',
          templateUrl: '/assets/partials/add-contact.html'
  });

  $routeProvider.when('/task/:id', {
          controller: 'taskCtrl',
          templateUrl: '/assets/partials/contact.html'
  }),

  $routeProvider.when('/completed/', {
          controller: 'completedCtrl',
          templateUrl: '/assets/partials/completed.html'
  }),

  $routeProvider.when('/uncompleted/', {
          controller: 'uncompletedCtrl',
          templateUrl: '/assets/partials/completed.html'
  }).


  otherwise({redirectTo:'/'});              //if it can't load the page it will redirect to index

   $locationProvider.html5Mode({
          enabled: true,
          requireBase: false
  });
});

myApp.factory('tasks', function($resource){   //these resources used to load different pages
       var resource = $resource('http://localhost:5000/tasks/:id', {id: '@id'}, {update: {method: 'PUT'},});
       var resource1 = $resource('http://localhost:5000/task/:id', {id: '@id'}, {update: {method: 'PUT'},});
       var resource2 = $resource('http://localhost:5000/task/:id', {id: '@id'}, {update: {method: 'PUT'},});
       var resource3 = $resource('http://localhost:5000/tasks/completed/:id', {id: '@id'}, {update: {method: 'PUT'},});
       var resource4 = $resource('http://localhost:5000/tasks/uncompleted/:id', {id: '@id'}, {update: {method: 'PUT'},});
        return {
           get: function(){ return resource.query();},
           getCompleted: function(){ return resource3.query();},
           getUncompleted: function(){ return resource4.query();},          //this will do different things with these resources...
           find: function(id){return resource2.query({id: id});},           //such as finding, getting, creating and deleting a task
           create: function(){ return new resource();},
           destroy: function(id){resource1.delete({id:id})},
          };
   })

myApp.directive('editable', function(){
       return {
           restrict: 'A',
           templateUrl: '/assets/partials/editable.html',         //this will create directive for the editable.html
           scope: {
               value: '=editable',
               field: '@fieldType'
           },
           controller: function($scope){
             $scope.field = ($scope.field) ? $scope.field : 'text';       //will read the field entered

             $scope.toggleEditor = function(){
               $scope.field = ($scope.field) ? $scope.field : 'text';   //will read field when in the toggle()
               $scope.editor.showing = !$scope.editor.showing;
             };
             $scope.editor = {
               showing: false,                      //will allow to edit value
               value: $scope.value
             };
             $scope.save = function(){
               $scope.value = $scope.editor.value;      //this will save value and toggle editor to hide.
               $scope.$emit('saved');
               $scope.toggleEditor();
             };

           }
       };
})

myApp.controller('AppCtrl', function($scope,$location) {
  $scope.startSearch = function(){              //this will search the website and redirect to index
    $location.path('/');
  };
});

myApp.controller('indexCtrl', function($scope,tasks, $alert){
  $scope.tasks = tasks.get();
  $scope.$on('saved', function(){                     //will save the value that is in the index
      $timeout(function(){
                 $scope.task.$update();
      }, 0);
  });

  $scope.delete = function(index){
      tasks.destroy($scope.tasks[index].id);          //will delete the value that is entered.
      $scope.tasks.splice(index, 1);
  };

});

myApp.controller('addtaskCtrl', function($scope, $routeParams,tasks){
  $scope.task = tasks.create();
  $scope.submit = function(){
         $scope.task.$save();                     //this will allow you to add a task and show a message if added.
         $scope.task = tasks.create();
         $scope.added = true;
     };
});

myApp.controller('taskCtrl', function($scope, $routeParams, tasks, $timeout, $resource){
      var response = tasks.find($routeParams.id)
      response.$promise.then(function onSuccess(data){              //will show the value/data
        $scope.task = data[0];
      },
      function onFail(data){                            //else, will show a message if there is a problem.
        window.alert('error');
      });

      $scope.$on('saved', function(){                     //will update value when submit pressed.
          $timeout(function(){
                     $scope.task.$update();
          }, 0);
      });
});

myApp.controller('completedCtrl', function($scope,tasks, $alert){
  $scope.tasks = tasks.getCompleted();
  $scope.$on('saved', function(){                   //this will allow you to save the value that is entered.
      $timeout(function(){
                 $scope.task.$update();
      }, 1);
  });

  $scope.delete = function(index){
      tasks.destroy($scope.tasks[index].id);      // this will delete the value when pressed.
      $scope.tasks.splice(index, 1);
  };
});

myApp.controller('uncompletedCtrl', function($scope,tasks, $alert){
  $scope.tasks = tasks.getUncompleted();
  $scope.$on('saved', function(){                   //this will allow you to update value entered.
      $timeout(function(){
                 $scope.task.$update();
      }, 1);
  });

  $scope.delete = function(index){
      tasks.destroy($scope.tasks[index].id);        //this will delete value when pressed.
      $scope.tasks.splice(index, 1);
  };
});
