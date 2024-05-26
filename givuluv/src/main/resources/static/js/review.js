const reviewService = {
	insert:function(data,callback){
		console.log("전송 :",data);
		$.ajax({
			type:"POST",
			url:"/review/regist",
			data:JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			success:function(result,status,xhr){
				console.log(result)
				callback(result)
			},
			error:function(result,status,xhr){
				
			}
		})
	},
}