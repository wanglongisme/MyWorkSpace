/**
 * hexo.html 对应js
 * 2018-02-02 10:51:13
 */

//init
$(function(){
	var zPage = getDiaryPage();
	var bPage = getBlogPage();
	//首页
	initPageDiv('page-nav',zPage, loadIndexContent);
	//随笔
	initPageDiv('page-nav-1',bPage, loadBlog);
	//日志
	initPageDiv('page-nav-2',zPage, loadDiary);
	//主页,随笔,日记,看书,电影绑定事件
	$("#headerUL li").click(function(){
		var liTxt = $(this).children("a").first().text();
		if(liTxt == "主页"){
			$("#js-content-1, #js-content-2, #js-content-book, #js-content-movie, #js-content-blog").hide();
			$("#js-content").show();
		}
		if(liTxt == "随笔"){
			$("#js-content, #js-content-2, #js-content-book, #js-content-movie, #js-content-blog").hide();
			$("#js-content-1").show();
			//loadBlog(1);
		}
		if(liTxt == "日记"){
			$("#js-content, #js-content-1, #js-content-book, #js-content-movie, #js-content-blog").hide();
			$("#js-content-2").show();
			//loadDiary(1);
		}
		if(liTxt == "看书"){
			$("#js-content, #js-content-1, #js-content-2, #js-content-movie, #js-content-blog").hide();
			$("#js-content-book").show();
		}
		if(liTxt == "电影"){
			$("#js-content, #js-content-1, #js-content-2, #js-content-book, #js-content-blog").hide();
			$("#js-content-movie").show();
		}
	});
	//所有文章
	$("#allBlog").bind("click", getAllBlogTitle);
	$("#searchBtn").bind("click", searchContent);
});

/**
 * 初始化分页组件
 * @param zPage - 总条数
 * @param modelType - 模块分类(0-首页,1-随笔,2-日记)
 * @returns
 */
function initPageDiv(pageObjId, zPage, func){
	//分页组件
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage;
		  laypage.render({ //执行一个laypage实例
		     elem: pageObjId //demo id,不用加 #号
		    ,count: zPage //数据总数
		    ,limit: 12
		    ,layout: ['first', 'prev', 'page', 'next', 'last', 'count']
		    ,jump: function(obj, first){
		    	var pageDivId = "#"+pageObjId;
		    	//$(pageDivId).parent().animate({scrollTop:0}, 500);
		    	$("#container").animate({'scrollTop':0}, 200);
		        //console.log("当前页:"+obj.curr); //当前页 (注:obj包含了当前分页的所有参数)
		        func(obj.curr);
		      }
		  });
	});
}

/**
 * @desc 加载文章内容
 * @param page - 页数
 * 2018-02-02 10:58:33
 */
var loadIndexContent = function(page){
	$.ajax({
		async : false,
		type : "post",
		url:"getDiaryListByPage",
		data:{'page':page},
		dataType : "json",
		success : function(data){
			$("#page-nav").siblings().remove(); 
			if(data!=null && data.length>0){
				for(i=0; i<data.length; i++){
//					if(data[i].type == "2"){ //diary
						var divObj = new diaryObj();
						divObj.id = data[i].id;
						divObj.info = data[i].info;
						divObj.createTime = data[i].createTime;
						$("#page-nav").before(divObj.getDivText());
//					}else{
//						//blog
//						var divObj = new blogObj();
//						divObj.id = data[i].id;
//						divObj.title = data[i].title;
//						divObj.info = data[i].info;
//						divObj.createTime = data[i].createTime;
//						$("#page-nav").before(divObj.getDivText());
//					}
				}
				$("#page-nav").attr("page",page);
			}else{
				layer.msg('没有更多了');
				$(window).unbind('scroll');
			}
		}, error : function(){
			layer.msg('something wrong');
		}
	});
}

