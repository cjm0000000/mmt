$(function() {
	$modal = $('#cust-ajax-modal');
	
	//监听加载添加页面事件
	$('#cust-add').on('click', function(){
		loadPage(url_showPage, {cust_id:0}, $modal);
	});
	
	//监听加载编辑页面事件
	$('#cust-update').on('click', function(){
		var cust_id = getSelectedValues('cust_id');
		loadPage(url_showPage, {cust_id:cust_id}, $modal);
	});
	
	//监听保存数据事件
	$modal.on('click', '.save', function(){
		var params_save = {
			cust_id		: $('#cust_id').val(),
			cust_name	: $('#cust_name').val(),
			memo		: $('#memo').val()
	 	};
		mmtPost(url_save, params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听删除事件
	$('#cust-delete').on('click', function(){
		$("#cust-confirm-modal").modal();
	});
	
	//确认删除
	$("#cust-confirm-modal").on('click', '.confirm-delete', function(){
		var cust_id = getSelectedValues("cust_id");
		mmtPost(url_delete, {cust_id:cust_id}, $('body'), $('.panel-heading'));
	});
});