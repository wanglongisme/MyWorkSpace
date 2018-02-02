/**
 * 
 */
var areaArr;
//地区
var initDataArea = function(id){
	$.ajax({
		async : true,
		type : "post",
		url:"../getDataAreaList",
		data:{'id': id},
		dataType : "json",
		success : function(data){
			//console.log(data);
			areaArr = data;
			if(data!=null && data.length>0){
				var li = "";
				for(i=0; i<data.length; i++){
					var _li = "<li name='"+data[i].areaId+"'>"+data[i].area+"</li>";
					li = li + _li;
				}
				$("#areaUl").append(li);
				//获取日期列
				initDataDate(id,1);
			}
			
		}, error : function(){
			console.log("getDataAreaList - error");
		}
	});
	
}
var dateParam = "";
//日期列
var initDataDate = function(id,page){
	$.ajax({
		async : true,
		type : "post",
		url:"../getDataDateList",
		data:{'id': id,'page':page},
		dataType : "json",
		success : function(data){
			//console.log(data);
			if(data!=null && data.length>0){
				var div = "";
				for(i=0; i<data.length; i++){
					dateParam = dateParam + "'" + data[i].dateval + "',";
					var _div = "<div class='cd-table-column'>";
					var _h2 = "<h2 style='border-top: 1px solid #e6e7f1;'>"+data[i].dateval+"</h2><ul>";
					var li = "";
					for(j=0; j<areaArr.length; j++){
						var _li = "<li id='"+data[i].dateval+"-"+areaArr[j].areaId+"'></li>";
						li = li + _li;
					}
					li = li+"</ul>";
					_div = _div+_h2+li+"</div>";
					div = div + _div;
				}
				$(".cd-table-wrapper").append(div);
				//获取数据
				initData(id, dateParam);
			}else{
				console.log("没有更多数据了");
				$(".cd-table-container").unbind('scroll');
			}
		}, error : function(){
			console.log("getDataDateList - error");
		}
	});
}

//数据
var initData = function(id, dateParam){
	//var arr = JSON.stringify(dateArr);
	$.ajax({
		async : true,
		type : "post",
		url:"../getDataList",
		data:{'id': id,'dateArr':dateParam},
		dataType : "json",
		success : function(data){
			//console.log(data);
			if(data!=null && data.length>0){
				for(i=0; i<data.length; i++){
					var _id = data[i].dateVal;
					var id_ = data[i].areaId;
					var id = "#"+_id + "-" + id_;
					$(id).text(data[i].dataVal);
				}
			}
		}, error : function(){
			console.log("getDataList - error");
		}
	});
}

var dataPage = 1;
var touchRight = function(){
	$(".cd-table-container").scroll(function(){
		//console.log("touch:" +$(".cd-table-container")[0].scrollLeft+"--"+$(".cd-table-container")[0].scrollWidth+"--"+$(".cd-table-container").width());
		if( ($(".cd-table-container")[0].scrollWidth - $(".cd-table-container").width()-$(".cd-table-container")[0].scrollLeft)<5){
			console.log("加载数据,page:"+dataPage);
			dataPage = dataPage + 1;
			initDataDate(dataId, dataPage);
		}
	});
}