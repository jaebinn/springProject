// 공백 정규식
var blank_pattern = /^\s+|\s+$/g;

// 날짜 계산
function date(date) {
	date = moment(date);

	// 초 (밀리초)
	const seconds = 1;
	// 분
	const minute = seconds * 60;
	// 시
	const hour = minute * 60;
	// 일
	const day = hour * 24;
	// 달
	const month = day * 30;
	// 년
	const year = month * 12;

	var today = moment();
	var elapsedTime = today.diff(date) / 1000;

	var elapsedText = "";
	if (elapsedTime < seconds * 10) {
		elapsedText = "방금 전";
	} else if (elapsedTime < minute) {
		elapsedText = Math.trunc(elapsedTime) + "초 전";
	} else if (elapsedTime < hour) {
		elapsedText = Math.trunc(elapsedTime / minute) + "분 전";
	} else if (elapsedTime < day) {
		elapsedText = Math.trunc(elapsedTime / hour) + "시간 전";
	} else if (elapsedTime < month) {
		elapsedText = Math.trunc(elapsedTime / day) + "일 전";
	} else if (elapsedTime < year) {
		elapsedText = Math.trunc(elapsedTime / month) + "달 전";
	} else {
		elapsedText = Math.trunc(elapsedTime / year) + "년 전";
	}

	return elapsedText;
}

// textarea 변환
const replaceString = {
	toDiv: function(data) {
		//DB에 있는 값을 div에 보여주기
		let result = "";
		result = data.split("//givuluv<enter>givuluv//").join('<br>');
		result = result.split("//givuluv<space>givuluv//").join('&nbsp;');

		return result;
	},
	toDB: function(data) {
		//textarea에 있는 value를 DB에 저장하기

		let result = "";
		result = data.replace(/(?:\r\n|\r|\n)/g, "//givuluv<enter>givuluv//");
		result = result.replace(/ /g, "//givuluv<space>givuluv//")
		return result;
	},
	toTextarea: function(data) {
		//div에 있는 값을 textarea에 보여주기
		let result = "";
		result = data.split("<br>").join('\r\n');
		result = result.split("&nbsp;").join('\ ');

		return result;
	}
}

// 모달 사이드 박스
function side_box_Print(boardnum) {
	console.log(boardnum)
	let str = `<div class="modal_side_box">
	<div class="commentbox">
		<p id="no_comment${boardnum}" class="no_comment">댓글이 없습니다.</p>
	</div>
	<div class="comment_regist_box">
		<input type="hidden" id="cBoardnum" value="${boardnum}">
		
		<input type="hidden" id="modifyflag${boardnum}" value="false">
		<input type="hidden" id="replyflag${boardnum}" value="false">
		<input type="hidden" id="replyprint${boardnum}" value="false">
		
		<input type="hidden" id="commentlastnum${boardnum}" value='0'>
		<textarea class="regist_commentdetail" id="commentdetail${boardnum}"></textarea>
		<a href="javascript:commentRegist(${boardnum})" class="registBtn">등록</a>
		<div style="display:none" class="modifyBtn_box">
			<a class="modifyBtn">수정</a>
			<a class="modifyCancelBtn">취소</a>
		</div>
	</div>
</div>`;
console.log("게시글 번호 "+boardnum)
	console.log($("#board6").html())
	$("#board"+boardnum+" .modal_box .modal").append(str);
}

