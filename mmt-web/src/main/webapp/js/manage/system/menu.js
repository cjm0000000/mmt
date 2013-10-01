$(function() {
	$.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner = '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">'
			+ '<div class="progress progress-striped active">'
			+ '<div class="progress-bar" style="width: 100%;"></div>'
			+ '</div>' + '</div>';

	$.fn.modalmanager.defaults.resize = true;

	$('[data-source]').each(function() {
		var $this = $(this), $source = $($this.data('source'));

		var text = [];
		$source.each(function() {
			var $s = $(this);
			if ($s.attr('type') == 'text/javascript') {
				text.push($s.html().replace(/(\n)*/, ''));
			} else {
				text.push($s.clone().wrap('<div>').parent().html());
			}
		});

		$this.text(text.join('\n\n').replace(/\t/g, '    '));
	});
});



/**
 * 加载页面
 * @param target
 * @param url_showPage
 * @param url_save
 */
function loadPage(target, url_showPage, url_save){
	var $modal = target;
	$('.ajax .menu').on('click', function(){
		var opType = this.id;
		var menu_id = 0;
		if(opType === 'menu-update'){
			menu_id = getSelectedValues('menu_id');
		}
		$('body').modalmanager('loading');

		setTimeout(function(){
			$modal.load(url_showPage, {menu_id:menu_id}, function(){
				$modal.modal();
			});
		}, 500);
	});
	//监听保存数据按钮
	$modal.on('click', '.save', function(){
	  $modal.modal('loading');
	  setTimeout(function(){
	    $modal.modal('loading');
	    $.post(url_save,{
			menu_id:$('#menu_id').val(),
			menu_name:$('#menu_name').val(),
			supmenucode:$('#supmenucode').val(),
			menuurl:$('#menuurl').val(),
			menuico:$('#menuico').val(),
			sort:$('#sort').val()
	 	},
		function(data){
	 		$modal.find('.modal-body')
	        .prepend('<div class="alert alert-info fade in">' + data +
	          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
	        '</div>');
	    });
		setTimeout("document.location.reload()",500);
	  }, 500);
	});
}

/**
 * 删除菜单
 */
function deleteMenu(){
	var menu_id = getSelectedValues("menu_id");
	$('body').modal('loading');
	    $.post(url_delete,{
			menu_id:menu_id
	 	},
		function(data){
	 		$('.text-left')
	        .prepend('<div class="alert alert-info fade in">' + data +
	          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
	        '</div>');
	    });
		setTimeout("document.location.reload()",500);
}