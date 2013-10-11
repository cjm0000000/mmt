$(function() {
	//监听接口开关
	$('#interface-switch').on('switch-change', function (e, data) {
	    if(data.value){
	    	$("#form-main").css('display','inline');
	    	$(".yx-config").attr('disabled',false);
	    }else{
	    	$("#form-main").css('display','none');
	    }
	});
	
	//监听保存数据事件
	$(".yx-config").on('click', function(){
		var params_save = {
			apiStatus	: document.getElementById('yxservice').checked ? 'true' : 'false',
			cust_id		: $('#cust_id').val(),
			yx_account	: $('#yx_account').val(),
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