// 댓글 불러오기
function replyList(cBoardnum) {
	console.log("replyList 실행");
	console.log(cBoardnum);

	if ($("#replyprint" + cBoardnum).val() === 'true') {
		return;
	}
	$("#replyprint" + cBoardnum).val('true');

	let commentlastnum = $("#commentlastnum" + cBoardnum).val();

	if (commentlastnum != 0) {
		commentlastnum--;
	}
	console.log(commentlastnum)
	$.ajax({
		url: '/comment/commentList',
		type: 'Get',
		data: {
			cBoardnum: cBoardnum,
			commentlastnum: commentlastnum
		},
		dataType: 'json',
		success: function(data) {
			if(data.length==0){
				return;
			}
			
			if ($("#replyflag" + cBoardnum).val() != 'true') {
				if (commentlastnum === data[data.length - 1].comment.commentnum) {
					$("#replyflag" + cBoardnum).val('true');
				}
			} else {
				return;
			}
			console.log(data);
			if (data.length > 0) {
				$("#no_comment" + cBoardnum).attr("style", "display:none");
				let str = '';
				$.each(data, function(index, item) {// data의 크기만큼 for문 , index=index, item=data의 index번째 방
					let commentnum = item.comment.commentnum;
					let commentdate = date(item.comment.commentregdate);
					str += `
	<div class="comment" id="comment${commentnum}">
		<div class="commentHeader">`
					if (item.comment.type === 'O') {
						str += `
			<a href="#" class="commentprofile">
				<img src="${item.commentFileLink}">
				<p>${item.commentWriterName}</p>
			</a>`
			
					}
					else {
						str += `
			<div class="commentprofile">
				<p>${item.commentWriterName}<span>${item.comment.connectid}</span></p>
			</div>`
					}
					str += `
			<div class="like_box">
				<p id="commentLikeCount${commentnum}">${item.commentLikeCount}</p> <!-- 1000이상이면 k 달아주기 -->`
					if (session_loginUser !== null) {
						//유저만 보임
						if (item.commentLike == null) {
							str += `
					<a href="javascript:addCommentLike(${commentnum})" id="addCommentLike${commentnum}"
						class="addLike"><i style="color: #666" class="fa-regular fa-heart"></i></a>
					<a href="javascript:cancelCommentLike(${commentnum})" id="cancelCommentLike${commentnum}"
						class="cancelLike" style="display:none"><i style="color:rgb(255, 128, 194);" class="fa-solid fa-heart"></i></a>
					`
						}
						else {
							str += `
					<a href="javascript:addCommentLike(${commentnum})" id="addCommentLike${commentnum}"
						class="addLike" style="display:none"><i style="color: #666" class="fa-regular fa-heart"></i></a>
					<a href="javascript:cancelCommentLike(${commentnum})" id="cancelCommentLike${commentnum}"
						class="cancelLike"><i style="color:rgb(255, 128, 194);" class="fa-solid fa-heart"></i></a>
					`
						}
					} else {
						str += `좋아요`
					}
					let commentdetail = replaceString.toDiv(item.comment.commentdetail);
					str += `
			</div>
		</div>
		<div class="commentdetail">
			${commentdetail}
		</div>
		<div class="commentFooter">
			<div class="date">`
					if (item.comment.commentupdatedate != null) {
						str += `<p>수정됨</p>`
					}
					str += `
				<p>${commentdate}</p>
			</div>`
					if (item.comment.connectid === session_loginUser) {
						if (item.comment.type === 'U')
							str += `
			<div class="commentA_box">
				<a class="commentA" href="javascript:commentModifyBox(${commentnum}, ${cBoardnum})">수정</a>
				<a class="commentA" href="javascript:commentDelete(${commentnum}, ${cBoardnum})">삭제</a>
			</div>`
					}
					if (item.comment.connectid === session_loginOrg) {
						if (item.comment.type === 'O')
							str += `
			<div class="commentA_box">
				<a class="commentA" href="javascript:commentModifyBox(${commentnum}, ${cBoardnum})">수정</a>
				<a class="commentA" href="javascript:commentDelete(${commentnum}, ${cBoardnum})">삭제</a>
			</div>`
					}
					str += `
		</div>
	</div>`
					$("#commentlastnum" + cBoardnum).val(commentnum);
				});
				$("#board" + cBoardnum + " .commentbox").append(str);
			} else {
				console.log("없음");
			}
			$("#replyprint" + cBoardnum).val('false');
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error: 댓글없음 ', status, error);
			replyprint = false;
		}
	});
}

//댓글 등록
function commentRegist(cBoardnum) {
	console.log("commentRegist : " + cBoardnum)
	let commentdetail = $("#commentdetail" + cBoardnum).val();
	console.log("댓글 : " + commentdetail);

	if (commentdetail.replace(blank_pattern, '') == "") {
		console.log("공백")
		$("#commentdetail" + cBoardnum).val('');
		$("#commentdetail" + cBoardnum).focus();
		alert('작성된 글이 없습니다!');
		return false;
	}

	commentdetail = replaceString.toDB(commentdetail);
	console.log("변환 댓글 : " + commentdetail);

	commentService.insert(
		{ commentdetail: commentdetail, cboardnum: cBoardnum },
		function() {
			$("#commentdetail" + cBoardnum).val('');
			$("#commentlastnum" + cBoardnum).val('0');

			$("#replyprint" + cBoardnum).val('false');

			$("#board" + cBoardnum + " .commentbox").html(`<p id="no_comment${cBoardnum}" style="display:none" class="no_comment">댓글이 없습니다.</p>`);

			replyList(cBoardnum);
		}
	)
}

