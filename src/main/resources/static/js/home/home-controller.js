angular.module('id.co.blogspot.fathan.netanalytic.controller.home', []);
angular.module('id.co.blogspot.fathan.netanalytic.controller.home').controller(
		'homeController',
		[ '$scope', 'cluster', 'filterTotalPerCluster', 'filterAllEditable', 'bulkSave',
				function($scope, cluster, filterTotalPerCluster, filterAllEditable, bulkSave) {
					var generateUUID = function() {
						var d = new Date().getTime();
						var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
							var r = (d + Math.random() * 16) % 16 | 0;
							d = Math.floor(d / 16);
							return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
						});
						return uuid;
					}
					var increaseLoading = function(code) {
						if (typeof $scope.loadings[code] == 'undefined') {
							$scope.loadings[code] = 1;
						} else {
							$scope.loadings[code]++;
						}
					}
					var decreaseLoading = function(code) {
						if (typeof $scope.loadings[code] == 'undefined') {
							$scope.loadings[code] = 0;
						} else {
							$scope.loadings[code]--;
						}
					}
					var postFilterTotalPerCluster = function(totalPerClusters) {
						$scope.totalPerClusterLabels = [];
						$scope.totalPerClusterDatas = [];
						for (var i = 0; i < $scope.centroids.length; i++) {
							$scope.totalPerClusterLabels.push('Cluster ' + (i + 1));
							var exist = false;
							for (var j = 0; j < totalPerClusters.length; j++) {
								if (i == totalPerClusters[j][0]) {
									$scope.totalPerClusterDatas.push(totalPerClusters[j][1]);
									exist = true;
									break;
								}
							}
							if (!exist) {
								$scope.totalPerClusterDatas.push(0);
							}
						}
					}
					$scope.cluster = function(requestId) {
						increaseLoading('cluster');
						var startTime = new Date();
						cluster.get({
							'requestId' : requestId
						}, function(response) {
							if (response.success) {
								$scope.centroids = response.value;
								$scope.filterTotalPerCluster(requestId);
							}
							var endTime = new Date();
							$scope.clusterTime = endTime.getTime() - startTime.getTime();
							decreaseLoading('cluster');
						}, function(response) {
							decreaseLoading('cluster');
						});
					}
					$scope.filterTotalPerCluster = function(requestId) {
						increaseLoading('cluster');
						filterTotalPerCluster.get({
							'requestId' : requestId
						}, function(response) {
							if (response.success) {
								postFilterTotalPerCluster(response.value);
							}
							decreaseLoading('cluster');
						}, function(response) {
							decreaseLoading('cluster');
						});
					}
					$scope.filterAllEditable = function(requestId) {
						increaseLoading('systemParameter');
						filterAllEditable.get({
							'requestId' : requestId
						}, function(response) {
							if (response.success) {
								$scope.systemParameters = response.content;
							}
							decreaseLoading('systemParameter');
						}, function(response) {
							decreaseLoading('systemParameter');
						});
					}

					$scope.bulkSave = function(requestId, systemParameters) {
						increaseLoading('bulkSave');
						bulkSave.post({
							'requestId' : requestId,
							'systemParameters' : systemParameters
						}, function(response) {
							if (response.success) {
								$scope.filterAllEditable(requestId);
							}
							decreaseLoading('bulkSave');
						}, function(response) {
							decreaseLoading('bulkSave');
						});
					}
					$scope.isLoading = function(code) {
						return typeof $scope.loadings[code] != 'undefined' && $scope.loadings[code] != 0;
					}
					$scope.isCentroidsExist = function() {
						return typeof $scope.centroids != 'undefined';
					}
					$scope.clickCluster = function() {
						$scope.centroids = [];
						$scope.cluster(generateUUID());
					}
					$scope.clickSave = function() {
						$scope.bulkSave(generateUUID(), $scope.systemParameters);
					}
					$scope.initialize = function() {
						$scope.loadings = [];
						$scope.clusterTime = 0;
						$scope.filterAllEditable(generateUUID());
					}
					$scope.initialize();
				} ]);