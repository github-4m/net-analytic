angular.module('id.co.blogspot.fathan.netanalytic.service.home', [ 'ngResource' ]);
angular.module('id.co.blogspot.fathan.netanalytic.service.home').factory('cluster', [ '$resource', function($resource) {
	return $resource('network-access/cluster', {}, {
		get : {
			method : 'GET',
			params : {
				'requestId' : '@requestId'
			}
		}
	});
} ]);
angular.module('id.co.blogspot.fathan.netanalytic.service.home').factory('filterTotalPerCluster',
		[ '$resource', function($resource) {
			return $resource('network-access/filter/total-per-cluster', {}, {
				get : {
					method : 'GET',
					params : {
						'requestId' : '@requestId'
					}
				}
			});
		} ]);
angular.module('id.co.blogspot.fathan.netanalytic.service.home').factory('filterAll',
		[ '$resource', function($resource) {
			return $resource('network-access', {}, {
				get : {
					method : 'GET',
					params : {
						'requestId' : '@requestId'
					}
				}
			});
		} ]);
angular.module('id.co.blogspot.fathan.netanalytic.service.home').factory('filterAllEditable',
		[ '$resource', function($resource) {
			return $resource('system-parameter/filter/all-editable', {}, {
				get : {
					method : 'GET',
					params : {
						'requestId' : '@requestId'
					}
				}
			});
		} ]);
angular.module('id.co.blogspot.fathan.netanalytic.service.home').factory('bulkSave',
		[ '$resource', function($resource) {
			return $resource('system-parameter/bulk/save', {}, {
				post : {
					method : 'POST',
					params : {
						'requestId' : '@requestId'
					}
				}
			});
		} ]);