// 댓글 수정 박스
function commentModifyBox(commentnum, cBoardnum) {
	if ($("#modifyflag" + cBoardnum).val() == true) {
		return;
	}
	$("#modifyflag" + cBoardnum).val('true');


	let board = $("#board" + cBoardnum);
	// 수정을 위한 내용을 textarea에 출력하기위한 변환
	let commentdetail = board.find("#comment" + commentnum + " .commentdetail").html();
	commentdetail = commentdetail.replace(/(?:\r\n|\r|\n|\t)/g, "");
	commentdetail = commentdetail.replace(/ /g, "")
	commentdetail = replaceString.toTextarea(commentdetail);
	// 수정을 위한 내용을 textarea에 출력
	board.find(".regist_commentdetail").val(commentdetail);
	board.find(".regist_commentdetail").focus();
	// 기존 댓글박스 숨기기
	board.find("#comment" + commentnum).attr("style", "display:none");
	// 수정버튼에 href 달아주기
	board.find(".comment_regist_box .modifyBtn_box .modifyBtn").attr("href", `javascript:commentModify(${commentnum},${cBoardnum})`);
	board.find(".comment_regist_box .modifyBtn_box .modifyCancelBtn").attr("href", `javascript:commentModifyCancel(${commentnum},${cBoardnum})`);
	// 버튼 바꿔주기
	board.find(".registBtn").attr("style", "display:none");
	board.find(".modifyBtn_box").attr("style", "display:flex");

}

// 댓글 수정하기
function commentModify(commentnum, cBoardnum) {
	let board = $("#board" + cBoardnum);
	let commentdetail_temp = $("#commentdetail" + cBoardnum).val();

	if (commentdetail_temp.replace(blank_pattern, '') == "") {
		console.log("공백")
		$("#commentdetail" + cBoardnum).val('');
		$("#commentdetail" + cBoardnum).focus();

		if (confirm("삭제하시겠습니까?")) {
			commentDelete(commentnum, cBoardnum);
			commentModifyCancel(commentnum, cBoardnum)
			board.find("#comment" + commentnum).remove();
			return;
		}
	}
	let commentdetail = replaceString.toDB(commentdetail_temp);
	commentService.update(
		{ commentnum: commentnum, commentdetail: commentdetail },
		function(result) {
			// 기존 댓글박스에 DB 글 변환해서 넣어주기
			console.log(result.commentdetail)
			let result_commentdetail = replaceString.toDiv(result.commentdetail);
			board.find("#comment" + commentnum + " .commentdetail").html(result_commentdetail);
		}
	)
	commentModifyCancel(commentnum, cBoardnum);
}

// 수정 취소 or 수정 끝
function commentModifyCancel(commentnum, cBoardnum) {
	let board = $("#board" + cBoardnum);
	// textarea의 value 없애기
	board.find(".regist_commentdetail").val('');
	// 기존 댓글박스 보여주기
	board.find("#comment" + commentnum).attr("style", "display:block");
	// 수정버튼에 href 지우기
	board.find(".comment_regist_box .modifyBtn_box .modifyBtn").removeAttr("href");
	board.find(".comment_regist_box .modifyBtn_box .modifyCancelBtn").removeAttr("href");
	// 버튼 바꿔주기
	board.find(".registBtn").attr("style", "display:flex");
	board.find(".modifyBtn_box").attr("style", "display:none");

	$("#modifyflag" + cBoardnum).val('false');
}

// 댓글 삭제
function commentDelete(commentnum, cBoardnum) {
	commentService.delete(
		{ commentnum: commentnum },
		function() {
			$("#comment" + commentnum).remove();

			let board = $("#board" + cBoardnum);
			let commentbox = board.find(".commentbox");
			let commentbox_child_length = commentbox.children().length;
			if (commentbox_child_length < 2) {
				board.find(".commentbox .no_comment").attr("style", "display:flex")
				return;
			}

			$("#board" + cBoardnum + " .commentbox").html(`<p id="no_comment${cBoardnum}" style="display:none" class="no_comment">댓글이 없습니다.</p>`);
			$("#commentlastnum" + cBoardnum).val('0');
			$("#replyprint" + cBoardnum).val('false');
			replyList(cBoardnum);
		});
}

