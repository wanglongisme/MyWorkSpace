/**
 * admin.html的js
 */
var did = null;
//记录选中的日志id
function chooseDiary(myself){
	//console.log(myself);
	var a = $(myself).attr("did");
	did=a;
}

//init
$(function(){
	changeTopButton();
});

/*
 * group buttons is show or hide. 
 * 右上角按钮的切换
 */
function changeTopButton(){
	$("#li_out,#li_messages").show();
	$("input[name='tab']").on("click",function(e){
		var _class = $(this).attr("class");
		switch (_class) {
	    case "tab-1"://Home
	    	$("#ul_shower li").hide();
	    	$("#li_out,#li_messages").show();
	        break;
	    case "tab-2"://List
	    	$("#ul_shower li").hide();
	    	$("#li_edit,#li_write,#li_delete,#li_edit2,#li_see").show();
	    	selectList();
	        break;
	    case "tab-3":
	        break;
	    case "tab-4":
	        break;
	    case "tab-5":
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

//List button
function selectList(){
	//分页组件
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage,layer = layui.layer;
		  var zPage = getPage(0);
		  laypage({
			    cont: $('#page_div'),
			    pages: zPage,
			    first: 1,  //首页
			    last: zPage, //总页数
			    skin: '#1ABC9C',
			    groups: 8,
			    //prev: '<em><</em>',
			    //next: '<em>></em>',
			    prev: false,
			    next: false,
		    	jump: function(obj, first){
		    		//if(!first){
		    			//layer.msg('第 '+ obj.curr +' 页');
		    			getDiaryList(obj.curr);
		    		//}
		    	}
		});
	});  
}

function getDiaryList(page){
	$.ajax({
		async : true,
		type : "post",
		url:"../getList",
		data:{'page':page},
		dataType : "json",
		success : function(data){
			//console.log(data);
			$("#diary_list").empty();
			if(data!=null && data.length>0){
				var a = "";
				for(i=0; i<data.length; i++){
					//判断是blog还是diary
					var _a;
					if(data[i].title=="" && data[i].type=="2" ){
						//diary
						console.log(data[i].createTime);
						_a = "<a href='javascript:void(0);' onclick='chooseDiary(this);' class='list-group-item' did='"+data[i].id+"'>"+data[i].createTime+"<sup style='background:#1ABC9C;border-radius:2px;color:white;'> Diary&nbsp;</sup></a>";	
					}else{
						_a = "<a href='javascript:void(0);' onclick='chooseDiary(this);' class='list-group-item' did='"+data[i].id+"'>"+data[i].title+"</a>";
					}
					a = a + _a;
				}
				$("#diary_list").append(a);
			}
		}, error : function(){
			
		}
	});
	
}

function edit(){
	if(did==null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	$.ajax({
		async : true,
		type : "post",
		url:"../getDiary/"+did,
		data:{},
		dataType : "json",
		success : function(data){
			//悬浮层DOM
			var _div = "<script type='text/javascript' src='../js/tinymce/tinymce.min.js' ><\/script><script type='text/javascript' src='../js/tinymce/plugins/imagetools/plugin.min.js' ><\/script> " +
					"<div class='container' style='position:absolute;'> <br> <input type='text' class='form-control' id='title' name='title' value='"+data.title+"'> <br> " +
					"<textarea  rows='15' id='info' name='info'>"+data.info+"<\/textarea> " +
					"<div class='tinymce'></div> <br> " +
					"<button type='button' class='btn btn-success' onclick='update(1)'>Submit</button></div> " +
					"<form id='my_form' target='form_target' method='post' enctype='multipart/form-data' style='width:0px;height:0;overflow:hidden'>" +
					"<input id='file_input' name='file' type='file' onchange='uploadImg();'></form>" +
					"<script>" +
					"var inputId;"+
					"tinymce.init({selector:'textarea', menubar: false, " +
								//"plugins: [' advlist autolink lists link image charmap print preview anchor', " +
								//"'searchreplace visualblocks code fullscreen', " +
								//"'insertdatetime table contextmenu paste media code '], " +
								"plugins: ['  lists link image    ', " +
								"'  code ', " +
								"'  media code '], " +
								"toolbar: ' insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media code', " +
								"file_browser_callback: function(field_name, url, type, win) {" +
									"if(type=='image'){" +
										"inputId = '#'+field_name;" +
										//"$('.mce-reset').css('z-index',999999); " +
										//"$('.mce-container').css('z-index',999999); " +
										"$('#my_form input').click(); " +
									"} " +
								"} " +
					"});" +
					"<\/script>";
			//layer自定页
			editLayerId = layer.open({
			  type: 1,
			  //skin: 'layui-layer-demo', //样式类名
			  area: ['800px', '600px'],
			  zIndex:99,
			  closeBtn: 1, //显示关闭按钮
			  anim: 2,
			  shadeClose: false, //开启遮罩关闭
			  content: _div
			});
			$(".layui-layer-shade").css('z-index',999);
			$(".layui-layer").css('z-index',999);
			
		}, error : function(){
			console.log("getDiary - error");
		}
	});
	
}

//纯文本模式编辑
function edit2(){
	if(did==null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	$.ajax({
		async : true,
		type : "post",
		url:"../getDiary/"+did,
		data:{},
		dataType : "json",
		success : function(data){
			//悬浮层DOM
			var _div = 
					"<div class='container'> <br> <input type='text' class='form-control' id='title' name='title' value='"+data.title+"'> <br> " +
					"<textarea  rows='15' style='width:750px;' id='info' name='info'>"+data.info+"<\/textarea> " +
					"<div class='tinymce'></div> <br> " +
					"<button type='button' class='btn btn-success' onclick='update(2)'>Submit</button></div> ";
			//layer自定页
			editLayerId = layer.open({
			  type: 1,
			  skin: 'layui-layer-demo', //样式类名
			  area: ['800px', '600px'],
			  zIndex:99,
			  closeBtn: 1, //显示关闭按钮
			  anim: 2,
			  shadeClose: false, //开启遮罩关闭
			  content: _div
			});
		}, error : function(){
			console.log("getDiary - error");
		}
	});
	
}

function update(uType){
	var info = "";
	if(uType==1){
		info = tinyMCE.get('info').getContent();
	}else{
		info = $("#info").val();	
	}
	var title = $("#title").val();
	$.ajax({
		async : true,
		type : "post",
		url:"../update",
		data:{'info':info,'title':title,'id':did},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				layer.msg('修改成功', {
					time: 2000
				}, function(){
					selectList();
					layer.close(editLayerId);
				});
			}else{
				layer.msg('修改失败_'+data);
			}
		}, error : function(){
			console.log("update - error");
		}
	});
}

//编辑时上传图片
function uploadImg(){
	var formData = new FormData();
	formData.append("file",$("#file_input")[0].files[0]);
	$.ajax({
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
	     	var imgFullUrl = window.projectUrl.split('/isay')[0]+"/"+imgUrl;
	     	//console.log(imgFullUrl);
	     	$(inputId).val(imgFullUrl);
	     },error : function(data) {
	     	console.log("upload - img - error: "+JSON.stringify(data));
	     }
	});
}

//创建日志窗口
var createDiary = function(){
	//悬浮层DOM
	//tinymce 属性convert_urls :false //试着保持URL的完整性,而不对路径进行转换.
	var _div = "<div class='container' style='position:absolute;'> <br> " +
			"<input type='text' class='form-control' id='title' name='title' > <br> " +
			"<textarea  rows='15' id='info' name='info'><\/textarea> " +
			"<div class='tinymce'></div> <br> " +
			
			"<button type='button' class='btn btn-success' onclick='submit()'>Submit</button></div> " +
			"<form id='my_form' target='form_target' method='post' enctype='multipart/form-data' style='width:0px;height:0;overflow:hidden'>" +
			"<input id='file_input' name='file' type='file' onchange='uploadImg();'></form>" +
			"<script type='text/javascript' src='../js/tinymce/tinymce.min.js' ><\/script>" +
			"<script type='text/javascript' src='../js/tinymce/plugins/imagetools/plugin.min.js' ><\/script> " +
			"<script>tinymce.init({" +
			"	selector:'textarea',convert_urls :false, menubar: false, " +
			//"	plugins: ['advlist autolink lists link image charmap print preview anchor', " +
			//"		'searchreplace visualblocks code fullscreen', 'insertdatetime table contextmenu paste media code '], " +
			"plugins: ['  lists link image    ', " +
			"'  code ', " +
			"'  media code '], " +
			"	toolbar: ' insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image media code', " +
			"	file_browser_callback: function(field_name, url, type, win) {" +
			"		if(type=='image'){" +
			"			inputId = '#'+field_name;" +
			//"			$('#mceu_30').css('z-index',9999999);" +
			"		$('#my_form input').click(); " +
			"		} " +
			"	} " +
			"});<\/script>";
	//layer自定页
	writeLayerId = layer.open({
	  type: 1,
	  zIndex: 99,
	  title:"writing something",
	  //skin: 'layui-layer-demo', //样式类名
	  area: ['800px', '600px'],
	  closeBtn: 1, //显示关闭按钮
	  anim: 2,
	  shadeClose: false, //开启遮罩关闭
	  content: _div
	});
	$(".layui-layer-shade").css('z-index',999);
	$(".layui-layer").css('z-index',999);
	$('.nav-tabs').button();
}

var submit = function(){
	var info = tinyMCE.get('info').getContent();
	var title = $("#title").val();
	$.ajax({
		async : true,
		type : "post",
		url:"../write",
		data:{'info':info,'title':title},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				layer.msg('创建成功', {
					time: 2000
				}, function(){
					selectList();
					layer.close(writeLayerId);
				});
			}else{
				layer.msg('创建失败_'+data);
			} 
		}, error : function(){
			console.log("write - error");
		}
	});
}

var delDiary = function(){
	if(did==null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	layer.confirm('真的要删除该日志吗?',{icon: 7, title:false}, function(index){
		layer.close(index);
		var load_index = layer.load(1,{shade: [0.6,'#CCCCCC']});
		$.ajax({
			async : false,
			type : "post",
			url:"../delDiary",
			data:{'id': did},
			dataType : "text",
			success : function(data){
				layer.close(load_index); 
				if(data == "true"){
					selectList();
					layer.msg('删除成功', {
						time: 2000
					}, function(){
					});
				}else{
					layer.msg('删除失败_'+data);
				} 
			}, error : function(){
				layer.close(load_index); 
				layer.msg('请求异常');
				console.log("delDiary - error");
			}
		});
	}); 
}

var seeDiary = function(){
  layer.open({
      type: 2,
      title: '详情',
      shadeClose: true,
      shade: [0.8, '#393D49'],
      maxmin: false, //开启最大化最小化按钮
      area: ['893px', '600px'],
      content: '../diary.html?'+did
    });
}