//加载日记
function loadDiary(page){
	$.ajax({
		type: "post",
		url: "getDiaryTitleListByPage",
		data:{'page' : page},
		dataType : "json",
		success : function(data){
			if(data!=null && data.length>0){
				//先清空内容
				$("#page-nav-2").siblings().remove();
				//生成时间(年.月)队列
				var timeArr = [];
				for(i=0; i<data.length; i++){
					var formatTime = data[i].createTime.substring(0,7);
					timeArr.push(formatTime);
				}
				var ymArr = removeDuplicatedItem(timeArr);
				for(j=0; j<ymArr.length; j++){
					var section = 
						'<section class="archives-wrap">'
						+'  <div class="archive-year-wrap">'
						+'    <a class="archive-year" >'+ymArr[j].replace("-",".")+'</a>'
						+'  </div>'
						+'  <div class="archives" id="ym'+ymArr[j]+'"></div>'
						+'</section>';
					$("#page-nav-2").before(section);
				}
				//生成title
				for(i=0; i<data.length; i++){
					var divObj = new titleObj();
					divObj.id = data[i].id;
					divObj.info = data[i].info;
					divObj.title = data[i].title;
					divObj.type = 2;
					divObj.createTime = data[i].createTime;
					var divId = "ym"+divObj.createTime.substring(0,7);
					$("#"+divId).append(divObj.getDivText());
				}
				return;
			}
			layer.msg('没有内容');
			$(window).unbind('scroll');
		}, error : function(){
			layer.msg('something wrong');
		}
	});
}

//加载随笔
function loadBlog(page){
	$.ajax({
		type: "post",
		url: "getBlogListByPage",
		data:{'page' : page},
		dataType : "json",
		success : function(data){
			if(data!=null && data.length>0){
				//先清空内容
				$("#page-nav-1").siblings().remove();
				//生成时间(年.月)队列
				var timeArr = [];
				for(i=0; i<data.length; i++){
					var formatTime = data[i].createTime.substring(0,7);
					timeArr.push(formatTime);
				}
				var ymArr = removeDuplicatedItem(timeArr);
				for(j=0; j<ymArr.length; j++){
					var section = 
						'<section class="archives-wrap">'
						+'  <div class="archive-year-wrap">'
						+'    <a class="archive-year" >'+ymArr[j].replace("-",".")+'</a>'
						+'  </div>'
						+'  <div class="archives" id="tt'+ymArr[j]+'"></div>'
						+'</section>';
					$("#page-nav-1").before(section);
				}
				//生成title
				for(i=0; i<data.length; i++){
					var divObj = new titleObj();
					divObj.id = data[i].id;
					divObj.info = data[i].info;
					divObj.title = data[i].title;
					divObj.type = 1;
					divObj.createTime = data[i].createTime;
					var divId = "tt"+divObj.createTime.substring(0,7);
					$("#"+divId).append(divObj.getDivText());
				}
				return;
			}
			layer.msg('没有内容');
			$(window).unbind('scroll');
		}, error : function(){
			layer.msg('something wrong');
		}
	});
}
//数组去重
function removeDuplicatedItem(ar) {
    var ret = [];
    for (var i = 0, j = ar.length; i < j; i++) {
        if (ret.indexOf(ar[i]) == -1) {
            ret.push(ar[i]);
        }
    }
    return ret;
}
//diary object
function diaryObj(){
		var id = '0';
		var type = '2';
		var info = '';
		var createTime = '';
		this.getDivText = function(){
			var diaryTemplate = 
			 '<article id="diary_'+this.id+'" class="article article-type-post article-index" itemscope="" itemprop="blogPost">'
			+'	<div class="article-inner">'
			+'		<header class="article-header"></header>'
			+'		<div class="article-entry" itemprop="articleBody">'
			+'			<p>'+this.info+'</p>'
			+'		</div>'
			+'		<div class="article-info article-info-index">'
			+'			<div style="overflow: hidden; width: 190px;">'
			+'				<i class="icon-calendar icon"></i>'+this.createTime
			+'			</div>'
			//+'			<div class="clearfix"></div>'
			+'		</div>'
			+'	</div>'
			+'</article>';
			
			return diaryTemplate;
		}
}
//blog object 
//@Deprecated
function blogObj(){
	var id = '0';
	var title = '';
	var type = 1;
	var info = '';
	var createTime = '';
	this.getDivText = function(){
		var diaryTemplate = 
			 '<article id="blog_'+this.id+'" class="article article-type-post  article-index" itemscope="" itemprop="blogPost">'
			+'	<div class="article-inner">'
			+'		<header class="article-header">'
			+'			<h1 itemprop="name">'
			+'				<a class="article-title" href="javascript:showABlog('+this.id+',1)">'+this.title+'</a>'
			+'			</h1>'
			+'			<a href="/posts/edd7588a.html" class="archive-article-date">'
			+'				<i class="icon-calendar icon"></i>'+this.createTime
			+'			</a>'
			+'		</header>'
			+'		<div class="article-entry" itemprop="articleBody">'
			+'			<p>'+this.info+'</p>'
			+'		</div>'
			+'		<div class="article-info article-info-index">'
			+'			<div class="article-tag tagcloud">'
			+'				<i class="icon-price-tags icon"></i>'
			+'				<ul class="article-tag-list">'
			+'					<li class="article-tag-list-item">'
			+'					<a href="javascript:void(0)" class="js-tag article-tag-list-link color3">label</a>'
			+'					</li>'
			+'				</ul>'
			+'			</div>'
			+'			<div class="article-category tagcloud">'
			+'				<i class="icon-book icon"></i>'
			+'				<ul class="article-tag-list">'
			+'					<li class="article-tag-list-item">'
			+'					<a href="/categories/随笔//" class="article-tag-list-link color3">type</a>'
			+'					</li>'
			+'				</ul>'
			+'			</div>'
			+'			<div class="clearfix"></div>'
			+'		</div>'
			+'	</div>'
			+'</article>';
		return diaryTemplate;
	}
}

