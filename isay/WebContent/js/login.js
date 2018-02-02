/*
---------------------------
-Click on the Button Join !-
---------------------------

var t = 0;
function join_1(){
	if(t == 0){
		setTimeout(function(){
			document.querySelector('.cont_join').className = 'cont_join cont_join_form_act';
		},1000);    
		t++;
    }else{
    	t++;            
    	document.querySelector('.cont_form_join').style.bottom = '-420px';
    	setTimeout(function(){               
    		document.querySelector('.cont_join').className = 'cont_join cont_join_form_act cont_join_finish';
    	},500);
  }           
}
*/
function join_1(){
	$("#form_user").submit();
	/*
	//ajax提交的数据, mvc无法转发.
	var userName = $("#userName").val();
	var pwd = $("#pwd").val();
	$.ajax({
		async : false,
		type : "post",
		url:"admin/login.php",
		data:{'userName':userName,'pwd':pwd},
		dataType : "json",
		 //告诉jQuery不要去处理发送的数据
	     processData:false,
	     //阻止jquery修改contentType报表头,导致传输文件的边界无法识别的错误.
	     contentType : false,
		success : function(data){
			console.log(data);
		
		}, error : function(){
			console.log("login - error");
		}
	});     
	*/
}