// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers','starter.services','nvd3','ngCordova'])
////$log configure
.config(['$logProvider', function($logProvider){
  $logProvider.debugEnabled(true);
  //TODO:https://github.com/ThomasBurleson/angularjs-logDecorator
}])
///ENV_config
.constant('CONFIG_ENV', {
  'api_endpoint': 'http://localhost:8080/octo-ninja/constellation/',
  'api_version': '5.16.3',
  'stomp_uri':'ws://www.xyz.com:61614/stomp',
  'stomp_protocol':'v11.stomp',
  'debug':false
})
.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
      url: "/app",
      abstract: true,
      templateUrl: "templates/menu.html",
      controller: 'MainCtrl'
    })

    .state('app.search', {
      url: "/search",
      views: {
        'menuContent' :{
          templateUrl: "templates/search.html"
        }
      }
    })

    .state('app.browse', {
      url: "/browse",
      views: {
        'menuContent' :{
          templateUrl: "templates/browse.html"
        }
      }
    })
    .state('app.dashboard', {
      url: "/dashboard",
      views: {
        'menuContent' :{
          templateUrl: "templates/dashboard.html",
          controller: 'MainCtrl'
        }
      }
    })

    .state('app.single', {
      url: "/dashboard/:personId",
      views: {
        'menuContent' :{
          templateUrl: "templates/persondetail.html",
          controller: 'PersonDetailCtrl'
        }
      }
    });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/dashboard');
});

