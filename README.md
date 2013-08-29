# MMT is a smart message robot.

WeiXin feature is Integrated, You can easy use the it.

## User guide

### 1. Build from code

	$ git clone https://github.com/cjm0000000/mmt.git mmt
	$ cd mmt
	$ mvn install
	
### 2. Create tables
	
Here is MySQL code, you can see full SQL at [SQL CODE] (https://github.com/cjm0000000/mmt/blob/master/mmt-web/src/main/resources/mmt_db.sql)
	
	# Table weixin_config
	CREATE TABLE `weixin_config` (
	  `cust_id` int(11) NOT NULL AUTO_INCREMENT,
	  `wx_account` varchar(50) NOT NULL DEFAULT '',
	  `token` varchar(255) NOT NULL DEFAULT '',
	  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  `subscribe_msg` text COMMENT '订阅事件需要发送的消息',
	  `unsubscribe_msg` text COMMENT '取消订阅事件需要发送的消息',
	  PRIMARY KEY (`cust_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='微信配置信息表'
	
	# Table customer
	CREATE TABLE `customer` (
	  `cust_id` int(11) NOT NULL AUTO_INCREMENT,
	  `cust_name` varchar(255) NOT NULL DEFAULT '',
	  `memo` varchar(255) NOT NULL DEFAULT '',
	  `status` char(1) NOT NULL DEFAULT '1',
	  PRIMARY KEY (`cust_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='客户信息表'
	
	# Table weixin_log_siteaccess
	CREATE TABLE `weixin_log_siteaccess` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `cust_id` int(11) NOT NULL DEFAULT '0',
	  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
	  `timestamp` varchar(20) DEFAULT NULL,
	  `nonce` varchar(30) DEFAULT NULL,
	  `echostr` varchar(50) DEFAULT NULL,
	  `token`	varchar(50) NOT NULL,
	  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='微信接入日志';
	
	# Table weixin_log_msg
	CREATE TABLE `weixin_log_msg` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `cust_id` int(11) NOT NULL DEFAULT '0',
	  `msgType` varchar(8) DEFAULT NULL,
	  `msg` text,
	  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信信息日志';

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

You can implement your business by extends `lemon.weixin.biz.customer.CustBasicMsgBiz`,
this class supply an easy subscribe implement.

=====
Good luck for you!