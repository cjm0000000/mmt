# MMT is a smart message robot.

WeiXin feature is Integrated, You can easy use the it.

## User guide

### 1. Build from code

	$ git clone https://github.com/cjm0000000/mmt.git mmt
	$ cd mmt
	$ mvn install
	
### 2. Create tables
	
I only supply MySQL code, you can see hole SQL at [SQL CODE] (https://github.com/cjm0000000/mmt/blob/master/mmt-web/src/main/resources/mmt_db.sql)

### 1. configure the WeiXin feature at web.xml:

	<filter>
		<filter-name>MicroChat</filter-name>
		<filter-class>lemon.weixin.gateway.MicroChatGateWay</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>MicroChat</filter-name>
		<url-pattern>/microchat/*</url-pattern>
	</filter-mapping>

### 2. implement your personalized message business:

You can implement your business by extends lemon.weixin.biz.customer.CustBasicMsgBiz,
this class supply an easy subscribe implement.

Good luck for you!