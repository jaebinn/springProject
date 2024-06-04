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
                
                updateApproveLists(sellerApprove, orgApprove);
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
    
    // ajax비동기 통신으로 승인테이블 가져오기
    $.ajax({
        url: "/manager/getApproveCnt",
        type: "GET",
        success: function(data) {
            $('#approveCnt').text(data);
        },
        error: function(e){
            console.log(e)
        }
    });
});

// 기본적으로 sellerApproveTabContent를 표시하는 함수
function showTabContent(tabName) {
    $(".tablinks").removeClass("selected");
    $(".tabcontent").hide();

    $("." + tabName).addClass("selected");
    $("#" + tabName + "TabContent").show();
}

function updateApproveLists(sellerApprove, orgApprove) {
    var sellerNotApproveList = $(".approveSellerList .waitList");
    var sellerApproveList = $(".approveSellerList .successList");

    sellerNotApproveList.empty();
    sellerApproveList.empty();

    sellerApprove.sellernotApproveList.forEach(function(seller) {
		console.log(seller)
        sellerNotApproveList.append(`<li class='sellername'><a href="/manager/sellerApproveProfile?sName=${seller.sname}" target="_blank">${seller.sname}</a><a href='#' class='isApproveBefore btn' data-type=${seller.sname}>승인</a></li>`);
    });

    sellerApprove.sellerApproveList.forEach(function(seller) {
        sellerApproveList.append(`<li class='sellername'><a href="/manager/sellerApproveProfile?sName=${seller.sname}" target="_blank">${seller.sname}</a><a href='#' class='isApproveAfter btn' data-type=${seller.sname}>승인 취소</a></li>`);
    });

    var orgNotApproveList = $(".approveOrgList .waitList");
    var orgApproveList = $(".approveOrgList .successList");

    orgNotApproveList.empty();
    orgApproveList.empty();

    orgApprove.orgnotApproveList.forEach(function(org) {
		console.log(org)
        orgNotApproveList.append(`<li class='orgname'><a href="/manager/orgApproveProfile?oapprovenum=${org.oapprovenum}" target="_blank">${org.instituteName}</a><a href='#' class='isApproveBefore btn' data-type=${org.oapprovenum}>승인</a></li>`);
    });

    orgApprove.orgApproveList.forEach(function(org) {
        orgApproveList.append(`<li class='orgname'><a href="/manager/orgApproveProfile?oapprovenum=${org.oapprovenum}" target="_blank">${org.instituteName}<a href='#' class='isApproveAfter btn' data-type=${org.oapprovenum}>승인 취소</a></li>`);
    });

    // 승인 버튼 클릭 이벤트 핸들러 추가
    $('.isApproveBefore').on('click', function(e) {
        e.preventDefault();
		if(confirm("승인하겠습니까?")){
	        let type = $(this).data('type');
	        console.log(type)
	        $.ajax({
	            url: "/manager/approve",
	            type: "POST",
	            contentType: "application/json",
	            data: JSON.stringify({
	                approveNum: type
	            }),
	            success: function(response) {
					alert(response)
					location.href="/";
	            },
	            error: function(error) {
	                console.log(error);
	            }
	        });
		}else{
			return;
		}
    });
    // 승인 버튼 클릭 이벤트 핸들러 추가
    $('.isApproveAfter').on('click', function(e) {
        e.preventDefault();
		if(confirm("승인취소하시겠습니까?")){
	        let type = $(this).data('type');
			console.log(type)
	        $.ajax({
	            url: "/manager/approveCancel",
	            type: "POST",
	            contentType: "application/json",
	            data: JSON.stringify({
	                approveNum: type
	            }),
	            success: function(response) {
					alert(response)
					location.href="/";
	            },
	            error: function(error) {
	                console.log(error);
	            }
	        });
		}else{
			return;
		}
    });
}
