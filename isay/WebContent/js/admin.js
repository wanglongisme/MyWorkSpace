//init
$(function(){
	changeTopButton();
});

/**
 * @desc group buttons is show or hide. 
 *		 右上角按钮的切换
 */
function changeTopButton(){
	$("#li_out,#li_messages").show();
	$("input[name='tab']").on("click",function(e){
		var _class = $(this).attr("class");
		$("#ul_shower li").hide();
    	$("#ul_shower li").unbind();
		switch (_class) {
	    case "tab-1"://Home
	        break;
	    case "tab-2"://Diary
	    	initDiaryPageElement();
	    	$("#li_add").bind("click", createDiary);
	    	$("#li_edit").bind("click", editDiary);
	    	$("#li_see").bind("click", seeDiary);
	    	$("#li_delete").bind("click", delDiary);
	    	$("#li_add,#li_edit,#li_delete,#li_see").show();
	        break;
	    case "tab-3": //Book
	    	showBookList();
	    	$("#li_add").bind("click",addBook);
	    	$("#li_add").show();
	        break;
	    case "tab-4": //Movie
	    	showMovieList();
	    	$("#li_add").bind("click",addMovie);
	    	$("#li_add").show();
	        break;
	    case "tab-5": //Blog
	    	initBlogPageElement();
	    	$("#li_add").bind("click", createBlog);
	    	$("#li_edit").bind("click", editBlog);
	    	$("#li_see").bind("click", seeBlog);
	    	$("#li_delete").bind("click", delBlog);
	    	$("#li_add,#li_edit,#li_delete,#li_see").show();
	        break;
	    case "tab-6":
	        break;
	    case "tab-7":
	        break;
	    case "tab-8":
	        break;
	    default:
		}
	});
}

//编辑时上传图片
function uploadImg(file){
	var formData = new FormData();
	formData.append("file",file);
	var returnUrl;
	$.ajax({
		async : false,
	     url : "../upload/img",
	     type : "POST",
	     data : formData,
	     dataType : "text",
	  	 //告诉jQuery不要去处理发送的数据
	     processData:false,
	     //阻止jquery修改contentType报表头,导致传输文件的边界无法识别的错误.
	     contentType : false,
	     success : function(imgUrl) {
	     	console.log(imgUrl);
	     	var url = window.location.href;
	     	//TODO
	     	var imgFullUrl = window.projectUrl.split('/isay')[0]+"/"+imgUrl;
	     	returnUrl = imgFullUrl;
	     },error : function(data) {
	     	console.log("upload - img - error: "+JSON.stringify(data));
	     }
	});
	return returnUrl;
}

//-----------------Read-----------------------------
function showBookList(){
	$.ajax({
		async : true,
		type : "post",
		url:"../getBookList",
		//data:{},
		dataType : "json",
		success : function(data){
			//console.log(data);
			$("#book_tbody").empty();
			if(data!=null && data.length>0){
				var table = "";
				for(i=0; i<data.length; i++){
					//状态
					var statusDemo = '<span class="layui-badge layui-bg-green">在读</span>';
					if(data[i].status == "2"){
						statusDemo = '<span class="layui-badge layui-bg-gray">已读</span>';
					}
					if(data[i].status == "3"){
						statusDemo = '<span class="layui-badge layui-bg-blue">想读</span>';
					}
					//pic
					var pic = '<div><img src="'+data[i].pic+'" style="width:46.08px;height:60px;"></div>';
					//desc
					var desc = '<div style="width:220px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;" onclick=javascript:layer.alert("'+data[i].des+'")>'+data[i].des+'</div>';
					//star
					var star = '';
					for(j=0; j<data[i].star; j++){
						star = star + '<i class="layui-icon" style="font-size: 10px; ">&#xe658;</i>';
					}
					var tr = "<tr><td>"+pic+"</td><td>"+data[i].bookName+"</td><td>"+data[i].readTime+"</td><td>"
						+statusDemo+"</td><td>"+star+"</td><td>"+desc+"</td></tr>";
					table = table + tr;
				}
				$("#book_tbody").append(table);
			}
		}, error : function(){
			
		}
	});
}

function layerNotice(obj){
	layer.open({
		  type: 1,
		  shade: false,
		  title: false, //不显示标题
		  content: $(obj), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
		  cancel: function(){
		    //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
		  }
		});
}

//----------------------movie--------------------------------------
function showMovieList(){
	$.ajax({
		async : true,
		type : "post",
		url:"../getMovieList",
		dataType : "json",
		success : function(data){
			$("#movie_tbody").empty();
			if(data!=null && data.length>0){
				var table = "";
				for(i=0; i<data.length; i++){
					//状态
					var statusDemo = '<span class="layui-badge layui-bg-gray">已看</span>';
					if(data[i].status == "2"){
						statusDemo = '<span class="layui-badge layui-bg-green">想看</span>';
					}
					//pic
					var pic = '<div><img src="'+data[i].pic+'" style="width:46.08px;height:60px;"></div>';
					//desc
					var desc = '<div style="width:220px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;" onclick=javascript:layer.alert("'+data[i].des+'")>'+data[i].des+'</div>';
					//star
					var star = '';
					for(j=0; j<data[i].star; j++){
						star = star + '<i class="layui-icon" style="font-size: 10px; ">&#xe658;</i>';
					}
					var tr = "<tr><td>"+pic+"</td><td>"+data[i].movieName+"</td><td>"+data[i].seeTime+"</td><td>"
						+statusDemo+"</td><td>"+star+"</td><td>"+desc+"</td></tr>";
					table = table + tr;
				}
				$("#movie_tbody").append(table);
			}
		}, error : function(){
			
		}
	});
}