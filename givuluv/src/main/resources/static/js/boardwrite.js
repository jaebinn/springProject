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
//캘린더
var days = 1;
$(function() {
	$('#datetime').daterangepicker({
		singleDatePicker: true,
		showDropdowns: true,
		minYear: parseInt(moment().format('YYYY')),
		maxYear: parseInt(moment().format('YYYY')) + 5
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
				$('#dEnddate').val(start.format('YYYY-MM-DD'))
			}

		});

});

//섬머노트
$(document).ready(function() {
	//여기 아래 부분
	$('#summernote').summernote({
		height: 300,                 // 에디터 높이
		minHeight: null,             // 최소 높이
		maxHeight: null,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
		lang: "ko-KR",               // 한글 설정
		placeholder: '최대 2048자까지 쓸 수 있습니다'   //placeholder 설정
	});
});
$('#summernote').summernote({
	placeholder: '글을 작성해주세요.',
	minHeight: 600,             // 최소 높이
	maxHeight: 600,             // 최대 높이
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

/*보류 파일 삭제
function deleteSummernoteImageFile(imageName) {
	console.log("사진 삭제 : "+ imageName)
	let idx = files.indexOf(imageName);
	if(idx > -1){
		files.splice(idx, 1)
	}
	console.log(files);
	
}*/

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
	if(content === ''){
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
	if($("#realtargetAmount").val()>2000000000){
		alert("목표 금액은 2,000,000,000 이하로 설정해주세요.")
		$("#targetAmount").focus();
		return false;
	}
	
	// 날짜 유효성 검사
	if($("#dEnddate").val()===''){
		alert("마감일을 선택해주세요!")
		return false;
	}

	//파일 유효성 검사
	console.log($("#thumbnail")[0])
	console.log($("#thumbnail")[0].files.length)
	if($("#thumbnail")[0].files.length==0){
		alert("파일을 첨부해주세요!")
		return false;
	}

	// 내용 input:hidden에 삽입
	$("#dContent").val(content);
	console.log("content : " + content);
	console.log("dContent 삽입 : " + $("#dContent").val())

	//파일 이름 input:hidden에 삽입
	$(".filenames").val(filenames);
	console.log("filename 삽입 : " + $(".filenames").val());

	console.log("submit");
	donationForm.submit();
}