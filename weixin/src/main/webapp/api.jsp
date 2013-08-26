<%
//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		
		System.out.println("signature="+signature);
		System.out.println("timestamp="+timestamp);
		System.out.println("nonce="+nonce);
		System.out.println("echostr="+echostr);
		out.print(echostr);

%>