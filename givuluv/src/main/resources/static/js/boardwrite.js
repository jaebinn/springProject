// 있으면 true
var pattern_num = /[0-9]/;	// 숫자 
var pattern_eng = /[a-zA-Z]/;	// 문자 
var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

var filenames = [];

//목표 금액 유효성 검사 + ','찍어주기
$("#targetAmount").keyup(function() {
	let targetAmount = $("#targetAmount").val().replace(/[^\d]/g, '');
	targetAmount = targetAmount.replace(/,/g, '');

	//if문 안이 유효성 검사
	if (!(pattern_eng.test(targetAmount)) && !(pattern_spc.test(targetAmount)) && !(pattern_kor.test(targetAmount))) {
		// 진짜 realAmount를 넘겨줄 요소에 삽입
		$("#realtargetAmount").val(targetAmount);
		console.log($("#realtargetAmount").val())

		// ',' 찍어서 보여주기
		let result = targetAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		$("#targetAmount").val(result);
		console.log(result);
		return true;
	} else {
		alert("숫자만 입력 가능합니다.");
		$("#targetAmount").val('');
		return false;
	}
})
//물건 가격 유효성 검사 + ','찍어주기
function productcost(e) {
	console.log("productcost 함수 호출")
	const productcosttarget = $(e.target);
	console.log(productcosttarget)
	//숫자를 제외하고 ,을 비롯한 모든 문자 없애기
	let productcost = productcosttarget.val().replace(/[^\d]/g, '');

	//if문 안이 유효성 검사
	if (!(pattern_eng.test(productcost)) && !(pattern_spc.test(productcost)) && !(pattern_kor.test(productcost))) {
		// ',' 찍어서 보여주기
		let result = productcost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		productcosttarget.val(result);
		console.log(result);
		return true;
	} else {
		alert("숫자만 입력 가능합니다.");
		productcosttarget.val('');
		return false;
	}
}

//물건 상품 유효성 검사 + ','찍어주기
function productamount(e) {
	const productamounttarget = $(e.target);
	console.log(productamounttarget)
	//숫자를 제외하고 ,을 비롯한 모든 문자 없애기
	let productamount = productamounttarget.val().replace(/[^\d]/g, '');

	//if문 안이 유효성 검사
	if (!(pattern_eng.test(productamount)) && !(pattern_spc.test(productamount)) && !(pattern_kor.test(productamount))) {
		// ',' 찍어서 보여주기
		let result = productamount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
		productamounttarget.val(result);
		console.log(result);
		return true;
	} else {
		alert("숫자만 입력 가능합니다.");
		productamounttarget.val('');
		return false;
	}
}
//캘린더
var days = 1;
$(function() {
	$('#datetime').daterangepicker({
		singleDatePicker: true,
		showDropdowns: true,
		minYear: parseInt(moment().format('YYYY')),
		maxYear: parseInt(moment().format('YYYY')) + 5,
		minDate: moment(),
		maxDate: moment().add(5, 'years')
	},
		function(start, end, label) {
			days = moment().diff(start, 'days');
			console.log(days);
			if (days > 0) {
				alert(moment().format('MM/DD/YYYY') + "이후로 설정해주세요!");
				$('#datetime').val(moment().format('MM/DD/YYYY'));
				$('#datetime').focus();
			}
			else {
				//히든 input에 양식 맞춰서 시간 넣기
				console.log(start.format('YYYY-MM-DD'))
				$('#enddate').val(start.format('YYYY-MM-DD'))
			}

		});

});

//섬머노트
$(document).ready(function() {
	// .productcost에 대한 keyup 이벤트 핸들러를 바인딩
	$(document).on('keyup', '.productcost', productcost);
	$(document).on('keyup', '.productamount', productamount);
});