// 댓글 서비스
const commentService = {
	insert: function(data, callback) {
		console.log("전송 :", data);
		$.ajax({
			type: "POST",
			url: "/comment/regist",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				callback()
			},
			error: function(xhr, status, error) {
				alert("댓글 등록에 실패했습니다.");
				console.error('AJAX Error: ', status, error);
			}
		})
	},
	/*	selectAll:function(data,callback){
			let boardnum = data.boardnum;
			let pagenum = data.pagenum;
			
			$.getJSON(
				`/reply/list/${boardnum}/${pagenum}`,
				function(data){
					//data : 응답되는 JSON ({ replyCnt:댓글갯수, list:[...] })
					console.log
					callback(data);
				}
			)
		},*/
	delete: function(data, callback, error) {
		$.ajax({
			type: "DELETE",
			url: `/comment/${data.commentnum}`,
			success: function(result) {
				callback(result);
			},
			error: function(result) {
				error(result);
			}
		})
	},
	update: function(data, callback) {
		$.ajax({
			type: "PATCH",
			url: "/comment/" + data.commentnum,
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result) {
				console.log(result)
				callback(result);
			}
		})
	}
}

// 보드 좋아요
function addBoardLike(boardnum) {
	boardLikeService.insert(
		{ connectid: boardnum },
		function(result) {
			$(".boardLikeCount" + boardnum).html(result);
			console.log($(".addBoardLike" + boardnum).html())
			$(".addBoardLike" + boardnum).attr("style", "display:none");
			$(".cancelBoardLike" + boardnum).attr("style", "display:block");
		}
	)
}
// 보드 좋아요 취소
function cancelBoardLike(boardnum) {
	boardLikeService.delete(
		{ connectid: boardnum },
		function(result) {
			$(".boardLikeCount" + boardnum).html(result);
			$(".addBoardLike" + boardnum).attr("style", "display:block");
			$(".cancelBoardLike" + boardnum).attr("style", "display:none");
		}
	)
}
// 댓글 좋아요
function addCommentLike(commentnum) {
	commentLikeService.insert(
		{ connectid: commentnum },
		function(result) {
			$("#commentLikeCount" + commentnum).html(result);
			$("#addCommentLike" + commentnum).attr("style", "display:none");
			$("#cancelCommentLike" + commentnum).attr("style", "display:block");
		}
	)
}
// 댓글 좋아요 취소
function cancelCommentLike(commentnum) {
	commentLikeService.delete(
		{ connectid: commentnum },
		function(result) {
			$("#commentLikeCount" + commentnum).html(result);
			$("#addCommentLike" + commentnum).attr("style", "display:block");
			$("#cancelCommentLike" + commentnum).attr("style", "display:none");
		}
	)
}

const commentLikeService = {
	insert: function(data, callback) {
		$.ajax({
			type: "POST",
			url: "/comment/likeregist",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				callback(result);
			},
			error: function(xhr, status, error) {
				console.error('AJAX Error: ', status, error);
			}
		})
	},
	delete: function(data, callback) {
		$.ajax({
			type: "DELETE",
			url: "/comment/likecancel",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				callback(result)
			},
			error: function(xhr, status, error) {
				console.error('AJAX Error: ', status, error);
			}
		})
	}
}
const boardLikeService = {
	insert: function(data, callback) {
		$.ajax({
			type: "POST",
			url: "/campaign/likeregist",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				callback(result);
			},
			error: function(xhr, status, error) {
				console.error('AJAX Error: ', status, error);
			}
		})
	},
	delete: function(data, callback) {
		$.ajax({
			type: "DELETE",
			url: "/campaign/likecanacel",
			data: JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success: function(result) {
				callback(result)
			},
			error: function(xhr, status, error) {
				console.error('AJAX Error: ', status, error);
			}
		})
	}
}


function addFollow(orgid) {
	let data = { orgid: orgid }
	console.log(orgid)
	console.log("팔로우")
	$.ajax({
		type: "POST",
		url: "/user/addFollow",
		data: JSON.stringify(data),
		contentType: "application/json;charset=utf-8",
		success: function(result, status, xhr) {
			$(".addFollow_" + orgid).remove();
		},
		error: function(xhr, status, error) {
			console.error('AJAX Error: ', status, error);
		}
	})
}