//"随笔"和"日记"分类下,列表 object
function titleObj(){
	var id = '0';
	var title = '';
	var type = 0;
	var info = '';
	var createTime = '';
	this.getDivText = function(){
		var diaryTemplate;
		//blog
		if(this.type == 1){
			diaryTemplate = 
				 '    <article class="archive-article archive-type-post">'
				+'      <div class="archive-article-inner">'
				+'        <header class="archive-article-header">'
				+'          <div class="article-meta">'
				+'            <a class="archive-article-date">'
				+'                <i class="icon-calendar icon"/>'+this.createTime
				+'            </a>'
				+'          </div>'
				+'          <h1 itemprop="name">'
				+'            <a class="archive-article-title" href="javascript:showABlog('+this.id+',1)">'+this.title+'</a>'
				+'          </h1>'
				+'          <div class="clearfix"/>'
				+'        </header>'
				+'      </div>'
				+'    </article>';
		}
		//diary
		if(this.type == 2){
			diaryTemplate = 
				 '    <article class="archive-article archive-type-post">'
				+'      <div class="archive-article-inner">'
				+'        <header class="archive-article-header">'
				+'          <h1 itemprop="name">'
				+'            <a class="archive-article-title" href="javascript:showABlog('+this.id+',2)">'+this.title+'</a>'
				+'          </h1>'
				+'        </header>'
				+'      </div>'
				+'    </article>';
		}
		return diaryTemplate;
	}
}
//获取"读书"列表
function getReadBook(){
	var tdSize = $("#book_tbody").children().length;
	if(tdSize>0){
		console.log("The book data,not repeat request.");
		return;
	}
	$.ajax({
		async : true,
		type : "post",
		url:"getBookList",
		dataType : "json",
		success : function(data){
			$("#book_tbody").empty();
			if(data!=null && data.length>0){
				var table = "";
				for(i=0; i<data.length; i++){
					//状态
					var statusDemo = '<span class="layui-badge layui-bg-red">在读</span>';
					if(data[i].status == "2"){
						statusDemo = '<span class="layui-badge layui-bg-green">已读</span>';
					}
					if(data[i].status == "3"){
						statusDemo = '<span class="layui-badge layui-bg-blue">想读</span>';
					}
					//star
					var star = '';
					for(j=0; j<data[i].star; j++){
						star = star + '<i class="layui-icon" style="font-size: 10px; ">&#xe658;</i>';
					}
					var tr = 
						 '<tr><td>'
					 	+'<div style="overflow: hidden;width:100%;padding: 10px;">'
					    +'  <div style="float: left;width: 90px;height: 100px;"><img src="'+data[i].pic+'" style="width:76.8px;height:100px;margin: 3px auto;" /></div>'
					    +'  <div style="float: left;margin-left: 10px;width: 300px;height: 100px;">'
					    +'    <div style="height:25px;"><span style="color:#08c;font-size: 1rem;">'+data[i].bookName+'</span></div>'
					    +'    <div style="height:25px;margin-top:3px;">'+statusDemo+' // '+data[i].readTime+'</div>'
					    +'    <div style="height:25px;margin-top:3px;">'+star+'</div>'
					    +'    <div style="width:960px;height:25px;margin-top:3px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;">'+data[i].des+'</div>'
					    +'  </div>'
					    +'</div>'
						+'</td></tr>';
					table = table + tr;
				}
				$("#book_tbody").append(table);
			}
		}, error : function(){
			layer.msg("something wrong");
		}
	});
}
//获取"电影"列表
function getSeeMovie(){
	var tdSize = $("#movie_tbody").children().length;
	if(tdSize>0){
		console.log("The movie data,not repeat request.");
		return;
	}
	$.ajax({
		async : true,
		type : "post",
		url:"getMovieList",
		dataType : "json",
		success : function(data){
			$("#movie_tbody").empty();
			if(data!=null && data.length>0){
				var table = "";
				for(i=0; i<data.length; i++){
					//状态
					var statusDemo = '<span class="layui-badge layui-bg-green">看过</span>';
					if(data[i].status == "2"){
						statusDemo = '<span class="layui-badge layui-bg-red">想看</span>';
					}
					//star
					var star = '';
					for(j=0; j<data[i].star; j++){
						star = star + '<i class="layui-icon" style="font-size: 10px; ">&#xe658;</i>';
					}
					var tr = 
						 '<tr><td style="border:0;border-bottom:1px solid #dedede">'
					 	+'<div style="overflow: hidden;width:100%;padding: 10px;">'
					    +'  <div style="float: left;width: 90px;height: 100px;"><img src="'+data[i].pic+'" style="width:76.8px;height:100px;margin: 3px auto;" /></div>'
					    +'  <div style="float: left;margin-left: 10px;width: 300px;height: 100px;">'
					    +'    <div style="height:25px;"><span style="color:#08c;font-size: 1rem;">'+data[i].movieName+'</span></div>'
					    +'    <div style="height:25px;margin-top:3px;">'+statusDemo+' // '+data[i].seeTime+'</div>'
					    +'    <div style="height:25px;margin-top:3px;">'+star+'</div>'
					    +'    <div style="width:960px;height:25px;margin-top:3px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;">'+data[i].des+'</div>'
					    +'  </div>'
					    +'</div>'
						+'</td></tr>';
					table = table + tr;
				}
				$("#movie_tbody").append(table);
			}
		}, error : function(){
			layer.msg("something wrong");
		}
	});
}

