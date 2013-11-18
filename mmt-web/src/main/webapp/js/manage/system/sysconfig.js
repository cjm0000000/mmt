$(function() {
	//监听保存数据事件
	$("#sysconfig-add").on('click', function(){
		$('#sysconfig-add').attr('disabled',true);
		var params_save = {
			domain : $('#domain').val()
	 	};
		$.post(url_save, params_save,function(data){
			$(".panel-body").prepend(data);
			setTimeout(function(){
				document.location.reload();
			},500);
	    });
	});
});