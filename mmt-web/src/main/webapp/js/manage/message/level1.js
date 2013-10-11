$(function() {
	//监听保存数据事件
	$(".level1-save").on('click', function(){
		var result = [];
		$.each($("input[name='record-id']"),function(key,val){
			result.push({
				id		: val.value,
				key		: $('#key'+(key+1)).val(),
				value	: $('#value'+(key+1)).val()
			});
		});
		alert(JSON.stringify(result));
		$.post(url_save, {json:JSON.stringify(result)},function(data){
			$(".panel-body").prepend(data);
			setTimeout(function(){
				document.location.reload();
			},500);
	    });
	});
});