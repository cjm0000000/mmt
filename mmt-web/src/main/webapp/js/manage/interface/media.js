$(function() {
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