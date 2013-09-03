# MMT is a smart message robot.

WeiXin feature is Integrated, You can easy use the it.

## User Guide

	
### 1. Create tables
Download [Table structure] (https://github.com/cjm0000000/mmt/blob/master/mmt_db.sql)

### 2. Build from code

	$ git clone https://github.com/cjm0000000/mmt.git mmt
	$ cd mmt
	$ mvn install
		
### 3. configure the WeiXin feature at web.xml:

	<filter>
		<filter-name>MicroChat</filter-name>
		<filter-class>lemon.weixin.gateway.MicroChatGateWay</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>MicroChat</filter-name>
		<url-pattern>/microchat/*</url-pattern>
	</filter-mapping>

### 4. implement your personalized message business:

You can implement your business by extends `lemon.weixin.biz.customer.WXCustBasicMsgProcessor`,
this class supply an easy subscribe implement.

=====
Good luck for you!