//随笔 or 日记  详情
function showABlog(id,type){
	var ajaxUrl = "getBlogById";
	var ajaxData = {'blogId':id};
	if(type == 2){
		ajaxUrl = "getDiaryById";
		ajaxData = {'diaryId':id};
	}
	$.ajax({
		//async : false,
		type : "post",
		url: ajaxUrl,
		data: ajaxData,
		dataType : "json",
		success : function(data){
			if(data!=null && data.length>0){
				//隐藏其他content
				$("#js-content, #js-content-1,#js-content-2, #js-content-book, #js-content-movie").hide();
				$("#js-content-blog").empty();
				$("#js-content-blog").show();
				var blog = data[0];
				var template = 
					 ' <article id="blogInfo_'+data[0].id+'" class="article article-type-post" itemscope="" itemprop="blogPost"> '
					+'  <div class="article-inner"> '
					+'   <header class="article-header"> '
					+'    <h1 class="article-title" itemprop="name">'+data[0].title+'</h1> '
					+'    <a class="archive-article-date"> <i class="icon-calendar icon"></i>2018-01-11</a> '
					+'   </header> '
					+'   <div class="article-entry" itemprop="articleBody">'+data[0].info+'</div> '
					+'   <div class="article-info article-info-index"> '
					+'    <div class="article-tag tagcloud"> '
					+'     <i class="icon-price-tags icon"></i> '
					+'     <ul class="article-tag-list"> '
					+'      <li class="article-tag-list-item"> <a href="javascript:void(0)" class="js-tag article-tag-list-link color5">Label1</a> </li> '
					+'     </ul> '
					+'    </div> '
					+'    <div class="article-category tagcloud"> '
					+'     <i class="icon-book icon"></i> '
					+'     <ul class="article-tag-list"> '
					+'      <li class="article-tag-list-item"> <a href="#" class="article-tag-list-link color3">Label2</a> </li> '
					+'     </ul> '
					+'    </div> '
					+'   </div> '
					+'  </div> '
					+' </article> ';
//					+' <nav id="article-nav"> '
//					+'  <a href="/posts/c389b314.html" id="article-nav-newer" class="article-nav-link-wrap"> <i class="icon-circle-left"></i> '
//					+'   <div class="article-nav-title">UML中类的几种关系</div> '
//					+'  </a> '
//					+'  <a href="/posts/16df79fa.html" id="article-nav-older" class="article-nav-link-wrap"> '
//					+'   <div class="article-nav-title">20180111</div>'
//					+'   <i class="icon-circle-right"></i>'
//					+'  </a> '
//					+' </nav> ';
				$("#js-content-blog").append(template);
			}else{
				layer.msg('data is null');
			}
		}, error : function(){
			layer.msg('something wrong');
		}
	});
}
//load all blog title for search model.
function getAllBlogTitle(){
	var loadFlag = $("#searchBlogList").children().length;
	if(loadFlag>0){
		console.log("Tab data not repeat laod.");
		return;
	}
	$.ajax({
		async : true,
		type : "post",
		url:"getBlogList",
		dataType : "json",
		success : function(data){
			$("#searchBlogList").empty();
			if(data!=null && data.length>0){
				var ul = "";
				for(i=0; i<data.length; i++){
					var li =
						'<li class="search-li" q-show="isShow" style="overflow: hidden;">'
						+'	<a q-attr="href:path|urlformat" class="search-title" href="javascript:showABlog('+data[i].id+')">'
						//+'		<i class="icon-quo-left icon"></i>'
						+'		<span q-text="title">'+data[i].title+'</span>'
						+'	</a>'
						+'	<p class="search-time">'
						+'		<i class="icon-calendar icon"></i> <span>'+data[i].createTime+'</span>'
						+'	</p>'
						+'</li>';
					ul = ul + li;
				}
				$("#searchBlogList").append(ul);
			}
		}, error : function(){
			layer.msg("something wrong");
		}
	});
	getTag();
}
//load tag
function getTag(){
	$.post("getBlogTagList", function(data){
		if(data!=null && data.length>0){
			$("#tagLastDiv").siblings().remove();
			var ul = "";
			for(i=0; i<data.length; i++){
				var color = Math.floor(Math.random()*5+1);
				var li ='<li class="article-tag-list-item"><a href="javascript:void(0)" class="js-tag color'+color+'">'+data[i].name+'</a></li>';
				ul = ul + li;
			}
			$("#tagLastDiv").before(ul);
		}
	}, "json");
}