$('#summernote').summernote({
	placeholder: '글을 작성해주세요.',
	minHeight: 600,             // 최소 높이
	maxHeight: 600,             // 최대 높이
	width: 950,					//고정 width
	height: 600,              // 기초 높이
	tabsize: 2,
	focus: true,
	lang: "ko-KR",
	toolbar: [              // 툴바 설정
		['fontname', ['fontname']],
		['fontsize', ['fontsize']],
		['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
		['font', ['bold', 'underline', 'clear']],
		['color', ['forecolor', 'color']],
		['table', ['table']],
		['para', ['ul', 'ol', 'paragraph']],
		['height', ['height']],
		['insert', ['link', 'picture']],
		['view', ['help']]
	],
	fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
	fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
	callbacks: {   //이미지를 첨부
		onImageUpload: function(files) {
			uploadSummernoteImageFile(files[0], this);
		},
		onPaste: function(e) {
			var clipboardData = e.originalEvent.clipboardData;
			if (clipboardData && clipboardData.items && clipboardData.items.length) {
				var item = clipboardData.items[0];
				if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
					e.preventDefault();
				}
			}
		}
	}
});
function uploadSummernoteImageFile(file, editor) {
	data = new FormData();
	data.append("file", file);

	console.log('사진 넣기');

	$.ajax({
		data: data,
		type: "POST",
		url: "/file/uploadSummernoteImageFile",
		contentType: false,
		processData: false,
		success: function(data) {
			//항상 업로드된 파일의 url이 있어야 한다.
			$(editor).summernote('insertImage', data.url);

			let filename = data.url.substring(17);
			filenames.push(filename);
			console.log(filenames);
		}
	});
}


//파일
function upload() {
	$("#thumbnail").click();
}
//$(선택자).change(함수) : 해당 선택자의 요소에 변화가 일어난다면 넘겨주는 함수 호출
$("[type=file]").change(function(e) {
	//e : 파일이 업로드된 상황 자체를 담고있는 이벤트 객체
	//e.target : 파일이 업로드가 된 input[type=file] 객체(태그객체)
	const fileTag = e.target;
	console.log(fileTag);
	//e.target.files : 파일태그에 업로드가 된 파일들의 배열
	const file = fileTag.files[0];
	console.log(file);

	if (file == undefined) {
		//업로드 창을 띄웠다가 취소한 경우(파일이 업로드 되었다가 없어진 경우)
		cancelFile();
	}
	else {
		//파일을 업로드를 한 경우(없었다가 업로드, 있었는데 다른 파일로 업로드)
		//#file0name 찾아서 내부 텍스트 변경(파일의 이름으로)
		$("img.thumbnailImg").remove();
		$("#thumbnailname").text(file.name);
		//업로드 된 파일의 확장자명         
		let ext = file.name.split(".").pop();
		if (ext == "jpeg" || ext == "JPEG" || ext == "png" || ext == "PNG" || ext == "jpg" || ext == "JPG" || ext == "webp" || ext == "WEBP" || ext == "gif" || ext == "GIF") {
			const reader = new FileReader();
			reader.onload = function(ie) {
				const img = document.createElement("img");
				img.setAttribute("src", ie.target.result);
				img.setAttribute("class", "thumbnailImg");
				$("#file_box").prepend(img);
			}
			reader.readAsDataURL(file);
		}
		else {
			cancelFile();
			alert("사진 파일만 넣어주세요!");
		}
	}
})

function cancelFile() {
	$("#thumbnailname").html("선택된 썸네일 없음")
	$("img.thumbnailImg").remove();
	$("[type=file]").val('');
}

