angular.module('starter.services', [])

//PersonService
.factory('PersonService', function($resource,CONFIG_ENV) {
    var data = $resource(CONFIG_ENV.api_endpoint+'person/create');
    return data;
})
;