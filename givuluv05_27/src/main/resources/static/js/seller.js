let resultid = document.querySelector("#id_check");
/*let resultNick = document.querySelector("#nickname_check");*/
function sendit(){
    const seller_joinForm = document.seller_joinForm;

    const sellerid = seller_joinForm.sellerid;
    if(sellerid.value == ""){
        alert("아이디를 입력하세요!");
        sellerid.focus();
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
    
    const sellerpw = seller_joinForm.sellerpw;
    if(sellerpw.value==""){
        alert("비밀번호를 입력하세요!");
        userpw.focus();
        return;
    }
    
    const sellerpw_re = seller_joinForm.sellerpw_re;
    if(sellerpw_re.value==""){
        alert("비밀번호 확인을 입력하세요!");
        sellerpw_re.focus();
        return;
    }
    
    // 비밀번호 확인 체크
    if(sellerpw.value !== sellerpw_re.value){
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        sellerpw.value="";
        sellerpw_re.value="";
        sellerpw.focus();
        return;
    }
    const sellername = seller_joinForm.sellername;
    const exp_name = /^[가-힣]+$/;
    if(!exp_name.test(sellername.value)){
    	alert("이름에는 한글만 입력하세요!");
    	sellername.focus();
    	return false;
    }

    /*const gender = joinForm.gender;
    if(!gender[0].checked && !gender[1].checked){
        alert("성별을 선택하세요!");
        return;
    }*/
    
    /*const birth = document.getElementById('birth');
    if(birth.value==""){
        alert("생년월일을 입력하세요!");
        return;
    }*/

    const email = document.getElementById('email');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if(!emailRegex.test(email.value)) {
        alert("이메일을 올바른 형식으로 입력하세요!");
        email.value="";
        email.focus();
        return;
    }
    const sellerphone = document.getElementById('sellerphone');
    const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;
    /*3자리 - 4자리 - 4자리 인지 아닌지 판별*/
    
    if(!phoneRegex.test(sellerphone.value)) {
        alert("전화번호를 010-1234-5678 형식으로 입력하세요!");
        sellerphone.value="";
        sellerphone.focus();
        return;
    }
    alert("[판매자] 회원가입이 정상처리되었습니다.")
    seller_joinForm.submit();
}

function seller_checkId(){
	const xhr = new XMLHttpRequest();
	const sellerid = document.seller_joinForm.sellerid;
	if(sellerid.value == ""){
		alert("아이디를 입력하세요!");
		sellerid.focus();
		return;
	}
	if(sellerid.value.length<5 || sellerid.value.length>12){
    	alert("아이디는 5자 이상 12자 이하로 입력하세요!");
    	sellerid.focus();
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
					document.seller_joinForm.sellerpw.focus();
				}
				else{
					resultid.classList.add("visible");
					resultid.style.color = "red";
					resultid.innerHTML = "중복된 아이디가 있습니다!";
					sellerid.value = "";
					sellerid.focus();
				}
			}
		}
	}
	
	xhr.open("GET","/seller/checkId?sellerid="+sellerid.value);
	xhr.send();
}

/*function checkNickname(){
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
					resultNick.innerHTML = "중복된 닉네임이 있습니다!";
					nickname.value = "";
					nickname.focus();
				}
			}
		}
	}
	
	xhr.open("GET","/user/checkNickname?nickname="+nickname.value);
	xhr.send();
}*/

function seller_pwcheck() {
    const sellerpw = document.seller_joinForm.sellerpw;
	const sellerpw_re = document.seller_joinForm.sellerpw_re;
	const pw_checkText = document.querySelector("#pw_checkText");
	const reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@-]).{4,}$/;
	const msg = [
	    "영어 대문자, 소문자, 숫자, 특수문자(~,?,!,@,-)를 모두 하나 이상 포함해야 해요",
	    "최소 8자 이상의 비밀번호가 보안에 안전해요",
	    "같은 문자가 연속해서 사용되지 않았어요",
	    "사용할 수 없는 문자가 포함되지 않았어요"
	];
	const pw_check = [];
	if(sellerpw.value == ""){
		alert("비밀번호를 먼저 입력해주세요.");
		sellerpw_re.value = "";
		sellerpw.focus();
		return;
	}
	if (!reg.test(sellerpw.value)) {
	    pw_check.push(msg[0]);
	}
	if (sellerpw.value.length < 8) {
	    pw_check.push(msg[1]);
	}
	if (/(\w)\1\1/.test(sellerpw.value)) {
	    pw_check.push(msg[2]);
	}
	if (/[^\w~?!@-]/.test(sellerpw.value)) {
	    pw_check.push(msg[3]);
	}
	if (sellerpw.value !== sellerpw_re.value) {
	    pw_check.push("비밀번호 확인이 일치하지 않아요!");
	}
	if (pw_check.length > 0) {
	    alert(pw_check.join("\n"));
	    sellerpw.value = "";
	    sellerpw_re.value = "";
	    pw_checkText.innerHTML = "";
	    sellerpw.focus();	    
	} else {
		console.log("확인")	
		pw_checkText.innerHTML = "비밀번호가 확인되었습니다.";
		pw_checkText.style.color = "green";
	}
}

$(document).ready(function() {
    $("#sellerpw").focus(function() {
        const pwHint = $("<p>").text("숫자, 영어 대문자, 특수문자(~,?,!,@,-)를 모두 하나 이상, 8자 이상의 비밀번호를 입력하세요.")
             .css({
                   "font-size": "0.7em",
                    "color": "gray",
                    "margin-top": "0",
                    "margin-bottom": "10px"
        		});
        $(this).parent().append(pwHint);
        
        $(this).blur(function() {
            pwHint.hide();
        });
    });
});

