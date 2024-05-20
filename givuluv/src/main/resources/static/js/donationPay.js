$(document).ready(function() {
	$('.categoryBtn').click(function() {
		$('.categoryBtn').removeClass('selected');
		$(this).addClass('selected');
	});
	// 클릭 시 on 클래스 토글 및 아이콘 업데이트
	$('.dtn_tab li').on('click', function() {
		// 모든 li에서 on 클래스 제거 및 아이콘 변경
		$('.dtn_tab li').each(function() {
			let icon = $(this).find('i.fa-circle-check');
			$(this).removeClass('on');
			icon.removeClass('fa-solid solid').addClass('fa-regular regular').css('color', 'grey');
		});

		// 클릭된 li에 on 클래스 추가 및 아이콘 변경
		$(this).addClass('on');
		let icon = $(this).find('i.fa-circle-check');
		icon.removeClass('fa-regular regular').addClass('fa-solid solid').css('color', 'rgb(231,112,151)');
	});

	$('.dtn_tab li').each(function() {
		let icon;
		if ($(this).hasClass('on')) {
			icon = $('<i class="fa-solid fa-circle-check solid"></i>');
		} else {
			icon = $('<i class="fa-regular fa-circle-check regular"></i>');
		}
		$(this).find('a span').prepend(icon);
	});
	// 고정 금액 버튼 클릭 시
	$('.jq_fix_amount').on('click', function() {
        const amount = parseInt($(this).data('amount').replace(/,/g, ''));
        const currentAmount = parseInt($('#ChargeTotalAmount').val().replace(/,/g, ''));
        const newAmount = currentAmount + amount;
        $('#ChargeTotalAmount').val(newAmount.toLocaleString());
    });
    // 초기화 버튼 클릭 시
     $('.reset').on('click', function() {
        $('#ChargeTotalAmount').val('0');
    });
	function checkAgreements() {
        const allAgreed = $('#agree_term').is(':checked');
        const donateButton = $('.jq_donate');
        if (allAgreed) {
            donateButton.removeClass('disabled');
        } else {
            donateButton.addClass('disabled');
        }
        checkAmount();
    }
	// 기부 금액이 1000원 이상인지 체크
    function checkAmount() {
        const totalAmount = parseInt($('#ChargeTotalAmount').val().replace(/,/g, ''));
        const donateButton = $('.jq_donate');
        if (totalAmount >= 1000) {
            donateButton.removeClass('disabled');
        } else {
            donateButton.addClass('disabled');
        }
    }
    // 필수 항목 체크박스 변화 감지
    $('#agree_term').on('change', function() {
        checkAgreements();
    });

    // 마케팅 알림 수신 동의 체크박스 변화 감지
    $('#agree_alarm').on('change', function() {
        checkAgreements();
    });

    // 전체 동의 체크박스 변화 감지
    $('#agree_all').on('change', function() {
        const isChecked = $(this).is(':checked');
        $('#agree_term, #agree_alarm').prop('checked', isChecked);
        checkAgreements();
    });

    // 초기 상태 설정
    checkAgreements();

    // 기부하기 버튼 클릭 시 필수 동의 여부 확인
    $('.jq_donate').on('click', function(e) {
        const allAgreed = $('#agree_term').is(':checked');
        const totalAmount = parseInt($('#ChargeTotalAmount').val().replace(/,/g, ''));
        if (!allAgreed) {
            alert('필수 약관 동의를 해주세요.');
            e.preventDefault();
        } else if (totalAmount < 1000) {
            alert('최소 1000원이상 입력해주세요.');
            e.preventDefault();
        }
    });
});