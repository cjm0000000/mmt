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
	var ids = $('input[name='+fieldName+'][checked]');
	var retCode = "";
	$.each(ids, function(key,val){
		retCode += val +',';
	});
	return retCode;
}