// 상품 등록
let i = 0; //상품 추가 횟수
function addProduct(num) {
	const product_box = $(".product_box");
	const product = $("#product" + num);
	console.log(product_box);
	console.log(product);

	const cloneElement = product.clone();
	console.log(cloneElement)
	i++;
	cloneElement.attr("id", "product" + i);
	cloneElement.children("p").text(i + 1);

	cloneElement.find("#product" + (i - 1) + "name").val('');
	cloneElement.find("#product" + (i - 1) + "name").attr("name", "productList[" + i + "].productname");
	cloneElement.find("#product" + (i - 1) + "name").attr("id", "product" + i + "name");
	cloneElement.find("#product" + (i - 1) + "cost").val('');
	cloneElement.find("#product" + (i - 1) + "cost").attr("name", "productList[" + i + "].cost");
	cloneElement.find("#product" + (i - 1) + "cost").attr("id", "product" + i + "cost");
	cloneElement.find("#product" + (i - 1) + "amount").val('');
	cloneElement.find("#product" + (i - 1) + "amount").attr("name", "productList[" + i + "].pAmount");
	cloneElement.find("#product" + (i - 1) + "amount").attr("id", "product" + i + "amount");

	cloneElement.find("#removeProduct" + (i - 1)).attr("href", "javascript:removeProduct(" + i + ")");
	cloneElement.find("#removeProduct" + (i - 1)).attr("id", "removeProduct" + i);
	cloneElement.find("#addProduct" + (i - 1)).attr("href", "javascript:addProduct(" + i + ")");
	cloneElement.find("#addProduct" + (i - 1)).attr("id", "addProduct" + i);

	console.log(cloneElement);
	//jQuery객체.appendTo("부모선택자") : 해당 선택자의 자식으로 jQuery 객체 추가
	cloneElement.appendTo(".product_box");
	$("#addProduct" + (i - 1)).remove();

}

function removeProduct(num) {
	console.log(i);
	// 하나 남았을 때 누르면
	if (i == 0) {
		$("#product0name").val('');
		$("#product0cost").val('');
		$("#product0amount").val('');
		return;
	}

	//마지막 누르면
	if (i == num) {
		console.log("마지막거")
		const addProduct = $("#addProduct" + i).clone();
		addProduct.appendTo("#product" + (num - 1) + " .product_btn_box");
		$("#addProduct" + i).attr("href", "javascript:addProduct(" + (i - 1) + ")");
		$("#addProduct" + i).attr("id", "addProduct" + (i - 1))

		$("#product" + num).remove();
		i--;
		return;
	}

	// 해당 물건 삭제
	$("#product" + num).remove();

	//지워진 다음 행 부터 숫자 바꿔주기
	for (let j = num + 1; j <= i; j++) {
		const el = $("#product" + j);
		el.attr("id", "product" + (j - 1));
		el.find("p").text(j);

		el.find("#product" + j + "name").attr("name", "productList[" + (j - 1) + "].productname");
		el.find("#product" + j + "name").attr("id", "product" + (j - 1) + "name");
		el.find("#product" + j + "cost").attr("name", "productList[" + (j - 1) + "].cost");
		el.find("#product" + j + "cost").attr("id", "product" + (j - 1) + "cost");
		el.find("#product" + j + "amount").attr("name", "productList[" + (j - 1) + "].pAmount");
		el.find("#product" + j + "amount").attr("id", "product" + (j - 1) + "amount");

		el.find("#removeProduct" + j).attr("href", "javascript:removeProduct(" + (j - 1) + ")");
		el.find("#removeProduct" + j).attr("id", "removeProduct" + (j - 1));
		el.find("#addProduct" + j).attr("href", "javascript:addProduct(" + (i - 1) + ")");
		el.find("#addProduct" + j).attr("id", "addProduct" + (i - 1));
	}
	i--;
}

