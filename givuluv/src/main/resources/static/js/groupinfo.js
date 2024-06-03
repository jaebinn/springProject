 // 제출하기 버튼 눌렀을 때 
 document.getElementById('submitBtn').addEventListener('click', function() {
            var instituteName = document.getElementById('insitutename').value;
            var address = document.getElementById('address').value;
            var phoneNumber = document.getElementById('phonenum').value;
            var information = document.getElementById('infomation').value;

            var resultMessage = document.getElementById('resultMessage');

            // 유효성 검사
            if (instituteName === "" || instituteName.length < 1 || instituteName.length > 30) {
                resultMessage.innerHTML = "단체 이름을 올바르게 입력하세요! (1~30자)";
                resultMessage.style.color = "red";
                return;
            }
            if (address === "") {
                resultMessage.innerHTML = "단체 주소를 입력하세요!";
                resultMessage.style.color = "red";
                return;
            }
            if (phoneNumber === "" || !/^\d{10,11}$/.test(phoneNumber)) {
                resultMessage.innerHTML = "연락처를 올바르게 입력하세요! ('-' 없이 10~11자리 숫자)";
                resultMessage.style.color = "red";
                return;
            }
            if (information === "") {
                resultMessage.innerHTML = "단체 설명을 입력하세요!";
                resultMessage.style.color = "red";
                return;
            }

            var orgapprove = {
                instituteName: instituteName,
                address: address,
                phoneNumber: phoneNumber,
                information: information
            };

            fetch('/orgapp/join', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(orgapprove)
            })
            .then(response => response.json())
            .then(data => {
                if (data) {
                    resultMessage.innerHTML = "단체 정보가 성공적으로 저장되었습니다.";
                    resultMessage.style.color = "rgb(79,148,111)";
                     setTimeout(function() {
                	window.location.href = '/org/signupcomplete';
            }, 2000);
                } else {
                    resultMessage.innerHTML = "단체 정보 저장에 실패했습니다.";
                    resultMessage.style.color = "red";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                resultMessage.innerHTML = "서버 오류가 발생했습니다.";
                resultMessage.style.color = "red";
            });
        });
        
 // 정보가 전부다 입력해야 다음버튼 클릭 가능하게 하기  
document.getElementById('insitutename').addEventListener('input', validateForm);
document.getElementById('address').addEventListener('input', validateForm);
document.getElementById('phonenum').addEventListener('input', validateForm);
document.getElementById('infomation').addEventListener('input', validateForm);   

function validateForm() {
    var instituteName = document.getElementById('insitutename').value;
    var address = document.getElementById('address').value;
    var phoneNumber = document.getElementById('phonenum').value;
    var information = document.getElementById('infomation').value;

    if (instituteName !== "" && instituteName.length >= 1 && instituteName.length <= 30 &&
        address !== "" &&
        phoneNumber !== "" && /^\d{10,11}$/.test(phoneNumber) &&
        information !== "") {
        document.getElementById('nextPage').style.pointerEvents = 'auto';
        document.getElementById('nextPage').style.opacity = '1';
    } else {
        document.getElementById('nextPage').style.pointerEvents = 'none';
        document.getElementById('nextPage').style.opacity = '0.5';
    }
}
     