function searchContent(){
	var keyWord = $("#searchInput").val();
	$.post("searchContent", {'keyWord':keyWord}, function(data){
		$("#searchBlogList").empty();
		if(data!=null && data.length>0){
			var ul = "";
			for(i=0; i<data.length; i++){
				var li =
					 '<li class="search-li" q-show="isShow" style="overflow: hidden;">'
					+'	<a q-attr="href:path|urlformat" class="search-title" href="javascript:showABlog('+data[i].id+',1)">'
					+'		<span q-text="title">'+data[i].title+'</span>'
					+'	</a>'
					+'	<p class="search-time">'
					+'		<i class="icon-calendar icon"></i> <span>'+data[i].createTime+'</span>'
					+'	</p>'
					+'</li>';
				if(data[i].type == '2'){ //diary
					li =
						 '<li class="search-li" q-show="isShow" style="">'
						+'	<a q-attr="href:path|urlformat" class="search-title" href="javascript:showABlog('+data[i].id+',2)">'
						+'		<span q-text="title">'+data[i].title+'</span>'
						+'	</a>'
						//+'	<p class="search-time">'
						//+'		<i class="icon-calendar icon"></i> <span>'+data[i].createTime+'</span>'
						//+'	</p>'
						+'</li>';
				}
				ul = ul + li;
			}
			$("#searchBlogList").append(ul);
		}
	}, "json");
}