//donation/wirte submit 유효성 검사
function donationsubmit() {
	const donationForm = document.donationForm;

	// 제목 유효성 검사
	let title = $("#dTitle").val();
	console.log(title);
	if (title.length < 1 || title.length === '') {
		alert("제목을 작성해주세요!");
		$("#dTitle").focus();
		return false;
	}

	// 내용 유효성 검사
	let content = $(".note-editable").html();
	if (content === '') {
		alert("내용을 입력해주세요.")
		$(".note-editable").focus();
		return false;
	}

	// 목표 금액 유효성 검사
	// 1. 작성 됐는지
	let targetAmount = $("#targetAmount").val().replace(/[^\d]/g, '');
	targetAmount = targetAmount.replace(/,/g, '');
	if (pattern_num.test(targetAmount)) {
		console.log(targetAmount);
	}
	else {
		alert("목표 금액을 작성해주세요!");
		$("#targetAmount").focus();
		return false;
	}
	// 2. 작성된 targetamount에 숫자 외 문자 있는지 다시 검사 후, realAmount에 다시 넣음.
	if (!(pattern_eng.test(targetAmount)) && !(pattern_spc.test(targetAmount)) && !(pattern_kor.test(targetAmount))) {
		// 진짜 realAmount를 넘겨줄 요소에 삽입
		$("#realtargetAmount").val(targetAmount);
		console.log($("#realtargetAmount").val())
	} else {
		alert("목표 금액에는 숫자만 입력 가능합니다.");
		$("#targetAmount").val('');
		return false;
	}
	// 3. 작성된 Amount가 int 이상 범위인지
	if ($("#realtargetAmount").val() > 2000000000) {
		alert("목표 금액은 2,000,000,000 이하로 설정해주세요.")
		$("#targetAmount").focus();
		return false;
	}

	// 날짜 유효성 검사
	if ($("#enddate").val() === '') {
		alert("마감일을 선택해주세요!")
		return false;
	}

	//파일 유효성 검사
	console.log($("#thumbnail")[0])
	console.log($("#thumbnail")[0].files.length)
	if ($("#thumbnail")[0].files.length == 0) {
		alert("파일을 첨부해주세요!")
		return false;
	}

	// 내용 input:hidden에 삽입
	$("#dContent").val(content);
	console.log("content : " + content);
	console.log("dContent 삽입 : " + $("#dContent").val())

	//파일 이름 input:hidden에 삽입
	$("#filenames").val(filenames);
	console.log("filename 삽입 : " + $("#filenames").val());

	console.log("submit");
	donationForm.submit();
}



//store/write submit 유효성 검사
function storesubmit() {
	const storeForm = document.storeForm;

	// 제목 유효성 검사
	let title = $("#sTitle").val();
	console.log(title);
	if (title.length < 1 || title.length === '') {
		alert("제목을 작성해주세요!");
		$("#sTitle").focus();
		return false;
	}

	// 내용 유효성 검사
	let content = $(".note-editable").html();
	if (content === '') {
		alert("내용을 입력해주세요.")
		$(".note-editable").focus();
		return false;
	}

	//파일 유효성 검사
	console.log($("#thumbnail")[0])
	console.log($("#thumbnail")[0].files.length)
	if ($("#thumbnail")[0].files.length == 0) {
		alert("파일을 첨부해주세요!")
		return false;
	}

	//상품 유효성 검사
	let product = [];
	$(".product").each(function() {
		product.push($(this))
	})
	for (j = 0; j < product.length; j++) {
		let productname = product[j].find(".productname");
		let productcost = product[j].find(".productcost")
		let productamount = product[j].find(".productamount")

		if (productname.val()== '') {
			alert("상품명을 작성해주세요.")
			productname.focus();
			return;
		}
		if (productcost.val().replace(/[^\d]/g, '') == '') {
			alert("상품의 가격을 작성해주세요.")
			productcost.focus();
			return;
		}
		if (productamount.val().replace(/[^\d]/g, '') == '') {
			alert("상품 수량을 작성해주세요.")
			productamount.focus();
			return;
		}
	}
	//가격, 수량에 있는 문자 지우고 숫자만 남겨서 넣기
	for (j = 0; j < product.length; j++) {
		let productcosttarget = product[j].find(".productcost")
		let productamounttarget = product[j].find(".productamount")

		let productcost = productcosttarget.val().replace(/[^\d]/g, '');
		console.log(productcost);
		let productamount = productamounttarget.val().replace(/[^\d]/g, '');
		console.log(productamount);
		productcosttarget.val(productcost)
		productamounttarget.val(productamount)
	}



	// 내용 input:hidden에 삽입
	$("#sContent").val(content);
	console.log("content : " + content);
	console.log("sContent 삽입 : " + $("#sContent").val())

	//파일 이름 input:hidden에 삽입
	$("#filenames").val(filenames);
	console.log("filename 삽입 : " + $("#filenames").val());

	console.log("submit");
	storeForm.submit();
}

