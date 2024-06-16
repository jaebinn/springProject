let resultid = document.querySelector("#checkid");

function sendit() {
	const myInfoForm = document.myInfoForm;
	const mySellerProfileForm = document.mySellerProfileForm;

	/* 비밀번호 검사 */
	const sellerpw = myInfoForm.sellerpw;
	if (sellerpw.value == "") {
		alert("비밀번호를 입력하세요!");
		sellerpw.focus();
		return;
	}
	/* 비밀번호 확인 검사 */
	const sellerpw_re = myInfoForm.sellerpw_re;
	if (sellerpw_re.value == "") {
		alert("비밀번호 확인을 입력하세요!");
		sellerpw_re.focus();
		return;
	}
	/* 비밀번호 확인 체크 */
	if (sellerpw.value !== sellerpw_re.value) {
		alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		sellerpw.value = "";
		sellerpw_re.value = "";
		sellerpw.focus();
		return;
	}

	/* 이름 검사 */
	const sellername = myInfoForm.sellername;
	if (sellername.value == "") {
		alert("이름을 입력하세요!");
		sellername.focus();
		return;
	}
	const exp_name = /^[가-힣]+$/;
	if (!exp_name.test(sellername.value)) {
		alert("이름에는 한글만 입력하세요!");
		sellername.focus();
		return false;
	}

	/* 이메일 검사 */
	const email = myInfoForm.email;
	if (email.value == "") {
		alert("이메일을 입력하세요!");
		email.focus();
		return;
	}
	const exp_email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	if (!exp_email.test(email.value)) {
		alert("이메일을 형식에 맞게 입력하세요! ex) aaaa@naver.com");
		email.focus();
		return false;
	}

	/* 전화번호 검사 */
	const sellerphone = document.getElementById('sellerphone');
	const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;

	if (sellerphone.value == "") {
		alert("전화번호를 입력하세요!");
		sellerphone.focus();
		return;
	}
	if (!phoneRegex.test(sellerphone.value)) {
		alert("전화번호를 형식에 맞게 입력하세요 ex) 010-1234-5678");
		sellerphone.focus();
		return;
	}

	myInfoForm.submit();
	mySellerProfileForm.submit();
	alert("내정보가 수정되었습니다!");
}