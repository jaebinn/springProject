$(document).ready(function () {
    
    $('#storenamebtn').on('click', function () {
        let storename = $('#storename').val().trim();
        if (storename.length < 1 || storename.length > 30) {
            $('#storename_result').text('유효한 상호명을 입력하세요.');
            return;
        }

        $.ajax({
            url: '/store/checkStorename',
            type: 'GET',
            data: { storename : storename },
            dataType: 'text',
			success: function(data) {
                if (data === "O") {
                    $('#storename_result').text('이미 등록된 상호명입니다.');
                } else {
                    $('#storename_result').text('사용 가능한 상호명입니다.');
                }
            },
            error: function (status, error) {
                console.error('AJAX Error: ', status, error);
                $('#storename_result').text('중복 확인 중 오류가 발생했습니다.');
            }
        });
    });
    
    
    $('#regnumbtn').on('click', function () {
        let regnum = $('#regnum').val().trim();
        if (regnum.length !== 10 || !$.isNumeric(regnum)) {
            $('#regnum_result').text('유효한 사업자등록번호를 입력하세요.');
            return;
        }

        $.ajax({
            url: '/store/checkRegnum',
            type: 'GET',
            data: { regnum : regnum },
            dataType: 'text',
			success: function(data) {
                if (data === "O") {
                    $('#regnum_result').text('이미 등록된 사업자등록번호입니다.');
                } else {
                    $('#regnum_result').text('사용 가능한 사업자등록번호입니다.');
                }
            },
            error: function (status, error) {
                console.error('AJAX Error: ', status, error);
                $('#regnum_result').text('중복 확인 중 오류가 발생했습니다.');
            }
        });
    });

    // 신청 버튼 클릭 이벤트
 // 신청 버튼 클릭 이벤트
    $('#storesubmit').on('click', function (e) {
        e.preventDefault();
        
        let valid = validateForm();
        
        if (valid) {
			console.log('에이작스 실행함수 실행');
            sendAjaxRequest();
        }
    });

    function validateForm() {
        let valid = true;

        if ($('#storename_result').text() !== "사용 가능한 상호명입니다.") {
            alert("상호명 중복체크 후 가입이 가능합니다!");
            $('#storename').focus();
            valid = false;
            return;
        }
        
        
        if ($('#regnum_result').text() !== "사용 가능한 사업자등록번호입니다.") {
            alert("사업자등록번호 중복체크 통과 후 가입이 가능합니다!");
            $('#regnum').focus();
            valid = false;
            return;
        }

        let leadername = $('#leadername').val().trim();
        if (leadername.length < 1 || leadername.length > 15) {
            alert("대표자 이름을 정확히 입력해주세요.");
            $('#leadername').focus();
            valid = false;
            return;
        }
        
        let infomation = $('#information').val();
        console.log(infomation);
        
        if (infomation == '') {
            alert("스토어 소개글을 작성해주세요.");
            $('#infomation').focus();
            valid = false;
            return;
        }

        let storePhone = $('#storePhone').val().trim();
        if (storePhone.length < 10 || storePhone.length > 15 || !$.isNumeric(storePhone.replace(/-/g, ''))) {
            alert("전화번호를 정확히 입력해주세요.");
            $('#storePhone').focus();
            valid = false;
            return;
        }

        let s_zipcode = $('#s_zipcode').val().trim();
        if (s_zipcode.length < 5 || s_zipcode.length > 6 || !$.isNumeric(s_zipcode)) {
            alert("주소를 입력해주세요.");
            $('#s_zipcode').focus();
            valid = false;
            return;
        }

        let s_addrdetail = $('#s_addrdetail').val().trim();
        if (s_addrdetail.length < 1) {
            alert("상세주소를 입력해주세요.");
            $('#s_addrdetail').focus();
            valid = false;
            return;
        }

        return valid;
    }

    function sendAjaxRequest() {
        let storename = $('#storename').val().trim();
        let regnum = $('#regnum').val().trim();
        let leadername = $('#leadername').val().trim();
        let information = $('#information').val();
        let storePhone = $('#storePhone').val().trim();
        let s_zipcode = $('#s_zipcode').val().trim();
        let s_addr = $('#s_addr').val().trim();
        let s_addrdetail = $('#s_addrdetail').val().trim();
        let s_addretc = $('#s_addretc').val().trim();

        console.log('AJAX 요청 보내기');
        $.ajax({
            url: '/store/signup',
            type: 'GET',
            data: { 
                storename: storename,
                regnum: regnum,
                leadername: leadername,
                information: information,
                storePhone: storePhone,
                s_zipcode: s_zipcode,
                s_addr: s_addr,
                s_addrdetail: s_addrdetail,
                s_addretc: s_addretc
             },
            dataType: 'text',
            success: function(data) {
                if (data === "O") {
                    alert('스토어 신청이 완료되었습니다.');
                    window.location.href = '/';
                } else {
                    alert('스토어 신청을 다시 시도해주세요.');
                    window.location.href = '/store/storeSignup';
                }
            },
            error: function (status, error) {
                console.error('AJAX Error: ', status, error);
                $('#regnum_result').text('신청 중 오류가 발생했습니다.');
            }
        });
    }
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
