let resultid = document.querySelector("#id_check");
let resultNick = document.querySelector("#nickname_check");
function sendit(){
    const joinForm = document.joinForm;

    const userid = joinForm.userid;
    if(userid.value == ""){
        alert("아이디를 입력하세요!");
        userid.focus();
        return;
    }
    
    if(resultid.innerHTML == ""){
        alert("아이디 중복검사를 진행해주세요!");
        userid.focus();
        return;
    }
    if(resultid.innerHTML == "중복된 아이디가 있습니다!"){
        alert("중복체크 통과 후 가입이 가능합니다!");
        userid.focus();
        return;
    }
    
    const userpw = joinForm.userpw;
    if(userpw.value==""){
        alert("비밀번호를 입력하세요!");
        userpw.focus();
        return;
    }
    
    const userpw_re = joinForm.userpw_re;
    if(userpw_re.value==""){
        alert("비밀번호 확인을 입력하세요!");
        userpw_re.focus();
        return;
    }
    
    // 비밀번호 확인 체크
    if(userpw.value !== userpw_re.value){
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        userpw.value="";
        userpw_re.value="";
        userpw.focus();
        return;
    }
    const username = joinForm.username;
    const exp_name = /^[가-힣]+$/;
    if(!exp_name.test(username.value)){
    	alert("이름에는 한글만 입력하세요!");
    	username.focus();
    	return false;
    }

    const gender = joinForm.gender;
    if(!gender[0].checked && !gender[1].checked){
        alert("성별을 선택하세요!");
        return;
    }
    
    const birth = document.getElementById('birth');
    if(birth.value==""){
        alert("생년월일을 입력하세요!");
        return;
    }

    const email = document.getElementById('email');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if(!emailRegex.test(email.value)) {
        alert("이메일을 올바른 형식으로 입력하세요!");
        email.value="";
        email.focus();
        return;
    }
    const userphone = document.getElementById('userphone');
    const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;
    
    if(!phoneRegex.test(userphone.value)) {
        alert("전화번호를 010-1234-5678 형식으로 입력하세요!");
        userphone.value="";
        userphone.focus();
        return;
    }
    
    const nickname = document.joinForm.nickname;
    if(nickname.value == ""){
        alert("닉네임을 입력하세요!");
        nickname.focus();
        return;
    }

    if(resultNick.innerHTML == ""){
        alert("닉네임 중복검사를 진행해주세요!");
        nickname.focus();
        return;
    }
    if(resultNick.innerHTML == "중복된 닉네임이 있습니다!"){
        alert("중복체크 통과 후 가입이 가능합니다!");
        nickname.focus();
        return;
    }
    
    const zipcode = joinForm.zipcode;
    if(zipcode.value == ""){
        alert("우편번호를 입력하세요!");
        findAddr();
        return;
    }
    
    const addrdetail = joinForm.addrdetail;
    if(addrdetail.value == ""){
        alert("상세주소를 입력해 주세요!");
        addrdetail.focus();
        return;
    }
    joinForm.submit();
}


function checkId(){
	const xhr = new XMLHttpRequest();
	const userid = document.joinForm.userid;
	if(userid.value == ""){
		alert("아이디를 입력하세요!");
		userid.focus();
		return;
	}
	if(userid.value.length<5 || userid.value.length>12){
    	alert("아이디는 5자 이상 12자 이하로 입력하세요!");
    	userid.focus();
    	return;
    }
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				let txt = xhr.responseText.trim();
				console.log(resultid);
				if(txt == "O"){
					resultid.classList.add("visible");
					resultid.style.color = "rgb(79,148,111)";
					resultid.innerHTML = "사용할 수 있는 아이디입니다!";
					document.joinForm.userpw.focus();
				}
				else{
					resultid.classList.add("visible");
					resultid.style.color = "red";
					resultid.innerHTML = "중복된 아이디가 있습니다!";
					userid.value = "";
					userid.focus();
				}
			}
		}
	}
	
	xhr.open("GET","/user/checkId?userid="+userid.value);
	xhr.send();
}

function checkNickname(){
	const xhr = new XMLHttpRequest();
	const nickname = document.joinForm.nickname;
	if(nickname.value == ""){
		alert("닉네임을 입력하세요!");
		nickname.focus();
		return;
	}
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				let txt = xhr.responseText.trim();
				console.log(resultNick);
				if(txt == "O"){
					alert("사용할 수 있는 닉네임입니다!");
					resultNick.style.color = "rgb(79,148,111)";
					resultNick.innerHTML = "사용할 수 있는 닉네임입니다!";
				}else{
					alert("중복된 닉네임이 있습니다!");
					resultNick.style.color = "red";
					resultNick.innerHTML = "중복된 아이디가 있습니다!";
					nickname.value = "";
					nickname.focus();
				}
			}
		}
	}
	
	xhr.open("GET","/user/checkNickname?nickname="+nickname.value);
	xhr.send();
}

function pwcheck() {
    const userpw = document.joinForm.userpw;
	const userpw_re = document.joinForm.userpw_re;
	const pw_checkText = document.querySelector("#pw_checkText");
	const reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@-]).{4,}$/;
	const msg = [
	    "영어 대문자, 소문자, 숫자, 특수문자(~,?,!,@,-)를 모두 하나 이상 포함해야 해요",
	    "최소 8자 이상의 비밀번호가 보안에 안전해요",
	    "같은 문자가 연속해서 사용되지 않았어요",
	    "사용할 수 없는 문자가 포함되지 않았어요"
	];
	const pw_check = [];
	if(userpw.value == ""){
		alert("비밀번호를 먼저 입력해주세요.");
		userpw_re.value = "";
		userpw.focus();
		return;
	}
	if (!reg.test(userpw.value)) {
	    pw_check.push(msg[0]);
	}
	if (userpw.value.length < 8) {
	    pw_check.push(msg[1]);
	}
	if (/(\w)\1\1/.test(userpw.value)) {
	    pw_check.push(msg[2]);
	}
	if (/[^\w~?!@-]/.test(userpw.value)) {
	    pw_check.push(msg[3]);
	}
	if (userpw.value !== userpw_re.value) {
	    pw_check.push("비밀번호 확인이 일치하지 않아요!");
	}
	if (pw_check.length > 0) {
	    alert(pw_check.join("\n"));
	    userpw.value = "";
	    userpw_re.value = "";
	    pw_checkText.innerHTML = "";
	    userpw.focus();	    
	} else {
		console.log("확인")	
		pw_checkText.innerHTML = "비밀번호가 확인되었습니다.";
		pw_checkText.style.color = "green";
	}
}



document.getElementById("userpw").addEventListener("focus", function() {
    const pwHint = document.createElement("p");
    pwHint.textContent = "숫자, 영어 대문자, 특수문자(~,?,!,@,-)를 모두 하나 이상, 8자 이상의 비밀번호를 입력하세요.";
    pwHint.style.fontSize = "0.7em";
    pwHint.style.color = "gray";
    pwHint.style.marginTop = "0";
    this.parentNode.insertBefore(pwHint, this.nextSibling);
    
    this.addEventListener("blur", function(){
        pwHint.style.display = "none"; 
    });
});


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
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("addretc").value = extraAddr;
            
            } else {
                document.getElementById("addretc").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("addr").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addrdetail").focus();
        }
    }).open();
}