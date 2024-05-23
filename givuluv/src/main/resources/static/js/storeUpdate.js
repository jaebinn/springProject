let resultsName = document.querySelector("#sName_check");

function sendit() {
	const storeUpdateForm = document.storeUpdateForm;

	/* 스토어 이름 검사 */
	const s_name = storeUpdateForm.s_name;
	if (s_name.value == "") {
		alert("스토어 이름을 입력하세요!");
		s_name.focus();
		return;
	}
	if (resultsName.innerHTML == "") {
		alert("스토어 이름 중복검사를 진행해주세요!");
		s_name.focus();
		return;
	}
	if (resultsName.innerHTML == "중복된 스토어 이름이 있습니다!") {
		alert("중복체크 통과 후 가입이 가능합니다!");
		s_name.focus();
		return;
	}
	
	/* 스토어 전화번호 검사 */
	const s_phone = document.getElementById('s_phone');
	const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;

	if (s_phone.value == "") {
		alert("스토어 전화번호를 입력하세요!");
		s_phone.focus();
		return;
	}
	if (!phoneRegex.test(s_phone.value)) {
		alert("전화번호를 010-1234-5678 형식으로 입력하세요!");
		s_phone.focus();
		return;
	}

	/* 카테고리 검사 */
	
	/* 우편번호, 상세주소, 검사 */
	const s_zipcode = storeUpdateForm.s_zipcode;
	if (s_zipcode.value == "") {
		alert("우편번호를 입력하세요!");
		findAddr();
		return;
	}
	const s_addrdetail = storeUpdateForm.s_addrdetail;
	if (s_addrdetail.value == "") {
		alert("상세주소를 입력해 주세요!");
		s_addrdetail.focus();
		return;
	}
	
	/* 오픈날짜 검사 */
	const s_opendate = storeUpdateForm.s_opendate;
	if (s_opendate.value == "") {
		alert("오픈 날짜를 입력하세요!");
		s_opendate.focus();
		return;
	}
	
	/* 대표자 이름 검사 */
	const s_leader = storeUpdateForm.s_leader;
	const exp_name = /^[ㄱ-ㅎ가-힣a-zA-Z\s]+$/;
	
	if (s_leader.value == "") {
		alert("대표자 이름 입력하세요!");
		s_leader.focus();
		return;
	}
	if (!exp_name.test(s_leader.value)) {
		alert("대표자 이름에는 한글, 영어만 입력하세요!");
		s_leader.focus();
		return false;
	}

	orgjoinForm.submit();
}


/* 스토어 이름 중복검사 */
function check_sName() {
	const xhr = new XMLHttpRequest();
	const s_name = document.storeUpdateForm.s_name;
	
	if (s_name.value == "") {
		alert("스토어 이름을 입력하세요!");
		s_name.focus();
		return;
	}
	if (s_name.value.length < 5 || s_name.value.length > 12) {
		alert("스토어 이름은 5자 이상 12자 이하로 입력하세요!");
		s_name.focus();
		return;
	}

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				let txt = xhr.responseText.trim();
				console.log(resultid);
				if (txt == "O") {
					resultsName.classList.add("visible");
					resultsName.style.color = "rgb(79,148,111)";
					resultsName.innerHTML = "사용할 수 있는 아이디입니다!";
					document.storeUpdateForm.s_phone.focus();
				}
				else {
					resultsName.classList.add("visible");
					resultsName.style.color = "red";
					resultsName.innerHTML = "중복된 아이디가 있습니다!";
					s_name.value = "";
					s_name.focus();
				}
			}
		}
	}

	xhr.open("GET", "/org/checkId?orgid=" + orgid.value);
	xhr.send();
}

function findAddr() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("s_addretc").value = extraAddr;

			} else {
				document.getElementById("s_addretc").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('s_zipcode').value = data.zonecode;
			document.getElementById("s_addr").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("s_addrdetail").focus();
		}
	}).open();
}