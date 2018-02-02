/**
 * 关于日志的公用js
 * 获取页数
 */
function getPage(type){
	//getProUrl();
	var page = "99";
	$.ajax({
		async:false,
		type : "post",
		url: window.projectUrl+"/getPage",
		data:{'pageType':type},
		dataType : "text",
		success : function(data){
			page = data;
		}, error : function(){
			console.log("getPage - error");
		}
	});
	return page;
}