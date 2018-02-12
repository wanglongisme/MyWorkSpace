/**
 * Movie 模块js
 */


//--------------------manager----------------------------------------
/**
 * 弹出添加movie窗口
 * @returns
 */
function addMovie(){
	var div = '<form class="layui-form" action="">'
		+'  <div class="layui-input-inline"></div>'
		+'  <div class="layui-form-item">'
		+'    <label class="layui-form-label">名称</label>'
		+'    <div class="layui-input-inline">'
		+'      <input id="movieName" type="text" maxlength="64" name="movieName" autocomplete="off" class="layui-input" style="width:260px;" onblur="getMovieImgByDouBan(this)" />'
		+'    </div>'
		+'  </div>'
		
		+'  <div class="layui-form-item">'
		+'    <label class="layui-form-label">封面</label>'
		+'    <div class="layui-input-inline">'
		+'      <input id="moviePic" type="text" maxlength="128" width="200" name="moviePic" required lay-verify="required" autocomplete="off" class="layui-input" style="width:480px;">'
		+'    </div>'
		+'  </div>'
		+'  <div class="layui-form-item">'
		+'    <label class="layui-form-label">时间</label>'
		+'    <div class="layui-input-inline">'
		+'       <input id="seeTime" type="text" class="layui-input" style="width:160px;">'
		+'    </div>'
		+'  </div>'
		+'  <div class="layui-form-item">'
		+'    <label class="layui-form-label">状态</label>'
		+'    <div class="layui-input-inline" style="width:260px;">'
		+'      <input type="radio" name="movie_status" value="1" title="已看" checked>'
		+'      <input type="radio" name="movie_status" value="2" title="想看" >'
		+'    </div>'
		+'  </div>'
		+'  <div class="layui-form-item">'
		+'    <label class="layui-form-label">星星</label>'
		+'    <div class="layui-input-inline">'
		+'      <input id="movieStar" type="text" name="movieStar" maxlength="1"  min="1" max="5" autocomplete="off" class="layui-input" style="width:160px;">'
		+'    </div>'
		+'  </div>'
		+'  <div class="layui-form-item layui-form-text">'
		+'    <label class="layui-form-label">描述</label>'
		+'    <div class="layui-input-inline">'
		+'      <input id="movieDes" type="text" maxlength="64" width="200" name="movieDes" required lay-verify="required" autocomplete="off" class="layui-input" style="width:480px;">'
		+'    </div>'
		+'  </div>'
		+'</form>'
		+'<div class="layui-input-inline">'
		+'<i id="loadDoubanPic" class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style="font-size:30px;color:#009688;display:none;">&#xe63d;</i>'
		+'<ul id="bookImgs"></ul>'
		+'</div>'
		+'<div class="layui-input-block" style="margin-left: 480px;">'
		+'  <button class="layui-btn" lay-submit lay-filter="formDemo" style="width: 150px;" onclick="addMovieSubmit()">提交</button>'
		+'</div>';
		//弹出表单
		var addFormIndex = layer.open({
			  type: 1,
			  title: 'See a movie',
			  skin: 'layui-layer-demo', //样式类名
			  closeBtn: 1, //不显示关闭按钮
			  anim: 2,
			  area: ['650px', '630px'],
			  shadeClose: false, //开启遮罩关闭
			  content: div,
			  cancel: function(){ 
				  //右上角关闭回调
				  getMovieImgFlag = true; //获取图片旗标恢复
			  }
			});
		//日期组件
		layui.use(['laydate','form'], function(){
			  var laydate = layui.laydate;
			  var form = layui.form;
			  //常规用法
			  laydate.render({
			    elem: '#seeTime'
			  });
			  form.render(); //layer 更新渲染form
		});
		
}

/**
 * see a movie,提交后台
 * @returns
 */
function addMovieSubmit(){
	var name = $("#movieName").val();
	var time = $("#seeTime").val();
	var pic = $("#moviePic").val();
	var desc = $("#movieDes").val();
	var status = $('input:radio[name="movie_status"]:checked').val();
	var star = $("#movieStar").val();
	star = (star=='')?0:star;
	$.ajax({
		type : "post",
		url:"../seeAMovie",
		data: {'movieName':name,'seeTime':time,'pic':pic,'des':desc,'status':status,'star':star},
		dataType : "text",
		success : function(data){
			if(data == "true"){
				getMovieImgFlag = true;
				showMovieList();
				layer.msg("Ok, see a movie");
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
var getMovieImgFlag = true;
function getMovieImgByDouBan(movieNameObj){
	$("#loadDoubanPic").show();
	var name = $(movieNameObj).val();
	console.log("movieFlag="+getMovieImgFlag);
	if(getMovieImgFlag){
		$.ajax({
			type : "post",
			url:"../getMovieImgByDouBan",
			data:{'movieName':name},
			dataType : "json",
			success : function(data){
				console.log(data);
				getMovieImgFlag = false;
				var img = "";
				for(i=0; i<data.length; i++){
					var imgDiv = '<li><img src="'+data[i]+'" /></li>';
					img = img + imgDiv;
				}
				$("#bookImgs").append(img);
				$("#loadDoubanPic").hide();
				addDoubanMovieImgsEvent(); //追加事件
			}, error : function(){
				console.log("get movie pic is error");
			}
		});
	}
}
/*
 * 请求douban获取的图片,添加事件
 */
function addDoubanMovieImgsEvent(){
	// item selection
	$('#bookImgs li').click(function (){
		$("#bookImgs").children("li.selected").removeClass('selected');
		$(this).toggleClass('selected');
		//img文本框赋值
		var imgPath = $(this).children(":first").attr("src");
		$("#moviePic").val(imgPath);
	});
}

