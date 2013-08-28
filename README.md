MMT is a smart message robot.

WeiXin feature is Integrated, You can easy use the it.

You can use API such as:

1. configure the WeiXin feature at web.xml:
<filter>
	<filter-name>MicroChat</filter-name>
	<filter-class>lemon.weixin.gateway.MicroChatGateWay</filter-class>
</filter>
<filter-mapping>
	<filter-name>MicroChat</filter-name>
	<url-pattern>/microchat/*</url-pattern>
</filter-mapping>

2.implement your personalized message business:
@see lemon.weixin.biz.customer.CustBasicMsgBiz
This class supply an easy subscribe implement, you can extends it and overwrite then.

Good luck for you.