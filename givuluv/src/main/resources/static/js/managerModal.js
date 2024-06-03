$(document).ready(function () {
    // 모달 디스플레이 제어
    $('.manager').on('click', function() {
        $('#managerApprove').css('display', 'block');
        showTabContent("sellerApprove");
        $.ajax({
			url: "/manager/getApproveInfo",
			type: "GET",
			success: function(data) {
				console.log(data)
				let sellerApprove = data[0];
				let orgApprove = data[1];
				$('.approveSellerList .wait').text(`승인 대기[${sellerApprove.sellernotApproveCnt}]`)
				$('.approveOrgList .wait').text(`승인 대기[${orgApprove.orgnotApproveCnt}]`)
				$('.approveSellerList .success').text(`승인 완료[${sellerApprove.sellerApproveCnt}]`)
				$('.approveOrgList .success').text(`승인 완료[${orgApprove.orgApproveCnt}]`)
		        
		        $('.approveSellerList .waitList').append(
					` <li class="sellername">${sellerApprove.sellernotApproveList.instituteName}<a href="#" class="isApprove">승인</a></li>`
				)
				
				
			},
			error: function(e){
				console.log(e)
			}
		})
        
    });

    $('.close').click(function() {
        $('#managerApprove').css('display', 'none');
    });

    $(window).click(function(event) {
        if ($(event.target).is('#managerApprove')) {
            $('#managerApprove').css('display', 'none');
        }
    });

    // 탭 제어
    $(".tablinks").on("click", function () {
        var tabName = $(this).attr("class").split(" ")[1];

        // 모든 탭 버튼에서 'selected' 클래스 제거 및 모든 탭 콘텐츠 숨기기
        $(".tablinks").removeClass("selected");
        $(".tabcontent").hide();

        // 클릭된 탭에 'selected' 클래스 추가 및 관련 탭 콘텐츠 표시
        $(this).addClass("selected");
        $("#" + tabName + "TabContent").show();
    });

    // 기본 탭 콘텐츠 표시
    showTabContent("sellerApprove");

    // 승인 버튼 클릭 시 내용 토글
    $(".approveBtn").on("click", function () {
        $(this).next("div").toggle();
    });
    
    //ajax비동기 통신으로 승인테이블 가져오기
	$.ajax({
		url: "/manager/getApproveCnt",
		type: "GET",
		success: function(data) {
			$('#approveCnt').text(data);
		},
		error: function(e){
			console.log(e)
		}
		
	})
});

// 기본적으로 sellerApproveTabContent를 표시	하는 함수
function showTabContent(tabName) {
    $(".tablinks").removeClass("selected");
    $(".tabcontent").hide();

    $("." + tabName).addClass("selected");
    $("#" + tabName + "TabContent").show();
}

