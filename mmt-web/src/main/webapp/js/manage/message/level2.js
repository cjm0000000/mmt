$(function() {
	$modal = $('#msg-level2-ajax-modal');
	
	//监听加载添加页面事件
	$('#level2-msg-add').on('click', function(){
		loadPage(url_showPage, {id:0}, $modal);
	});
	
	//监听加载编辑页面事件
	$('#level2-msg-edit').on('click', function(){
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
		mmtPost(url_save, params_save, $modal.find('.modal-body'), $modal.find('.modal-pre-body'));
	});
	
	//监听删除事件
	$('#level2-msg-delete').on('click', function(){
		var id = getSelectedValues("id");
		mmtPost(url_delete, {id : id}, $('body'), $('.panel-heading'));
	});
});