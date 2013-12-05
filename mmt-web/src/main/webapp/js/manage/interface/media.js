$(function() {
	//监听删除事件
	$('#media-delete').on('click', function(){
		$("#media-confirm-modal").modal();
	});
	
	//确认删除
	$("#media-confirm-modal").on('click', '.confirm-delete', function(){
		var media_id = getSelectedValues("id");
		mmtPost(url_delete, {media_id: media_id}, $('body'), $('.panel-heading'));
	});
	
	//同步到微信
	$("#media-sync-wx").on('click', function(){
		var media_id = getSelectedValues("id");
		mmtPost(url_sync, {media_id: media_id,service_type:'WEIXIN'}, $('body'), $('.panel-heading'));
	});
});