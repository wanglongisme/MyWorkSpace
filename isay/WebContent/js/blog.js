var bid = null;
//记录选中的日志id
function chooseBlog(myself){
	var a = $(myself).attr("bid");
	bid = a;
}
//init blog page element
function initBlogPageElement(){
	//分页组件
	var zPage = getBlogPage();
	layui.use(['laypage', 'layer'], function(){
		var laypage = layui.laypage;
		laypage.render({
			 elem: 'blog_page_div' //id,不用加 #号
			,count: zPage //数据总数
			,limit: 12
			,layout: ['first', 'prev', 'page', 'next', 'last', 'count']
			,jump: function(obj, first){
				console.log("当前所在位置 - blog - page:"+obj.curr); //当前页
				getBlogList(obj.curr);
			}
		});
	});
}
function getBlogList(page){
	$.ajax({
		async : true,
		type : "post",
		url:"../getBlogListByPage",
		data:{'page':page},
		dataType : "json",
		success : function(data){
			$("#blog_list").empty();
			if(data!=null && data.length>0){
				var a = "";
				for(i=0; i<data.length; i++){
					var _a = "<a href='javascript:void(0);' onclick='chooseBlog(this);' class='list-group-item' bid='"+data[i].id+"'>"+data[i].title+"</a>";	
					a = a + _a;
				}
				$("#blog_list").append(a);
			}
		}, error : function(){
			console.log("ajax - error");
		}
	});
}

//创建窗口
function createBlog(){
	//悬浮层DOM
	var _div = "<div class='container' style='position:absolute;'> <br> " 
			+"<input type='text' class='form-control' id='title' name='title' > <br> " 
			+"<div id='toolbar' class='toolbar'></div>"
			+"<div id='info' style='width:100%;height:420px;border:1px solid #ccc;'></div> <br> " 
			+"<button type='button' class='btn btn-success' onclick='submitBlog()'>Submit</button></div> " 
			+"<script type='text/javascript' src='../js/wangEditor.min.js' ><\/script>";
	writeLayerId = layer.open({
	  type: 1,
	  zIndex: 99,
	  title:"write",
	  area: ['60%', '80%'],
	  closeBtn: 1, //显示关闭按钮
	  anim: 6, //动画(0-6)
	  shadeClose: false, //开启遮罩关闭
	  content: _div,
	  success: function(layero, index){
		  //init editor
		  var E = window.wangEditor;
		  editor = new E('#toolbar', '#info');
		  initUploadDemo(editor);
	  }
	});
	//$(".layui-layer-shade").css('z-index',999);
	//$(".layui-layer").css('z-index',999);
	//$('.nav-tabs').button();
}

function initUploadDemo(editor){
	 // 自定义菜单配置
	  editor.customConfig.menus = [
		    'head',  // 标题
		    'bold',  // 粗体
		    'italic',  // 斜体
		    //'underline',  // 下划线
		    //'strikeThrough',  // 删除线
		    'foreColor',  // 文字颜色
		    //'backColor',  // 背景颜色
		    'link',  // 插入链接
		    'list',  // 列表
		    'justify',  // 对齐方式
		    'quote',  // 引用
		    //'emoticon',  // 表情
		    'image',  // 插入图片
		    'table',  // 表格
		    'video',  // 插入视频
		    'code',  // 插入代码
		    //'undo',  // 撤销
		    //'redo'  // 重复
		];
	  //插入网络图片回调
	  editor.customConfig.linkImgCallback = function (url) {
		    console.log(url) // url 即插入图片的地址
	  }
	  editor.customConfig.debug = 1;
	  editor.customConfig.uploadImgMaxSize = 5 * 1024 * 1024;  //3M
	  editor.customConfig.uploadImgMaxLength = 1; //最多5张
	  //上传图片的错误提示
	  editor.customConfig.customAlert = function (info) {
		    layer.msg(info);
	  }
	  //自定义上传事件
	  editor.customConfig.customUploadImg = function (files, insert) {
		    // files 是 input 中选中的文件列表
		  	var imgUrl = uploadImg(files[0]);
		    // 上传代码返回结果之后，将图片插入到编辑器中
		  	//insert 是获取图片 url 后，插入到编辑器的方法
		    insert(imgUrl);
	  }
	  editor.create();
}

