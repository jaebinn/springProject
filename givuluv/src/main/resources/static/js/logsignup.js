document.getElementById('nextPage').addEventListener('click', function(event) {
    
    event.preventDefault();

    
    const orgName = document.getElementById('orgname').value.trim();
    const orgUnqNum = document.getElementById('orgunqnum').value.trim();
    const managerName = document.getElementById('managername').value.trim();
    const managerId = document.getElementById('managerid').value.trim();
    const password = document.getElementById('password').value.trim();
    const passwordCheck = document.getElementById('passwordcheck').value.trim();

    
    document.getElementById('orgname_error').textContent = '';
    document.getElementById('uniquenumber_result').textContent = '';

    
    let valid = true;

    if (orgName === '' || orgName.length < 1 || orgName.length > 30) {
        document.getElementById('orgname_error').textContent = '단체명을 올바르게 입력해주세요.';
        alert('단체명을 올바르게 입력해주세요.');
        orgName.focus();
        valid = false;
    }

    if (orgUnqNum === '' || orgUnqNum.length !== 10 || isNaN(orgUnqNum)) {
        document.getElementById('uniquenumber_result').textContent = '고유번호를 올바르게 입력해주세요.';
        alert('고유번호를 올바르게 입력해주세요.');
        orgUnqNum.focus();
        valid = false;
    }

    if (managerName === '' || managerName.length < 1 || managerName.length > 15) {
        alert('담당자 이름을 올바르게 입력해주세요.');
        managerName.focus();
        valid = false;
    }

    if (managerId === '') {
        alert('담당자 ID를 입력해주세요.');
        managerId.focus();
        valid = false;
    }

    if (password === '' || password.length < 6 || password.length > 10) {
        alert('비밀번호를 올바르게 입력해주세요.');
        password.focus();
        valid = false;
    }

    if (passwordCheck === '' || password !== passwordCheck) {
        alert('비밀번호 확인이 일치하지 않습니다.');
        passwordCheck.focus();
        valid = false;
    }

    
    if (valid) {
        window.location.href = '/org/groupinfo';
    }
});
	
	// 고유번호 있는지 없는지 확인하기
	document.getElementById('orgunqnumbtn').addEventListener('click', function() {
	    var unqnum = document.getElementById('orgunqnum').value;
	    var errorDiv = document.getElementById('uniquenumber_result');
	    console.log(unqnum);
	    // 유효성 검사
	    if (unqnum.length !== 10 || !/^\d+$/.test(unqnum)) {
	        errorDiv.textContent = "고유번호는 10자리 숫자여야 합니다.";
	        return;
	    } else {
	        errorDiv.textContent = "";
	    }
	
	    // AJAX 중복 확인 요청
		var xhr = new XMLHttpRequest();
		    xhr.onload = function() {
		        if (xhr.status === 200) {
		            let txt = xhr.responseText.trim();
		            console.log(txt);
		            if (txt == "X") {
		                alert("등록되지 않은 고유번호입니다.");
		            } else {
		                alert("등록된 고유번호입니다. 사용가능합니다.");
		            }
		        }
		    };
		    xhr.onerror = function() {
		        alert("서버와의 통신 중 오류가 발생했습니다.");
		    };
		    xhr.open('GET', "/org/checkunqnum?orgunqnum=" + unqnum);
	    xhr.send();
	});
	// 매니저 이름 확인하기
	 document.getElementById('managernamebtn').addEventListener('click', function() {
            var managerName = document.getElementById('managername').value;
            var errorDiv = document.getElementById('managerNameResult');
            console.log(managerName);
            
            // 이름 유효성 검사
            if (!/^[a-zA-Z가-힣]{1,15}$/.test(managerName)) {
                errorDiv.textContent = "이름이 유효하지 않습니다. 1~15자 한글, 영문을 사용하세요.";
                return;
            } else {
                errorDiv.textContent = "";
            }
        
            // AJAX 요청
            var xhr = new XMLHttpRequest();
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let txt = xhr.responseText.trim();
                    console.log(txt);
		            if (txt == "O") {
		                alert("등록되지 않은 이름입니다.");
		            } else {
		                alert("등록된 담당자 이름 입니다. 인증가능합니다");
		            }
                }else {
            console.error('Error:', xhr.status, xhr.statusText);
            }
            };
            xhr.onerror = function() {
                alert("서버와의 통신 중 오류가 발생했습니다.");
            };
            xhr.open('GET', '/manager/checkManagername?managername=' + encodeURIComponent(managerName));
            xhr.send();
        });
		// 매니저 ID 확인하기
       document.getElementById('managerIdbtn').addEventListener('click', function() {
    var managerId = document.getElementById('managerid').value;
    var errorDiv = document.getElementById('managerIdResult');
    console.log(managerId);
    
    // AJAX 요청
    var xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.status === 200) {
            let txt2 = xhr.responseText.trim();
            console.log(txt2);
            if (txt2 === "O") {
                alert("등록되지 않은 ID입니다.");
            } else {
                alert("등록된 담당자 ID 입니다. 인증가능합니다");
            }
        } else {
            errorDiv.textContent = "서버와의 통신 중 오류가 발생했습니다.";
        }
    };
    xhr.onerror = function() {
        alert("서버와의 통신 중 오류가 발생했습니다.");
    };
    xhr.open('GET', '/manager/checkManagerId?managerid=' + encodeURIComponent(managerId));
    xhr.send();
});
function Password() {
            const password = document.getElementById('password').value;
            const passwordCheck = document.getElementById('passwordcheck').value;

            if (password !== passwordCheck) {
                alert('비밀번호가 일치하지 않습니다.');
                return;
            }

            const xhr = new XMLHttpRequest();
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let isValid = xhr.responseText.trim();
                    if (isValid === "O") {
                        alert('비밀번호가 유효하지 않습니다.');
                    } else {
                        alert('비밀번호가 유효합니다.');
                    }
                } else {
                    alert('서버와의 통신 중 오류가 발생했습니다.');
                }
            };

            xhr.onerror = function() {
                alert('서버와의 통신 중 오류가 발생했습니다.');
            };

            xhr.open('GET', '/org/checkPw?orgpw='+ password);
            xhr.send();
        }
        