$(function() {
	//监听接口开关
	$('#interface-switch').on('switch-change', function (e, data) {
	    if(data.value){
	    	$("#form-main").css('display','inline');
	    	$(".wx-config").attr('disabled',false);
	    }else{
	    	$("#form-main").css('display','none');
	    	$(".wx-config").attr('disabled',true);
	    }
	});
	
	//监听服务号开关
	$('#service-no').on('switch-change', function (e, data) {
	    if(data.value){
	    	$("#service-require").css('display','inline');
	    }else{
	    	$("#service-require").css('display','none');
	    }
	});
	
	//监听保存数据事件
	$(".wx-config").on('click', function(){
		var params_save = {
			apiStatus	: document.getElementById('wxservice').checked ? 'true' : 'false',
			cust_id		: $('#cust_id').val(),
			wx_account	: $('#wx_account').val(),
			account_type: document.getElementById('service-switch').checked ? 'FW' : 'DY',
			subscribe_msg: $('#subscribe_msg').val(),
			welcome_msg	: $('#welcome_msg').val(),
			appid		: $('#appid').val(),
			secret		: $('#secret').val()
	 	};
		$.post(url_save, params_save,function(data){
			$(".panel-body").prepend(data);
			setTimeout(function(){
				document.location.reload();
			},500);
	    });
	});
});