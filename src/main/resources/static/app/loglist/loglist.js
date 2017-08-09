(function () {
    "use strict";
    angular.module('widget.log-list', [])
        .controller('LogListController', ['$scope', '$http', '$interval', '$timeout',
            function ($scope, $http, $interval, $timeout) {
                $scope.logList = [];
                $scope.logInterval = 5000;
                $scope.newInterval = 5;

                $scope.setInterval = function () {
                    $http({
                        method: 'POST',
                        url: 'rest/interval',
                        data: $scope.newInterval
                    });
                    $scope.logInterval = $scope.newInterval * 1000;
                };

                $scope.getInterval = function () {
                    console.log("hello interval get");
                    return $scope.logInterval;
                };

                function loop(fn) {
                    $timeout(function() {
                        fn();
                        loop(fn);
                        }, $scope.getInterval());
                }

                loop(function () {

                    $http({
                        method: 'GET',
                        url: 'rest/log'
                    }).then(function successCallback(response) {
                        if (response.data && response.data.length > 0) {
                            $scope.logList.push(response.data);
                        }
                    });

                    $http({
                        method: 'GET',
                        url: 'rest/interval'
                    }).then(function successCallback(response) {
                        $scope.logInterval = response.data * 1000;
                    });

                });

            }]);
})();