/* Custom Directive to be called for focusout  event of any input field of a form*/
amdApp.directive('focusout', [
function() {
	return {
		require : 'ngModel',
		link : function(scope, elem, attrs, ctrl) {
			var inputfield = '#' + attrs.focusout;
			elem.add(inputfield).on('focusout', function() {
				scope.$apply(function() {
					scope.focusout();
				});
			});
		}
	};
}]); 