const replaceString = {
	toDiv : function(data){
		//DB에 있는 값을 div에 보여주기
		let result = "";
		result = data.split("//givuluv<enter>givuluv//").join('<br>');
		result = result.split("//givuluv<space>givuluv//").join('&nbsp;');
		
		return result;
	},
	toDB : function(data){
		//textarea에 있는 value를 DB에 저장하기
		
		let result = "";
		result = data.replace(/(?:\r\n|\r|\n)/g, "//givuluv<enter>givuluv//");
		console.log(result)
		result = result.replace(/ /g, "//givuluv<space>givuluv//")
		return result;
	},
	toTextarea : function(data){
		//DB에 있는 값을 textarea에 보여주기
		let result = "";
		result = data.split("//givuluv<enter>givuluv//").join('\r\n');
		result = result.split("//givuluv<space>givuluv//").join('\s');
		
		return result;
	}
}


function replyList(cBoardnum) {
	console.log("replyList 실행")
	let commentlastnum = $("#commentlastnum"+cBoardnum).val();
	$.ajax({
			url: '/comment/commentList',
			type: 'Get',
			data: {cBoardnum: cBoardnum,
					commentlastnum : commentlastnum},
			dataType: 'json',
			success: function (data) {
				console.log(data);
				if (data.length > 0) {
					$("#no_comment"+cBoardnum).attr("style","display:none");
					let str = '';
					$.each(data, function (index, item) {// data의 크기만큼 for문 , index=index, item=data의 index번째 방
						let commentnum = item.comment.commentnum;
						let commentdate = date(item.comment.commentregdate);
						str +=`
	<div class="comment" id="comment${commentnum}">
		<div class="commentHeader">`
						if(item.comment.type === 'O'){
							str +=`
			<a href="#" class="commentprofile">
				<img src="/summernoteImage/202405221023256949ed58d39-11cf-4eb5-bab3-0a9a7b59ab73.png">
				<p>${item.commentWriterName}</p>
			</a>`
						}
						else{
							str +=`
			<div class="commentprofile">
				<p>${item.commentWriterName}<span>${item.comment.connectid}</span></p>
			</div>`
						}
						str+=`
			<div class="like_box">
				<p>${item.commentLikeCount}</p> <!-- 1000이상이면 k 달아주기 -->`
						if(session_loginUser !== null){
							str+=`
					<a href="javascript:addcommentLike(${commentnum})" id="addcommentLike(${commentnum})"
						class="addLike"><i style="color: #666" class="fa-regular fa-heart"></i></a>
					<!--<a href="javascript:cancelcommentLike(${commentnum})" id="addLike(${commentnum})"
						class="cancelLike"><i style="color:rgb(255, 128, 194)" class="fa-solid fa-heart"></i></a>-->
					`
						}else{
							str+=`좋아요`
						}
						console.log(item.comment.commentdetail)
						let commentdetail = replaceString.toDiv(item.comment.commentdetail);
						console.log(commentdetail)
						str+=`
			</div>
		</div>
		<div class="commentdetail">
			${commentdetail}
		</div>
		<div class="commentFooter">
			<div class="date">`
						if(item.comment.commentupdatedate != null){
							str +=`<p>수정됨</p>`
						}
						str+=`
				<p>${commentdate}</p>
			</div>`
						if(item.comment.connectid === session_loginUser){
							if(item.comment.type === 'U')
							str+=`
			<div class="commentA_box">
				<a class="commentA" href="javascript:commentModifyBox(${commentnum})">수정</a>
				<a class="commentA" href="javascript:commentDelete(${commentnum})">삭제</a>
			</div>`
						}
						if(item.comment.connectid === session_loginOrg){
							if(item.comment.type === 'O')
							str+=`
			<div class="commentA_box">
				<a class="commentA" href="javascript:commentModifyBox(${commentnum})">수정</a>
				<a class="commentA" href="javascript:commentDelete(${commentnum})">삭제</a>
			</div>`
						}
						str+=`
		</div>
	</div>`
					});
					$("#board"+cBoardnum+" .commentbox").append(str);
				} else {
					console.log("없음");
				}
			},
			error: function (xhr, status, error) {
				console.error('AJAX Error: ', status, error);
			}
		});
}

function commentRegist(cBoardnum){
	console.log("commentRegist : "+cBoardnum)
	let commentdetail = $("#commentdetail"+cBoardnum).val();
	console.log("댓글 : "+commentdetail);

	var blank_pattern = /^\s+|\s+$/g;
	if(commentdetail.replace(blank_pattern, '' ) == ""){
		console.log("공백")
    	$("#commentdetail"+cBoardnum).val('');
    	$("#commentdetail"+cBoardnum).focus();
    	alert('작성된 글이 없습니다!');
    	return false;
	}

	commentdetail = replaceString.toDB(commentdetail);
	console.log("변환 댓글 : "+commentdetail);
		
	commentService.insert(
		{commentdetail : commentdetail, cboardnum : cBoardnum},
		function(result){
			console.log(result)
			$("#commentdetail"+cBoardnum).val('');
			$("#commentlastnum"+cBoardnum).val('0');
			$("#board"+cBoardnum+" .commentbox").html('');
			replyList(cBoardnum);
		}
	)
}
function commentModifyBox(commentnum){
	
}
function commentModify(commentnum){
	commentService.update
}
function commentDelete(commentnum){
	commentService.delete(
		{commentnum : commentnum},
		function(){
			console.log($("#comment"+commentnum));
			console.log("삭제")
			$("#comment"+commentnum).remove();
		});
}



const commentService = {
	insert:function(data,callback){
		console.log("전송 :",data);
		$.ajax({
			type:"POST",
			url:"/comment/regist",
			data:JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			success:function(result,status,xhr){
				callback()
			},
			error:function(xhr ,status, error){
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
	delete:function(data,callback,error){
		$.ajax({
			type:"DELETE",
			url:`/comment/${data.commentnum}`,
			success:function(result){
				callback(result);
			},
			error:function(result){
				error(result);
			}
		})
	},
	update:function(data,callback){
		$.ajax({
			type:"PATCH",
			url:"/comment/"+data.commentnum,
			data:JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			success:function(result){
				callback(result);
			}
		})
	}
}