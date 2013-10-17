$(function() {
	$modal = $('#role-ajax-modal');
	
	//监听加载添加页面事件
	$('#role-add').on('click', function(){
		loadPage(url_showPage, {role_id:0}, $modal);
	});
	
	//监听加载编辑页面事件
	$('#role-update').on('click', function(){
		var role_id = getSelectedValues('role_id');
		loadPage(url_showPage, {role_id:role_id}, $modal);
	});
	
	//监听保存数据事件
	$modal.on('click', '.save', function(){
		var params_save = {
			role_id		: $('#role_id').val(),
			role_name	: $('#role_name').val(),
			role_desc	: $('#role_desc').val(),
			sort		: $('#sort').val()
	 	};
		mmtPost(url_save, params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听删除事件
	$('#role-delete').on('click', function(){
		$("#role-confirm-modal").modal();
	});
	
	//确认删除
	$("#role-confirm-modal").on('click', '.confirm-delete', function(){
		var role_id = getSelectedValues("role_id");
		mmtPost(url_delete, {role_id:role_id}, $('body'), $('.panel-heading'));
	});
	
	//监听加载权限列表
	$('.authority-list').on('click', function(){
		var role_id = this.value;
		loadPage(url_showAuthorityPage, {role_id:role_id}, $modal);
	});
	
	//监听设置权限事件
	$modal.on('click', '.authority-config', function(){
		var menu_id = getSelectedValues("menu_id");
		mmtPost(url_authoritySet, {menu_id:menu_id,role_id:this.value}, $modal, $modal.find('.modal-body'));
	});
});