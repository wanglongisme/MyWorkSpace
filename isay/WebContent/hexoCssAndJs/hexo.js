/**
 * hexo.html 对应js
 * 2018-02-02 10:51:13
 */

//init
$(function(){
	initPageDiv();
	//loadDiary(1);
});

function initPageDiv(){
	//分页组件
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage,layer = layui.layer;
		  var zPage = getPage(0);
		  laypage({
			    cont: $('#page-nav'),
			    pages: zPage,
			    first: 1,
			    last: zPage,
			    skin: '#0099CC',
			    groups: 8,
			    //prev: '<em><</em>',
			    //next: '<em>></em>',
			    prev: false,
			    next: false,
		    	jump: function(obj, first){
		    		//if(!first){
		    			//layer.msg('第 '+ obj.curr +' 页');
		    		loadDiary(obj.curr);
		    		//}
		    	}
		});
	}); 
}

/**
 * @desc 加载文章内容
 * @param page - 页数
 * 2018-02-02 10:58:33
 */
var loadDiary = function(page){
	$.ajax({
		async : false,
		type : "post",
		url:"getDiaryList",
		data:{'page':page},
		dataType : "json",
		success : function(data){
			$("#page-nav").siblings().remove(); 
			if(data!=null && data.length>0){
				for(i=0; i<data.length; i++){
					if(data[i].type == "2"){ //diary
						var divObj = new diaryObj();
						divObj.id = data[i].id;
						divObj.info = data[i].info;
						divObj.createTime = data[i].createTime;
						$("#page-nav").before(divObj.getDivText());
					}else{
						//blog
						var divObj = new blogObj();
						divObj.id = data[i].id;
						divObj.title = data[i].title;
						divObj.info = data[i].info;
						divObj.createTime = data[i].createTime;
						$("#page-nav").before(divObj.getDivText());
					}
				}
			}else{
				layer.msg('没有更多了');
				$(window).unbind('scroll');
			}
		}, error : function(){
			layer.msg('something wrong');
		}
	});
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
function blogObj(){
	var id = '0';
	var title = '';
	var type = '1';
	var info = '';
	var createTime = '';
	this.getDivText = function(){
		var diaryTemplate = 
			 '<article id="blog_'+this.id+'" class="article article-type-post  article-index" itemscope="" itemprop="blogPost">'
			+'	<div class="article-inner">'
			+'		<header class="article-header">'
			+'			<h1 itemprop="name">'
			+'				<a class="article-title" href="/posts/edd7588a.html">'+this.title+'</a>'
			+'			</h1>'
			+'			<a href="/posts/edd7588a.html" class="archive-article-date">'
			+'				<time datetime="2018-01-25T14:18:29.000Z" itemprop="datePublished">'
			+'					<i class="icon-calendar icon"></i>'+this.createTime
			+'				</time>'
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


