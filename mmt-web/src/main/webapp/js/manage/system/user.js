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
		mmtPost(url_save, params_save, $modal.find('.modal-body'), $modal.find('.modal-pre-body'));
	});
	
	//监听删除事件
	$('#user-delete').on('click', function(){
		var user_id = getSelectedValues("user_id");
		mmtPost(url_delete, {user_id:user_id}, $('body'), $('.panel-heading'));
	});
	
	//监听Switch按钮
	$('.label-toggle-switch').on('switch-change', function (e, data) {
	    $.post(url_save, {user_id : data.el.val(), islock : data.value ? "UNAVAILABLE" : "AVAILABLE"},function(data){
	 		
	    });
	});
});