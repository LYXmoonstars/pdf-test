(function() {
    'use strict';

    angular
        .module('pdfTestApp')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['Task','$scope','Base64'];

    function TaskController(Task,$scope,Base64) {

        var vm = this;
        // $scope.pdfUrl = 'content/pdf/11.pdf';
        $scope.fileName ="1a9499383afb34eb3a3ebc648f14f489.pdf";
        $scope.filePDF= Base64.encode( $scope.fileName );
        $scope.pdfUrl="api/download/"+ $scope.filePDF;
        $scope.download=function() {
            const link = document.createElement('a');
            link.setAttribute('href',  $scope.pdfUrl);
            link.setAttribute('download',  $scope.fileName);
            link.click();
        }
        vm.tasks = [];

        loadAll();

        function loadAll() {
            Task.query(function(result) {
                vm.tasks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
