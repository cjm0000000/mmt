/**
 * 选择/取消选择全部
 * @param controller
 * @param field
 */
function checkList(controller, field){
	var ids = $('input[name='+field+']');
	$.each(ids,function(key,val){
		if(controller.checked)
			val.checked = true;
		else
			val.checked = false;
	});
}

/**
 * 监听事件，控制更新和删除按钮的状态
 * @param field
 * @param btnEdit
 * @param btnDel
 * @param singleton
 * @param singleton
 */
function btnListener(field, btnEdit, btnDel, singleton, current){
	if(!btnEdit && !btnDel)
		return;
	var ids = $('input[name=' + field + ']');
	var count = 0;
	
	$.each(ids,function(key,val){
		if(singleton && current.checked && val != current)
			val.checked = false;
		if(val.checked)
			count++;
	});
	if(count === 0){
		if(btnEdit)
			btnEdit.attr('disabled', true);
		if(btnDel)
			btnDel.attr('disabled', true);
	}else if(count === 1){
		if(btnDel)
			btnDel.attr('disabled', false);
		if(btnEdit)
			btnEdit.attr('disabled', false);
	}else{
		if(btnDel)
			btnDel.attr('disabled', false);
		if(btnEdit)
			btnEdit.attr('disabled', true);
	}
}

/**
 * 根据名称获取选中的值
 * @param fieldName
 */
function getSelectedValues(fieldName){
	var ids = $('input[name='+fieldName+']');
	var retCode = [];
	$.each(ids,function(key,val){
		if(val.checked)
			retCode.push(val.value);
	});
	return retCode.join(",");
}

/**
 * 根据名称获取选中的值
 * @param url	需要载入的URL
 * @param params	参数
 * @param target	宿主元素
 */
function loadPage(url, params, target){
	_modal = target;
	if(!_modal)
		_modal = $('body');
	$('body').modalmanager('loading');
	setTimeout(function(){
		_modal.load(url, params, function(){
			if(target)
				_modal.modal();
		});
	}, 500);
}

/**
 * 根据名称获取选中的值
 * @param url	需要载入的URL
 * @param params	参数
 * @param target	宿主元素
 * @param tipsTarget	提示信息宿主元素
 */
function mmtPost(url, params, target, tipsTarget){
	if(!target)
		target = $('body');
	target.modal('loading');
	setTimeout(function(){
		$.post(url, params,function(data){
	 		tipsTarget.prepend('<div class="alert alert-info fade in">' + data +
	          '<button type="button" class="close" data-dismiss="alert">&times;</button></div>');
	    });
		setTimeout("document.location.reload()",500);
	}, 500);
}