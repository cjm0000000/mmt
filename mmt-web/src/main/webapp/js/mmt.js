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
 */
function btnListener(field, btnEdit, btnDel){
	if(!btnEdit && !btnDel)
		return;
	var ids = $('input[name=' + field + ']');
	var count = 0;
	$.each(ids,function(key,val){
		if(val.checked)
			count++;
	});
	if(count === 0){
		btnEdit.attr('disabled', true);
		btnDel.attr('disabled', true);
	}else if(count === 1){
		btnDel.attr('disabled', false);
		btnEdit.attr('disabled', false);
	}else{
		btnDel.attr('disabled', false);
		btnEdit.attr('disabled', true);
	}
}