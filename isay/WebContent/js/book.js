/**
 * Read 模块js
 */

//--------------------viewer-----------------------------------------

//--------------------manager----------------------------------------
/**
 * 弹出添加窗口
 * @returns
 */
function addBook(){
	var div = '<form class="layui-form" action="">'
	+'  <div class="layui-input-inline"></div>'
	+'  <div class="layui-form-item">'
	+'    <label class="layui-form-label">名称</label>'
	+'    <div class="layui-input-inline">'
	+'      <input id="book_id" type="text" maxlength="64" name="bookName" autocomplete="off" class="layui-input" style="width:260px;" onblur="getBookImgByDouBan(this)" />'
	+'    </div>'
	+'  </div>'
	+'  <div class="layui-form-item">'
	+'    <label class="layui-form-label">封面</label>'
	+'    <div class="layui-input-inline">'
	+'      <input id="book_pic" type="text" maxlength="128" width="200" name="password" required lay-verify="required" autocomplete="off" class="layui-input" style="width:480px;">'
	+'    </div>'
	+'  </div>'
	+'  <div class="layui-form-item">'
	+'    <label class="layui-form-label">时间</label>'
	+'    <div class="layui-input-inline">'
	+'       <input id="readTime" type="text" class="layui-input" style="width:160px;">'
	+'    </div>'
	+'  </div>'
	+'  <div class="layui-form-item">'
	+'    <label class="layui-form-label">状态</label>'
	+'    <div class="layui-input-inline" style="width:260px;">'
	+'      <input type="radio" name="book_status" value="2" title="已读" checked>'
	+'      <input type="radio" name="book_status" value="1" title="在读">'
	+'      <input type="radio" name="book_status" value="3" title="想读" >'
	+'    </div>'
	+'  </div>'
	+'  <div class="layui-form-item">'
	+'    <label class="layui-form-label">星星</label>'
	+'    <div class="layui-input-inline">'
	+'      <input id="bookStar" type="text" name="bookStar" maxlength="1"  min="1" max="5" autocomplete="off" class="layui-input" style="width:160px;">'
	+'    </div>'
	+'  </div>'
	+'  <div class="layui-form-item layui-form-text">'
	+'    <label class="layui-form-label">描述</label>'
	+'    <div class="layui-input-inline">'
	+'      <input id="book_desc" type="text" maxlength="64" width="200" name="desc" required lay-verify="required" autocomplete="off" class="layui-input" style="width:480px;">'
	+'    </div>'
	+'  </div>'
	+'</form>'
	+'<div class="layui-input-inline">'
	+'<i id="loadDoubanPic" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style="font-size:30px;color:#009688;display:none;">&#xe63d;</i>'
	+'</div>'
	+'<ul id="bookImgs"></ul>'
	+'<div class="layui-input-block" style="margin-left: 480px;">'
	+'  <button class="layui-btn" lay-submit lay-filter="formDemo" style="width: 150px;" onclick="addBookSubmit()">提交</button>'
	+'</div>'
	//弹出表单
	var addFormIndex = layer.open({
		  type: 1,
		  title: 'Read book',
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 1, //不显示关闭按钮
		  anim: 2,
		  area: ['650px', '680px'],
		  shadeClose: false, //开启遮罩关闭
		  content: div,
		  cancel: function(){ 
			  //右上角关闭回调
			  getBookImgFlag = true; //获取图片旗标恢复
		  }
		});
	//日期组件
	layui.use(['laydate','form'], function(){
		  var laydate = layui.laydate;
		  var form = layui.form;
		  //常规用法
		  laydate.render({
		    elem: '#readTime'
		  });
		  form.render(); //layer 更新渲染form
	});
}

/**
 * add read book
 * 2018-02-05 17:53:04
 */
function addBookSubmit(){
	var name = $("#book_id").val();
	var time = $("#readTime").val();
	var pic = $("#book_pic").val();
	var desc = $("#book_desc").val();
	var sta = $('input:radio[name="book_status"]:checked').val();
	var star = $("#bookStar").val();
	star = (star=='')?0:star;
	$.ajax({
		type : "post",
		url:"../readABook",
		data: {'bookName':name,'readTime':time,'pic':pic,'des':desc,'status':sta,'star':star},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				showBookList();
				layer.msg("Ok, read book again");
				layer.closeAll();
				return;
			}
			layer.msg("data="+data);
		}, error : function(){
			layer.msg("request is error!");
		}
	});
}
/**
 * 当"名称"input,失去焦点触发事件,去后台获取图片.
 */
var getBookImgFlag = true;
function getBookImgByDouBan(bookNameObj){
	$("#loadDoubanPic").show();
	var name = $(bookNameObj).val();
	if(getBookImgFlag){
		$.ajax({
			type : "post",
			url:"../getBookImgByDouBan",
			data:{'bookName':name},
			dataType : "json",
			success : function(data){
				getBookImgFlag = false;
				var img = "";
				for(i=0; i<data.length; i++){
					var imgDiv = '<li><img src="'+data[i]+'" /></li>';
					img = img + imgDiv;
				}
				$("#bookImgs").append(img);
				$("#loadDoubanPic").hide();
				addDoubanImgsEvent(); //追加事件
			}, error : function(){
				console.log("get book pic is error");
			}
		});
	}
}
/*
 * 请求douban获取的图片组,添加事件
 */
function addDoubanImgsEvent(){
	// item selection
	$('#bookImgs li').click(function (){
		$("#bookImgs").children("li.selected").removeClass('selected');
		$(this).toggleClass('selected');
		//img文本框赋值
		var imgPath = $(this).children(":first").attr("src");
		$("#book_pic").val(imgPath);
	  /*
	  if ($('#bookImgs li.selected').length == 0){
		  alert(11);
		  $('#bookImgs .select').removeClass('selected');  
	  }else{
		  alert(22);
		  $('#bookImgs .select').addClass('selected');  
	  }
	  */
	  //counter();
	});

	/* all item selection
	$('.select').click(function () {
	  if ($('#bookImgs li.selected').length == 0) {
	    $('#bookImgs li').addClass('selected');
	    $('.select').addClass('selected');
	  }
	  else {
	    $('#bookImgs li').removeClass('selected');
	    $('.select').removeClass('selected');
	  }
	  counter();
	});
	*/
// number of selected items
	/*
	function counter() {
	  if ($('#bookImgs li.selected').length > 0)
	    $('#bookImgs .send').addClass('selected');
	  else
	    $('#bookImgs .send').removeClass('selected');
	  $('#bookImgs .send').attr('data-counter',$('#bookImgs li.selected').length);
	}
	*/
}