//funding/write submit 유효성 검사
function fundingsubmit() {
	const fundingForm = document.fundingForm;

	// 제목 유효성 검사
	let title = $("#fTitle").val();
	console.log(title);
	if (title.length < 1 || title.length === '') {
		alert("제목을 작성해주세요!");
		$("#fTitle").focus();
		return false;
	}

	// 내용 유효성 검사
	let content = $(".note-editable").html();
	if (content === '') {
		alert("내용을 입력해주세요.")
		$(".note-editable").focus();
		return false;
	}
	// 목표 금액 유효성 검사
	// 1. 작성 됐는지
	let targetAmount = $("#targetAmount").val().replace(/[^\d]/g, '');
	targetAmount = targetAmount.replace(/,/g, '');
	if (pattern_num.test(targetAmount)) {
		console.log(targetAmount);
	}
	else {
		alert("목표 금액을 작성해주세요!");
		$("#targetAmount").focus();
		return false;
	}
	// 2. 작성된 targetamount에 숫자 외 문자 있는지 다시 검사 후, realAmount에 다시 넣음.
	if (!(pattern_eng.test(targetAmount)) && !(pattern_spc.test(targetAmount)) && !(pattern_kor.test(targetAmount))) {
		// 진짜 realAmount를 넘겨줄 요소에 삽입
		$("#realtargetAmount").val(targetAmount);
		console.log($("#realtargetAmount").val())
	} else {
		alert("목표 금액에는 숫자만 입력 가능합니다.");
		$("#targetAmount").val('');
		return false;
	}
	// 3. 작성된 Amount가 int 이상 범위인지
	if ($("#realtargetAmount").val() > 2000000000) {
		alert("목표 금액은 2,000,000,000 이하로 설정해주세요.")
		$("#targetAmount").focus();
		return false;
	}

	// 날짜 유효성 검사
	if ($("#enddate").val() === '') {
		alert("마감일을 선택해주세요!")
		return false;
	}
	//파일 유효성 검사
	console.log($("#thumbnail")[0])
	console.log($("#thumbnail")[0].files.length)
	if ($("#thumbnail")[0].files.length == 0) {
		alert("파일을 첨부해주세요!")
		return false;
	}

	//상품 유효성 검사
	let product = [];
	$(".product").each(function() {
		product.push($(this))
	})
	for (j = 0; j < product.length; j++) {
		let productname = product[j].find(".productname");
		let productcost = product[j].find(".productcost")
		let productamount = product[j].find(".productamount")

		if (productname.val()== '') {
			alert("상품명을 작성해주세요.")
			productname.focus();
			return;
		}
		if (productcost.val().replace(/[^\d]/g, '') == '') {
			alert("상품의 가격을 작성해주세요.")
			productcost.focus();
			return;
		}
		if (productamount.val().replace(/[^\d]/g, '') == '') {
			alert("상품 수량을 작성해주세요.")
			productamount.focus();
			return;
		}
	}
	//가격, 수량에 있는 문자 지우고 숫자만 남겨서 넣기
	for (j = 0; j < product.length; j++) {
		let productcosttarget = product[j].find(".productcost")
		let productamounttarget = product[j].find(".productamount")

		let productcost = productcosttarget.val().replace(/[^\d]/g, '');
		console.log(productcost);
		let productamount = productamounttarget.val().replace(/[^\d]/g, '');
		console.log(productamount);
		productcosttarget.val(productcost)
		productamounttarget.val(productamount)
	}

	// 내용 input:hidden에 삽입
	$("#fContent").val(content);
	console.log("content : " + content);
	console.log("fContent 삽입 : " + $("#fContent").val())

	//파일 이름 input:hidden에 삽입
	$("#filenames").val(filenames);
	console.log("filename 삽입 : " + $("#filenames").val());

	console.log("submit");
	fundingForm.submit();
}

//campaign/write submit 유효성 검사
function campaignsubmit() {
	const campaignForm = document.campaignForm;

	// 내용 유효성 검사
	let content = $(".note-editable").html();
	if (content === '') {
		alert("내용을 입력해주세요.")
		$(".note-editable").focus();
		return false;
	}

	// 내용 input:hidden에 삽입
	$("#cContent").val(content);
	console.log("content : " + content);
	console.log("cContent 삽입 : " + $("#cContent").val())

	//파일 이름 input:hidden에 삽입
	$("#filenames").val(filenames);
	console.log("filename 삽입 : " + $("#filenames").val());

	console.log("submit");
	campaignForm.submit();
}