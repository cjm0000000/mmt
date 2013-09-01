USE `mmt_db`;

#
# 客户信息表
#

CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(255) NOT NULL DEFAULT '',
  `memo` varchar(255) NOT NULL DEFAULT '',
  `status` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

#
# 微信配置表
#

CREATE TABLE `weixin_config` (
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `wx_account` varchar(50) NOT NULL DEFAULT '',
  `token` varchar(255) NOT NULL DEFAULT '',
  `subscribe_msg` text COMMENT '订阅事件需要发送的消息',
  `unsubscribe_msg` text COMMENT '取消订阅事件需要发送的消息',
  `biz_class` varchar(255) NOT NULL DEFAULT '' COMMENT '业务代码实现类',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证密钥',
  `api_url` char(40) NOT NULL DEFAULT '' COMMENT '客户微信API的URL(UNIQUE)',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信配置信息表';

#
# 微信消息日志表
#

CREATE TABLE `weixin_log_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `msgType` varchar(8) DEFAULT NULL,
  `msg` text NOT NULL,
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=476 DEFAULT CHARSET=utf8 COMMENT='微信消息日志';

#
# 微信接入日志
#

CREATE TABLE `weixin_log_siteaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  `nonce` varchar(30) DEFAULT NULL,
  `echostr` varchar(50) DEFAULT NULL,
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(50) NOT NULL DEFAULT '',
  `timestamp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='微信接入日志';

#
# 微信接收消息汇总表
#

CREATE TABLE `weixin_recvmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `toUserName` varchar(100) NOT NULL DEFAULT '',
  `fromUserName` varchar(100) NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `msgType` varchar(10) NOT NULL DEFAULT '',
  `msgId` bigint(20) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='微信接收消息汇总表';

#
# 微信事件消息表——接收
#

CREATE TABLE `weixin_recvmsg_event` (
  `detail_id` int(11) NOT NULL,
  `eventType` varchar(11) NOT NULL DEFAULT '',
  `eventKey` char(64) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信事件消息表——接收';

#
# 微信图片消息——接收
#

CREATE TABLE `weixin_recvmsg_image` (
  `detail_id` int(11) NOT NULL,
  `picUrl` varchar(100) DEFAULT NULL,
  `mediaId` char(64) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图片消息表';

#
# 微信链接消息表——接收
#

CREATE TABLE `weixin_recvmsg_link` (
  `detail_id` int(11) NOT NULL,
  `title` varchar(250) DEFAULT '',
  `description` text,
  `url` varchar(100) DEFAULT '',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信链接消息表';

#
# 微信地理位置消息表——接收
#

CREATE TABLE `weixin_recvmsg_location` (
  `detail_id` int(11) NOT NULL,
  `location_X` varchar(15) NOT NULL DEFAULT '0',
  `location_Y` varchar(15) NOT NULL DEFAULT '0',
  `scale` int(11) NOT NULL,
  `label` varchar(255) NOT NULL DEFAULT '',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信地理位置消息表';

#
# 微信文本消息表——接收
#

CREATE TABLE `weixin_recvmsg_text` (
  `detail_id` int(11) NOT NULL,
  `content` text,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信文本消息表';

#
# 微信消息发送汇总表
#

CREATE TABLE `weixin_sendmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `toUserName` varchar(100) NOT NULL DEFAULT '',
  `fromUserName` varchar(100) NOT NULL DEFAULT '',
  `createTime` bigint(20) NOT NULL DEFAULT '0',
  `msgType` varchar(10) NOT NULL DEFAULT '',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信消息发送汇总表';