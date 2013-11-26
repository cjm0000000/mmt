$(function() {
	
	//监听保存数据事件
	$(".save").on('click', function(){
		var params_save = {
			cust_id		: $('#cust_id').val(),
			cust_name	: $('#cust_name').val(),
			memo		: $('#memo').val()
	 	};
		mmtPost(url_save, params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听删除事件
	$('#media-delete').on('click', function(){
		$("#media-confirm-modal").modal();
	});
	
	//确认删除
	$("#media-confirm-modal").on('click', '.confirm-delete', function(){
		var media_id = getSelectedValues("id");
		mmtPost(url_delete, {id: media_id}, $('body'), $('.panel-heading'));
	});
});