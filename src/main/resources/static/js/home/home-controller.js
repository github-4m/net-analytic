angular.module('id.co.blogspot.fathan.netanalytic.controller.home', []);
angular.module('id.co.blogspot.fathan.netanalytic.controller.home').controller(
		'homeController',
		[
				'$scope',
				'cluster',
				'filterTotalPerCluster',
				'filterAllEditable',
				'bulkSave',
				'filterAll',
				'calculateSilhouetteCoefficient',
				function($scope, cluster, filterTotalPerCluster, filterAllEditable, bulkSave, filterAll,
						calculateSilhouetteCoefficient) {
					var colors = [ '#46BFBD', '#FDB45C', '#949FB1', '#4D5360', '#803690', '#00ADF9', '#DCDCDC' ];
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
						$scope.totalPerClusterColors = [];
						for (var i = 0; i < $scope.centroids.length; i++) {
							$scope.totalPerClusterLabels.push('Cluster ' + (i + 1));
							$scope.totalPerClusterColors.push(colors[i]);
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
					var postFilterAll = function(networkAccesses) {
						$scope.networkAccessesSeries = [];
						$scope.networkAccessesDatas = [];
						$scope.networkAccessesColors = [];
						$scope.networkAccessesLabels = [ 'Attribute 1', 'Attribute 2', 'Attribute 3', 'Attribute 4',
								'Attribute 5' ];
						for (i in networkAccesses) {
							$scope.networkAccessesSeries.push('Cluster ' + (networkAccesses[i][5] + 1));
							$scope.networkAccessesColors.push(colors[networkAccesses[i][5]]);
							$scope.networkAccessesDatas.push([ networkAccesses[i][0] / 100000,
									networkAccesses[i][1] * 1000, networkAccesses[i][2], networkAccesses[i][3],
									networkAccesses[i][4] / 1000 ]);
						}
					}
					var postCalculateSilhouetteCoefficient = function(silhouetteCoefficient) {
						$scope.silhouetteValuePerData = silhouetteCoefficient[0];
						$scope.silhouetteValueAveragePerCluster = silhouetteCoefficient[1];
					}
					$scope.cluster = function(requestId) {
						increaseLoading('cluster');
						var startTime = new Date();
						cluster.get({
							'requestId' : requestId
						}, function(response) {
							var endTime = new Date();
							$scope.clusterTime = endTime.getTime() - startTime.getTime();
							if (response.success) {
								$scope.centroids = response.value;
								$scope.filterTotalPerCluster(requestId);
								$scope.filterAll(requestId);
							}
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
					$scope.filterAll = function(requestId) {
						increaseLoading('networkAccesses');
						filterAll.get({
							'requestId' : requestId
						}, function(response) {
							if (response.success) {
								postFilterAll(response.content);
							}
							decreaseLoading('networkAccesses');
						}, function(response) {
							decreaseLoading('networkAccesses');
						});
					}
					$scope.calculateSilhouetteCoefficient = function(requestId) {
						increaseLoading('silhouetteCoefficient');
						calculateSilhouetteCoefficient.get({
							'requestId' : requestId
						}, function(response) {
							if (response.success) {
								postCalculateSilhouetteCoefficient(response.value);
							}
							decreaseLoading('silhouetteCoefficient');
						}, function(response) {
							decreaseLoading('silhouetteCoefficient');
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
						$scope.clusterOfData = false;
						$scope.cluster(generateUUID());
					}
					$scope.clickCalculate = function() {
						$scope.calculateSilhouetteCoefficient(generateUUID());
					}
					$scope.clickSave = function() {
						$scope.bulkSave(generateUUID(), $scope.systemParameters);
					}
					$scope.clickShowClusterOfData = function() {
						if ($scope.clusterOfData) {
							$scope.clusterOfData = false;
						} else {
							$scope.clusterOfData = true;
						}
					}
					$scope.initialize = function() {
						$scope.loadings = [];
						$scope.clusterTime = 0;
						$scope.clusterOfData = false;
						$scope.filterAllEditable(generateUUID());
						$scope.silhouetteValuePerData = null;
						$scope.silhouetteValueAveragePerCluster = null;
					}
					$scope.initialize();
				} ]);