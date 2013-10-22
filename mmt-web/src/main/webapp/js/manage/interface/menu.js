$(function() {
	$modal = $('#menu-ajax-modal');
	
	//监听加载添加/编辑页面事件
	$('.ajax .menu').on('click', function(){
		var opType = this.id;
		var menu_id = 0;
		if(opType === 'menu-update'){
			menu_id = getSelectedValues('menu_id');
		}
		loadPage(baseUrl + "add-edit-page", {menu_id:menu_id}, $modal);
	});
	
	//监听保存数据事件
	$modal.on('click', '.save', function(){
		var params_save = {
			menu_id		: $('#menu_id').val(),
			name		: $('#name').val(),
			supmenucode	: $('#supmenucode').val(),
			type		: $('#type').val(),
			key			: $('#key').val(),
			sort		: $('#sort').val()
	 	};
		mmtPost(baseUrl+ "save", params_save, $modal, $modal.find('.modal-body'));
	});
	
	//监听按钮类型SELECT
	$modal.on('change', '#type', function(){
		if($('#type').val() === 'view')
			$('#div-url-key').css('display','block');
		else
			$('#div-url-key').css('display','none');
	});
	
	//监听删除事件
	$('#menu-delete').on('click', function(){
		$("#menu-confirm-modal").modal();
	});
	
	//确认删除
	$("#menu-confirm-modal").on('click', '.confirm-delete', function(){
		var menu_id = getSelectedValues("menu_id");
		mmtPost(baseUrl + "delete", {menu_id:menu_id}, $('body'), $('.text-left'));
	});
	
	//同步到微信
	$('#sync-weixin').on('click', function(){
		mmtPost(baseUrl + "sync_menu_wx", null, $('body'), $('.text-left'));
	});
});