$(function() {
	var playVideo = $('video');
	var playPause = $('.playPause'); //播放和暂停

	playVideo[0].volume = 0.4; //初始化音量
	playPause.on('click', function() {
		playControl();
	});
	$('.playContent').on('click', function() {
		playControl();
	}).hover(function() {
		$('.turnoff').stop().animate({
			'right': 10
		}, 200);
	});

	playVideo.on('ended', function() {
		$('.playTip').removeClass('glyphicon-pause').addClass('glyphicon-play').fadeIn();
		playPause.toggleClass('playIcon');
	});
	
	$(window).keyup(function(event){
		event = event || window.event;
			if(event.keyCode == 32)playControl();
			if(event.keyCode == 27){
			$('.fullScreen').removeClass('cancleScreen');
			$('#willesPlay .playControll').css({
				'bottom': -48
			}).removeClass('fullControll');
			};
		event.preventDefault();
	});

	function playControl() {
		playVideo = $('video');
		console.log(playVideo.get(0).paused);
			playPause.toggleClass('playIcon');
			if (playVideo.get(0).paused) {
				playVideo.get(0).play();
				$('.playTip').removeClass('glyphicon-play').addClass('glyphicon-pause').fadeOut();
			} else {
				playVideo.get(0).pause();
				$('.playTip').removeClass('glyphicon-pause').addClass('glyphicon-play').fadeIn();
			}
		}

});
//music循环播放
function ScrollImgLeft(){ 
var speed=50; 
var scroll_begin = document.getElementById("scroll_begin"); 
var scroll_end = document.getElementById("scroll_end"); 
var scroll_div = document.getElementById("scroll_div"); 
scroll_end.innerHTML=scroll_begin.innerHTML; 
function Marquee(){ 
if(scroll_end.offsetWidth-scroll_div.scrollLeft<=0) 
scroll_div.scrollLeft-=scroll_begin.offsetWidth; 
else 
scroll_div.scrollLeft++; 
} 
var MyMar=setInterval(Marquee,speed); 
scroll_div.onmouseover=function() {clearInterval(MyMar);} 
scroll_div.onmouseout=function() {MyMar=setInterval(Marquee,speed);} 
} 
//加载等待
document.onreadystatechange = function () {
	 	if(document.readyState == "complete"){
	 		$(".loading_bg").fadeOut(200);
	 	}
	 	console.log("document")
	 }