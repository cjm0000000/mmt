$(function() {
	$modal = $('#user-ajax-modal');
	//监听加载添加页面事件
	$('#user-add').on('click', function(){
		loadPage(url_showPage, {user_id:0}, $modal);
	});
	
	//监听加载编辑页面事件
	$('#user-update').on('click', function(){
		var user_id = getSelectedValues('user_id');
		loadPage(url_showPage, {user_id:user_id}, $modal);
	});
	
	//监听保存数据事件
	$modal.on('click', '.save', function(){
		if($('#password').val() !== $('#repassword').val()){
			if($('.json-result'))
				$('.json-result').remove();
			$modal.find('.modal-body')
	        .prepend('<div class="json-result alert alert-info fade in">' +
	                '两次输入的密码不一致。<button type="button" class="close" data-dismiss="alert">&times;</button>' +
	              '</div>');
			return;
		}
		var params_save = {
			user_id		: $('#user_id').val(),
			username	: $('#username').val(),
			password	: $('#password').val(),
			xm			: $('#xm').val(),
			mphone		: $('#mphone').val(),
			idcard		: $('#idcard').val(),
			islock		: document.getElementById('islock').checked ? 'AVAILABLE' : 'UNAVAILABLE',
			bz			: $('#bz').val(),
			role_id		: $('#role_id').val(),
			cust_id		: $('#cust_id').val()
	 	};
		mmtPost(url_save, params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听删除事件
	$('#user-delete').on('click', function(){
		$("#user-confirm-modal").modal();
	});
	
	//确认删除
	$("#user-confirm-modal").on('click', '.confirm-delete', function(){
		var user_id = getSelectedValues("user_id");
		mmtPost(url_delete, {user_id:user_id}, $('.table'), $('.panel-heading'));
	});
	
	//监听Switch按钮
	$('.label-toggle-switch').on('switch-change', function (e, data) {
	    $.post(url_change_lock, {user_id : data.el.val(), islock : data.value ? "UNAVAILABLE" : "AVAILABLE"},function(data){
	    	var result = eval("("+data+")");
	    	if(!result.success){
	    		$(".panel-heading").prepend(result.msg);
	    		setTimeout(function(){
	    			$(".json-result").remove();
	    		},500);
	    	}
	    });
	});
});