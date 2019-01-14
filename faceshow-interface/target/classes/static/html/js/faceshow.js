//密码
$(function(){

	$('input').eq(1).keyup(function(){
		if($(this).val().length==0){
			 $("#p_pas1").html("密码为不能空！");
			$("#btn_submit").attr('disabled',true).addClass("full_bg"); 
		}else if($(this).val().length>5 && $(this).val().length<19){
			 $("#p_pas1").html("");
			 $("#btn_submit").attr('disabled',false);
		}else{
			 $("#p_pas1").html("长度只能在6-18个字符之间");
			 $("#btn_submit").attr('disabled',true);
		}		
	})
//	确认密码
	$('input').eq(2).keyup(function(){
		
		var pas1 = $("#pas1").val();
		if(pas1 == null || pas1.length==0){
			$("#p_pas1").html("密码为不能空！");
			return;
		}
		
		 if($(this).val()!=$('input').eq(1).val()){
			 $("#p_pas2").html("两次密码不匹配");
			$("#btn_submit").attr('disabled',true);
		}else{
			 $("#p_pas2").html("");
			 $("#btn_submit").attr('disabled',false);
		}		
	})
	
	$("#btn_submit").click(function(){
		var pas1 = $("#pas1").val();
		var pas2 = $("#pas2").val();
		if(pas1 == null || pas1.length==0){
			$("#p_pas1").html("密码为不能空！");
			return;
		}
		else if(pas1.length<6 ||pas1.length>18){
			$("#p_pas1").html("长度只能在6-18个字符之间");
			return;
		}
		
		else if(pas1 != pas2){
			$("#p_pas2").html("两次密码不匹配");
			return;
		}
		
		alert("123");
		
		// 提交
		$.post("/faceshow/user/register/retrievePwd",{"type":2,"pwd":pas1,"param":$("#pas").val()},function(data){
			if(data.code == 0){
				// 密码修改成功
				alert("密码修改成功");
                $("#btn_submit").attr('disabled',true);
			}else{
				// 密码修改失败
				alert("密码修改失败");
			}
		})
		
	})
});
	
	