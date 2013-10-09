$(function() {
	//监听接口开关
	$('#interface-switch').on('switch-change', function (e, data) {
	    if(data.value){
	    	$("#form-main").css('display','inline');
	    }else{
	    	$("#form-main").css('display','none');
	    }
	});
	
	//监听服务号开关
	$('#service-no').on('switch-change', function (e, data) {
	    if(data.value){
	    	$("#service-require").css('display','inline');
	    }else{
	    	$("#service-require").css('display','none');
	    }
	});
});