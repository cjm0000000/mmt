$(function() {
	$modal = $('#msg-ajax-modal');
	
	//监听加载添加页面事件
	$('#msg-add').on('click', function(){
		loadPage(url_showPage, {id:0}, $modal);
	});
	
	//监听加载编辑页面事件
	$('#msg-edit').on('click', function(){
		var id = getSelectedValues('id');
		loadPage(url_showPage, {id : id}, $modal);
	});
	
	//监听保存数据事件
	$modal.on('click', '.save', function(){
		var params_save = {
			id		: $('#id').val(),
			key		: $('#key').val(),
			value	: $('#value').val()
	 	};
		mmtPost(url_save, params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听删除事件
	$('#msg-delete').on('click', function(){
		$("#msg-confirm-modal").modal();
	});
	
	//确认删除
	$("#msg-confirm-modal").on('click', '.confirm-delete', function(){
		var id = getSelectedValues("id");
		mmtPost(url_delete, {id : id}, $('body'), $('.panel-heading'));
	});
});