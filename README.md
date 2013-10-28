# MMT是一个智能的微信、易信公众平台接入系统.

### 特性：
 1. 集成微信公众平台API。
 2. 集成易信公众平台API。
 3. 集成简单WEB管理平台。
 4. Spring Security 3 管理系统权限。

## 用户手册
	
### 1. 创建表（基于MYSQL数据库）
下载 [建表SQL] (https://github.com/cjm0000000/mmt/blob/master/mmt_db.sql)

### 2. 构建【构建之前请先修改数据源】

	$ git clone https://github.com/cjm0000000/mmt.git mmt
	$ cd mmt
	$ mvn install
		
### 3. 配置微信接入:

	<filter>
		<filter-name>weixinGW</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>weixinGW</filter-name>
		<url-pattern>/weichat/*</url-pattern>
	</filter-mapping>
	
### 4. 配置易信接入:

	<filter>
		<filter-name>yixinGW</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
		<init-param>
			<param-name>targetFilterLifecycle</param-name>
			<param-value>true</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>yixinGW</filter-name>
		<url-pattern>/echat/*</url-pattern>
	</filter-mapping>

### 5. 实现自定义消息处理器:

MMT为您的消息处理提供一个模板类 `lemon.shared.message.processor.AbstractMsgProcessor`, 
你可以继承这个抽象类来实现自定义的消息处理机。

MMT也提供了简单的消息实现: `lemon.weixin.message.processor.SimpleWeiXinMsgProcessor`
和`lemon.yixin.message.processor.SimpleYiXinMsgProcessor`.

=====
祝你好运!