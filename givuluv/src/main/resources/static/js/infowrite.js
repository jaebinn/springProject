// 있으면 true
var pattern_num = /[0-9]/;	// 숫자 
var pattern_eng = /[a-zA-Z]/;	// 문자 
var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

// textarea 변환
const replaceString = {
	toDiv: function(data) {
		//DB에 있는 값을 div에 보여주기
		let result = "";
		result = data.split("//givuluv<enter>givuluv//").join('<br>');
		result = result.split("//givuluv<space>givuluv//").join('&nbsp;');

		return result;
	},
	toDB: function(data) {
		//textarea에 있는 value를 DB에 저장하기

		let result = "";
		result = data.replace(/(?:\r\n|\r|\n)/g, "//givuluv<enter>givuluv//");
		result = result.replace(/ /g, "//givuluv<space>givuluv//")
		return result;
	},
	toTextarea: function(data) {
		//div에 있는 값을 textarea에 보여주기
		let result = "";
		result = data.split("<br>").join('\r\n');
		result = result.split("&nbsp;").join('\ ');

		return result;
	}
}

//댓글 등록
function commentRegist(cBoardnum) {
	console.log("commentRegist : " + cBoardnum)
	let commentdetail = $("#commentdetail" + cBoardnum).val();
	console.log("댓글 : " + commentdetail);

	if (commentdetail.replace(blank_pattern, '') == "") {
		console.log("공백")
		$("#commentdetail" + cBoardnum).val('');
		$("#commentdetail" + cBoardnum).focus();
		alert('작성된 글이 없습니다!');
		return false;
	}

	commentdetail = replaceString.toDB(commentdetail);
	console.log("변환 댓글 : " + commentdetail);

}



//파일
function thumbnailupload() {
	$("#thumbnail").click();
}
//$(선택자).change(함수) : 해당 선택자의 요소에 변화가 일어난다면 넘겨주는 함수 호출
$("#thumbnail[type=file]").change(function(e) {
	//e : 파일이 업로드된 상황 자체를 담고있는 이벤트 객체
	//e.target : 파일이 업로드가 된 input[type=file] 객체(태그객체)
	const fileTag = e.target;
	console.log(fileTag);
	//e.target.files : 파일태그에 업로드가 된 파일들의 배열
	const file = fileTag.files[0];
	console.log(file);

	if (file == undefined) {
		//업로드 창을 띄웠다가 취소한 경우(파일이 업로드 되었다가 없어진 경우)
		thumbnailcancelFile();
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
				$(".thumbnail_box").prepend(img);
			}
			reader.readAsDataURL(file);
		}
		else {
			thumbnailcancelFile();
			alert("사진 파일만 넣어주세요!");
		}
	}
})

function thumbnailcancelFile() {
	$("#thumbnailname").html("선택된 썸네일 없음")
	$("img.thumbnailImg").remove();
	$("#thumbnail[type=file]").val('');
}




var i=0;
function upload(num) {
	$("#file" + num).click();
	console.log(num)
}
//$(선택자).change(함수) : 해당 선택자의 요소에 변화가 일어난다면 넘겨주는 함수 호출
$(".file[type=file]").change(function(e) {
	//e : 파일이 업로드된 상황 자체를 담고있는 이벤트 객체
	//e.target : 파일이 업로드가 된 input[type=file] 객체(태그객체)
	const fileTag = e.target;
	console.log(fileTag);
	//e.target.files : 파일태그에 업로드가 된 파일들의 배열
	const file = fileTag.files[0];
	console.log(file);

	if (file == undefined) {
		//업로드 창을 띄웠다가 취소한 경우(파일이 업로드 되었다가 없어진 경우)
		cancelFile(fileTag.id.split("e").pop());
	}
	else {
		//파일을 업로드를 한 경우(없었다가 업로드, 있었는데 다른 파일로 업로드)
		//#file0name 찾아서 내부 텍스트 변경(파일의 이름으로)
		$("#" + fileTag.id + "name").text(file.name);
		//업로드 된 파일의 확장자명			
		let ext = file.name.split(".").pop();
		if (ext == "jpeg" || ext == "png" || ext == "jpg" || ext == "webp" || ext == "gif") {
			//".  file0       _cont"
			$("." + fileTag.id + "_upload .thumbnail").remove();
			const reader = new FileReader();
			reader.onload = function(ie) {
				const img = document.createElement("img");
				img.setAttribute("src", ie.target.result);
				img.setAttribute("class", "thumbnail");
				
				console.log(img)
				console.log($("."+fileTag.id+"_upload"))
				$("."+fileTag.id+"_upload").append(img)
				
			}
			reader.readAsDataURL(file);
		}
		else {
			$("." + fileTag.id + "_upload .thumbnail").remove();
		}

		//가장 마지막 [파일 선택] 버튼을 눌렀을 때
		if (fileTag.id == "file" + i) {
			const cloneElement = $(".r" + i).clone(true);
			i++;
			cloneElement.attr("class", "r" + i);
			cloneElement.children("td").attr("class", "file" + i + "_cont");

			cloneElement.find("a").attr("href","javascript:upload(" + i + ")");
			cloneElement.find("a").attr("class", "file_upload file"+i+"_upload");
			
			cloneElement.find("input[type='file']").attr("name", "files");
			cloneElement.find("input[type='file']").attr("id", "file" + i);
			cloneElement.find("input[type='file']").val("");


			//jQuery객체.appendTo("부모선택자") : 해당 선택자의 자식으로 jQuery 객체 추가
			cloneElement.appendTo(".imgfile_box tbody")
			$('html, body').scrollTop( $(document).height() );
		}
	}
})

function cancelFile(num) {
	//파일 업로드 했다가 취소로 파일을 삭제하는 경우에는 문자열로 넘어온다.
	num = Number(num);
	//가장 마지막 [첨부 삭제] 버튼을 누른 경우
	if (num == i) { return; }
	//tr 지우기
	$(".r" + num).remove();
	//지워진 다음 행 부터 숫자 바꿔주기
	for (let j = num + 1; j <= i; j++) {
		console.log(j);
		const el = $(".imgfile_box tbody .r" + j);
		el.attr("class", "r" + (j - 1));

		el.find("td").attr("class", "file" + (j - 1) + "_cont");

		el.find("a").attr("href", "javascript:upload(" + (j - 1) + ")");
		el.find("a").attr("class", "file_upload file"+(j-1)+"_upload");

		el.find("input[type=file]").attr("name", "files");
		el.find("input[type=file]").attr("id", "file" + (j - 1));
	}
	i--;
}

// 상품 등록
//let i = 0; //상품 추가 횟수
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
	$('html, body').scrollTop($(document).height());
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

	console.log("submit");
	donationForm.submit();
}