var submitBlog = function(){
	//editor.txt.text() 获取文本
	var info = editor.txt.html();
	var title = $("#title").val();
	$.ajax({
		async : true,
		type : "post",
		url:"../createBlog",
		data:{'title':title, 'info':info},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				layer.msg('创建成功', {
					time: 2000
				}, function(){
					getBlogList(1);
					layer.close(writeLayerId);
				});
			}else{
				layer.msg('创建失败');
			} 
		}, error : function(){
			console.log("ajax - error");
		}
	});
}


var delBlog = function(){
	if(bid == null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	layer.confirm('真的要删除吗?',{icon: 7, title:false}, function(index){
		layer.close(index);
		var load_index = layer.load(1,{shade: [0.6,'#CCC']});
		$.ajax({
			async : false,
			type : "post",
			url:"../delBlog",
			data:{'blogId': bid},
			dataType : "text",
			success : function(data){
				if(data == "true"){
					layer.close(load_index); 
					getBlogList(1);
					layer.msg('Done', {
						time: 2000
					}, function(){
					});
				}else{
					layer.msg('Fail');
				}
			}, error : function(){
				console.log("ajax - error");
			}
		});
	});
}

function editBlog(){
	if(bid==null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	$.ajax({
		async: false,
		type : "post",
		url:"../getBlogById",
		data:{'blogId': bid},
		dataType : "json",
		success : function(data){
			if(data==null || data.length<1){
				layer.msg("data is null");
				return;
			}
			data = data[0];
			//悬浮层DOM
			var _div = 
				 "<div class='container' style='position:absolute;'> <br> " 
				+"<input type='text' class='form-control' id='title' name='title' > <br> " 
				+"<div id='toolbar' class='toolbar' ></div>"
				+"<div id='info' style='width:100%;height:420px;border:1px solid #ccc;'></div> <br> " 
				+"<button type='button' class='btn btn-success' onclick='updateBlog()'>Submit</button></div> " 
				+"<form id='my_form' target='form_target' method='post' enctype='multipart/form-data' style='width:0px;height:0;overflow:hidden'>" 
				+"<input id='file_input' name='file' type='file' onchange='uploadImg();'></form>" 
				+"<script type='text/javascript' src='../js/wangEditor.min.js' ><\/script>";
			editLayerId = layer.open({
			  type: 1,
			  title: "update",
			  area: ['60%', '80%'],
			  zIndex:99,
			  closeBtn: 1, //显示关闭按钮
			  anim: 2,
			  shadeClose: false, //开启遮罩关闭
			  content: _div,
			  success: function(layero, index){
				  //init editor
				  var E = window.wangEditor
				  editor = new E('#toolbar', '#info');
				  initUploadDemo(editor);
				  editor.txt.html(data.info);
				  $("#title").val(data.title);
			  }
			});
			//$(".layui-layer-shade").css('z-index',999);
			//$(".layui-layer").css('z-index',999);
			
		}, error : function(){
			console.log("getDiary - error");
		}
	});
	
}
function updateBlog(){
	var info = editor.txt.html();
	var title = $("#title").val();
	$.ajax({
		async : true,
		type : "post",
		url:"../updateBlog",
		data:{'info':info,'title':title,'id':bid},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				layer.msg('修改成功', {
					time: 2000
				}, function(){
					getBlogList(1);
					layer.close(editLayerId);
				});
			}else{
				layer.msg('修改失败');
			}
		}, error : function(){
			console.log("update - error");
		}
	});
}

var seeBlog = function(){
	if(bid==null){
		layer.msg('请选择内容',{time: 1000});
		return;
	}
	  layer.open({
	      type: 2,
	      title: 'Content',
	      shadeClose: true,
	      shade: [0.8, '#393D49'],
	      maxmin: false, //开启最大化最小化按钮
	      area: ['893px', '600px'],
	      content: '../diary.html?1&'+bid
	    });
	}