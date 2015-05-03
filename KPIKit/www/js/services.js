angular.module('starter.services', [])
    //@see: http://www.masnun.com/2013/08/28/rest-access-in-angularjs-using-ngresource.html
    //@see: http://www.sitepoint.com/creating-crud-app-minutes-angulars-resource/
//WxArticleService
    .factory('WxArticleService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'wxArticle', {
            subscriberId: '@subscriberId'
        }, {
            save: {
                method: 'PUT',
                params: {
                    subscriberId: "@subscriberId"
                }
            },
            delete: {
                method: 'DELETE',
                params: {
                    subscriberId: "@subscriberId"
                }
            }
        });
        return data;
    })
//@see http://stackoverflow.com/questions/16627860/angular-js-and-ng-swith-when-emulating-enum
    .factory('Enum', [function () {
        var service = {
            getUUID: function () {
                // http://www.ietf.org/rfc/rfc4122.txt
                var s = [];
                var hexDigits = "0123456789abcdef";
                for (var i = 0; i < 36; i++) {
                    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
                }
                s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
                s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
                s[8] = s[13] = s[18] = s[23] = "-";

                var uuid = s.join("");
                return uuid;
            }
            , getTimestamp: function () {
                var now = new Date;
                var utc_timestamp = Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),
                    now.getUTCHours(), now.getUTCMinutes(), now.getUTCSeconds(), now.getUTCMilliseconds());
                return utc_timestamp;
            }
        };
        return service;
    }])
;