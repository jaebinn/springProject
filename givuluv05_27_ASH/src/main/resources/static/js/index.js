const counter = ($counter, $max) => {
	let now = $max;
	//console.log("result : "+$counter,$max)
	const handle = setInterval(() => {
		let figure = Math.ceil($max - now) 
		let result = figure.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		$counter.innerHTML = result;
		// 목표수치에 도달하면 정지
		if (now < 1) {
			clearInterval(handle);
		}

		// 증가되는 값이 계속하여 작아짐
		const step = now / 10;

		// 값을 적용시키면서 다음 차례에 영향을 끼침
		now -= step;
	}, 50);
}
const slidelength = $(".banner img").length; //banner list의 길이
let currentIndex = 0
const moveSlide = function(num){
	$(".slide_banner").attr("style",`transform:translateX(${-num * 1400}px)`)
	currentIndex = num;
}
$(".prev").on("click",function(num){
	if(currentIndex !== 0){
		moveSlide(currentIndex-1)
	}
});
$(".next").on("click",function(num){
	if(currentIndex !== slidelength-1){
		moveSlide(currentIndex+1)
	}
});
setInterval(()=>{
       if(currentIndex !== slidelength -1){
	       moveSlide(currentIndex +1);
       } else {
           moveSlide(0);
       }
}, 2500)

window.onload = () => {
	// 카운트를 적용시킬 요소
	const counters = document.querySelectorAll(".count");
	// 목표 수치
	const max = [2123,24215,4512,78543];
	/*const max = [${donation_people}, ${donation_save_money}, ${funding_people}, ${funding_save_money}]*/
	for(i=0;i<4;i++){
		let $counter = counters[i];
		let $max = max[i];
		setTimeout(() => counter($counter, $max), 1318);
	}
}