# MMT is a smart message robot.

1. WeiXin feature is Integrated, You can easy use the it.
2. YiXin feature is Integrated, You can easy use the it.

## User Guide

	
### 1. Create tables
Download [Table structure] (https://github.com/cjm0000000/mmt/blob/master/mmt_db.sql)

### 2. Build from code

	$ git clone https://github.com/cjm0000000/mmt.git mmt
	$ cd mmt
	$ mvn install
		
### 3. configure the WeiXin feature at web.xml:

	<filter>
		<filter-name>microChatGW</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>microChatGW</filter-name>
		<url-pattern>/microchat/*</url-pattern>
	</filter-mapping>
	
	<filter>
		<filter-name>yixinxGW</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>yixinxGW</filter-name>
		<url-pattern>/echat/*</url-pattern>
	</filter-mapping>

### 4. implement your personalized message business:

For WeiXin: You can implement your business by extends `lemon.weixin.biz.customer.WXCustBasicMsgProcessor`,
this class supply an easy subscribe implement.

For YiXin: You can implement your business by extends `lemon.yixin.biz.customer.YXCustBasicMsgProcessor`,
this class supply an easy subscribe implement.

=====
Good luck for you!