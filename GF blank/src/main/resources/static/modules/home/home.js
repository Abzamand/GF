'user strict';

angular.module('HomeApp').controller(
		'HomeCtrl',
		[ '$scope', '$http', 'Restful', 'CONSTANTS', '$rootScope','$location','$localStorage',
		function($scope, $http, Restful, CONSTANTS, $rootScope,$location,$localStorage) {
			var callBackTest = function(response,scope){
				if(response!=null && response.data.result){
					$scope.listNews = response.data.result.result;
					for(var i=0; i < $scope.listNews.length; i++){
						$scope.listNews[i].created_date = new Date($scope.listNews[i].created_date);
					}
					$localStorage.listNews = $scope.listNews;
				}else{
					window.alert(response.data.message);
				}
				$('#loaderContainer').css('display','none');
			};
			
			var callBackErrorTest = function(){
				$('#loaderContainer').css('display','none');
			};
			$scope.init = function(){
				Restful.httpGet('api/v1/home/news',null, null, callBackTest, callBackErrorTest, $scope, false, false);
			};
			$scope.goNews = function(data) {
				$localStorage.news = data;
				$location.path("/newsDetail");
			};
			
			var slideIndex = 0;
			var timer = null;
			showSlides();
			function showSlides() {
			  var i;
			  var slides = document.getElementsByClassName("slide");
			  for (i = 0; i < slides.length; i++) {
			    slides[i].style.opacity = "0";
			  }
			  slideIndex++;
			  if (slideIndex > slides.length) {slideIndex = 1}    
			  if(slides[slideIndex-1]){
				  slides[slideIndex-1].style.opacity = "100";  
			  }
			  timer = setTimeout(showSlides, 5000); // Change image every 2 seconds
			}
			
			$scope.slideBefore = function(){
				slideIndex--;
				pickSlides()
			}
			$scope.slideAfter = function(){
				slideIndex++;
				pickSlides()
			}
			function pickSlides() {
				  var i;
				  var slides = document.getElementsByClassName("slide");
				  for (i = 0; i < slides.length; i++) {
				    slides[i].style.opacity = "0";
				  }
				  if (slideIndex > slides.length) {slideIndex = 1}
				  if (slideIndex < 1) {slideIndex = slides.length}
				  if(slides[slideIndex-1]){
					  slides[slideIndex-1].style.opacity = "100";  
				  }
				  clearTimeout(timer);
				  timer = setTimeout(showSlides, 5000);
				}
		} ]);

