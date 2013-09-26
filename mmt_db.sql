########################### For System #####################
#
# 登录日志表
#
CREATE TABLE `system_log_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `logintime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `loginstatus` char(11) DEFAULT NULL COMMENT '登陆状态',
  `loginip` char(15) DEFAULT NULL COMMENT '登陆地IP',
  PRIMARY KEY (`id`),
  KEY `IDX_log_login_user` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

#
# 用户表
#
CREATE TABLE `system_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户登录名',
  `password` char(128) NOT NULL DEFAULT '' COMMENT '用户密码',
  `xm` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `idcard` char(18) DEFAULT NULL COMMENT '身份证号码',
  `mphone` char(11) DEFAULT NULL COMMENT '手机号码',
  `islock` char(11) NOT NULL DEFAULT '' COMMENT '锁定状态',
  `bz` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '用户状态',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# 角色表
#
CREATE TABLE `system_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '角色说明',
  `status` char(11) NOT NULL DEFAULT '' COMMENT '角色状态',
  `sort` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '排序号',
  `reloadable` char(11) NOT NULL DEFAULT '' COMMENT '在登录的时候是否重新生成菜单',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

#
# 用户角色表
#
CREATE TABLE `system_user_role` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

#
# 角色权限表
#
CREATE TABLE `system_role_menu` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#
# 角色默认权限表
#
CREATE TABLE `system_role_menu_default` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu_default` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色默认权限表';

#
# 系统菜单表
#
CREATE TABLE `system_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menulevcod` char(1) DEFAULT NULL COMMENT '菜单等级',
  `supmenucode` int(10) NOT NULL DEFAULT '0' COMMENT '上级菜单编号',
  `menuurl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `menuico` varchar(60) DEFAULT NULL COMMENT '菜单风格',
  `sort` decimal(4,0) DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

#
# 用户配置表
#
CREATE TABLE `system_user_config` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `key` varchar(128) DEFAULT '' COMMENT '配置名称',
  `value` varchar(255) DEFAULT '' COMMENT '配置内容',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户配置表';

#
# 客户信息表
#
CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `cust_name` varchar(255) NOT NULL DEFAULT '' COMMENT '客户名称',
  `memo` varchar(255) NOT NULL DEFAULT '' COMMENT '客户介绍',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='客户信息表';

#
# 客户服务开通情况表
#
CREATE TABLE `customer_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service` char(10) NOT NULL DEFAULT '' COMMENT '服务',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  `expire_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '服务到期时间（0000-00-00表示永久有效）',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='客户服务开通情况表';

#
# 第一层业务处理库，严格匹配KEY
#
CREATE TABLE `mmt_biz_l1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='第一层业务处理库，严格匹配KEY';

#
# 第二层业务处理库，模糊匹配KEY
#
CREATE TABLE `mmt_biz_l2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='第二层业务处理库，模糊匹配KEY';

#
# 通用业务处理库，模糊匹配KEY
#
CREATE TABLE `mmt_biz_l3` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='通用业务处理库，模糊匹配KEY';

#
# 地区表
#
CREATE TABLE `mmt_city` (
  `citycode` char(9) NOT NULL COMMENT '地区代码',
  `city_name` char(10) NOT NULL DEFAULT '' COMMENT '地区名称',
  `province` char(10) NOT NULL DEFAULT '' COMMENT '省份',
  `city_alias` varchar(20) NOT NULL DEFAULT '' COMMENT '城市别名',
  PRIMARY KEY (`citycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区表'

########################### For WeiXin #####################
#
# 微信配置表
#
CREATE TABLE `weixin_config` (
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户ID',
  `wx_account` varchar(50) NOT NULL DEFAULT '' COMMENT '微信号',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '微信接入TOKEN',
  `subscribe_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '订阅事件需要发送的消息',
  `welcome_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '欢迎信息',
  `biz_class` varchar(255) NOT NULL DEFAULT '' COMMENT '业务代码实现类',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证密钥',
  `api_url` char(40) NOT NULL DEFAULT '' COMMENT '客户微信API的URL(UNIQUE)',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信配置信息表'

#
# 微信粉丝表
#
CREATE TABLE `weixin_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `wxid` char(32) NOT NULL DEFAULT '' COMMENT '微信ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `nick_name` varchar(50) DEFAULT '' COMMENT '昵称',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信粉丝表';

#
# 微信消息日志表
#
CREATE TABLE `weixin_log_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `msgType` varchar(8) NOT NULL DEFAULT '' COMMENT '消息类型',
  `msg` text NOT NULL COMMENT '消息内容',
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=509 DEFAULT CHARSET=utf8 COMMENT='微信消息日志';

#
# 微信接口接入日志
#
CREATE TABLE `weixin_log_siteaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  `nonce` varchar(30) DEFAULT NULL COMMENT '接收到的nonce',
  `echostr` varchar(50) DEFAULT NULL COMMENT '接收到的echostr',
  `token` varchar(50) NOT NULL DEFAULT '' COMMENT '微信接入TOKEN',
  `timestamp` varchar(20) DEFAULT NULL COMMENT '接收到的timestamp',
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8 COMMENT='微信接口接入日志';

#
# 微信订阅日志表
#
CREATE TABLE `weixin_log_subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `wxid` char(32) NOT NULL DEFAULT '' COMMENT '微信ID',
  `subscribe_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信订阅日志表';

#
# 微信退订日志表
#
CREATE TABLE `weixin_log_unsubscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `wxid` char(32) NOT NULL DEFAULT '' COMMENT '微信ID',
  `unsubscribe_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取消订阅时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信退订日志表';

#
# 微信接收消息汇总表
#
CREATE TABLE `weixin_recvmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `toUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '接受者微信ID',
  `fromUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '发送者微信ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '微信创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `msgId` bigint(20) DEFAULT NULL COMMENT '信息ID',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '接收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='微信消息接收汇总表';

#
# 微信事件消息接收表
#
CREATE TABLE `weixin_recvmsg_event` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `eventType` varchar(11) NOT NULL DEFAULT '' COMMENT '事件类型',
  `eventKey` char(64) NOT NULL DEFAULT '' COMMENT '事件KEY',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信事件消息接收表';

#
# 微信图片消息接收表
#
CREATE TABLE `weixin_recvmsg_image` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `picUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片链接',
  `mediaId` char(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图片消息接收表';

#
# 微信链接消息接收表
#
CREATE TABLE `weixin_recvmsg_link` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `description` varchar(1024) DEFAULT '' COMMENT '详情',
  `url` varchar(255) DEFAULT '' COMMENT '链接地址',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信链接消息接收表';

#
# 微信地理位置消息接收表
#
CREATE TABLE `weixin_recvmsg_location` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `location_X` varchar(15) NOT NULL DEFAULT '0' COMMENT 'X坐标',
  `location_Y` varchar(15) NOT NULL DEFAULT '0' COMMENT 'Y坐标',
  `scale` int(11) NOT NULL DEFAULT '0' COMMENT '放大倍数',
  `label` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信地理位置消息接收表';

#
# 微信文本消息接收表
#
CREATE TABLE `weixin_recvmsg_text` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `content` varchar(2048) DEFAULT '' COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信文本消息接收表';

#
# 微信视频消息接收表
#
CREATE TABLE `weixin_recvmsg_video` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `mediaId` char(64) NOT NULL,
  `thumbMediaId` varchar(255) NOT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信视频消息接收表';

#
# 微信语音消息接收表
#
CREATE TABLE `weixin_recvmsg_voice` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `mediaId` char(64) NOT NULL,
  `format` varchar(10) NOT NULL DEFAULT '' COMMENT '格式',
  `recognition` varchar(255) NOT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信语音消息接收表';

#
# 微信消息发送汇总表
#
CREATE TABLE `weixin_sendmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `toUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '接受者微信ID',
  `fromUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '发送者微信ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信消息发送汇总表';

#
# 微信音乐消息发送表
#
CREATE TABLE `weixin_sendmsg_music` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `musicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '音乐地址',
  `hqMusicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '高清音乐地址',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信音乐消息发送表';

#
# 微信图文消息发送表
#
CREATE TABLE `weixin_sendmsg_news` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `articleCount` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '文章数量',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文消息发送表';

#
# 微信图文消息发送表——文章
#
CREATE TABLE `weixin_sendmsg_news_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应news表ID',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情',
  `picUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '文章地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文消息发送表——文章';

#
# 微信文本消息发送表
#
CREATE TABLE `weixin_sendmsg_text` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `content` varchar(2048) NOT NULL DEFAULT '' COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信文本消息发送表';

########################### For YiXin #####################
#
# 易信配置表
#
CREATE TABLE `yixin_config` (
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户ID',
  `yx_account` varchar(50) NOT NULL DEFAULT '' COMMENT '易信号',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '易信接入TOKEN',
  `subscribe_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '订阅事件需要发送的消息',
  `welcome_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '欢迎信息',
  `biz_class` varchar(255) NOT NULL DEFAULT '' COMMENT '业务代码实现类',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证密钥',
  `api_url` char(40) NOT NULL DEFAULT '' COMMENT '客户易信API的URL(UNIQUE)',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信配置信息表'

#
# 易信粉丝表
#
CREATE TABLE `yixin_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `yxid` char(32) NOT NULL DEFAULT '' COMMENT '易信ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `nick_name` varchar(50) DEFAULT '' COMMENT '昵称',
  `status` char(11) NOT NULL DEFAULT '' COMMENT '状态（1：订阅：0：取消订阅）',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信粉丝表';

#
# 易信消息日志表
#
CREATE TABLE `yixin_log_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `msgType` varchar(8) NOT NULL DEFAULT '' COMMENT '消息类型',
  `msg` varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='易信消息日志';

#
# 易信接口接入日志
#
CREATE TABLE `yixin_log_siteaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  `nonce` varchar(30) DEFAULT NULL COMMENT '接收到的nonce',
  `echostr` varchar(50) DEFAULT NULL COMMENT '接收到的echostr',
  `token` varchar(50) NOT NULL DEFAULT '' COMMENT '易信接入TOKEN',
  `timestamp` varchar(20) DEFAULT NULL COMMENT '接收到的timestamp',
  `log_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='易信接口接入日志';

#
# 易信订阅日志表
#
CREATE TABLE `yixin_log_subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `yxid` char(32) NOT NULL DEFAULT '' COMMENT '易信ID',
  `subscribe_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信订阅日志表';

#
# 易信退订日志表
#
CREATE TABLE `yixin_log_unsubscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `yxid` char(32) NOT NULL DEFAULT '' COMMENT '易信ID',
  `unsubscribe_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取消订阅时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信退订日志表';

#
# 易信接收消息汇总表
#
CREATE TABLE `yixin_recvmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `toUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '接受者易信ID',
  `fromUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '发送者易信ID',
  `createTime` int(11) NOT NULL DEFAULT '0' COMMENT '易信创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `msgId` bigint(20) DEFAULT NULL COMMENT '信息ID',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '接收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='易信消息接收汇总表';

#
# 易信事件消息接收表
#
CREATE TABLE `yixin_recvmsg_event` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `eventType` varchar(11) NOT NULL DEFAULT '' COMMENT '事件类型',
  `eventKey` char(64) NOT NULL DEFAULT '' COMMENT '事件KEY',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信事件消息接收表';

#
# 易信图片消息接收表
#
CREATE TABLE `yixin_recvmsg_image` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `picUrl` varchar(255) DEFAULT '' COMMENT '图片链接',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信图片消息接收表';

#
# 易信链接消息接收表
#
CREATE TABLE `yixin_recvmsg_link` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `title` varchar(250) DEFAULT '' COMMENT '标题',
  `description` varchar(1024) DEFAULT '' COMMENT '详情',
  `url` varchar(255) DEFAULT '' COMMENT '链接地址',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信链接消息接收表';

#
# 易信地理位置消息接收表
#
CREATE TABLE `yixin_recvmsg_location` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `location_X` varchar(15) NOT NULL DEFAULT '0' COMMENT 'X坐标',
  `location_Y` varchar(15) NOT NULL DEFAULT '0' COMMENT 'Y坐标',
  `scale` int(11) NOT NULL DEFAULT '0' COMMENT '放大倍数',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信地理位置消息接收表';

#
# 易信文本消息接收表
#
CREATE TABLE `yixin_recvmsg_text` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信文本消息接收表';

#
# 易信视频消息接收表
#
CREATE TABLE `yixin_recvmsg_video` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '音频地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信视频消息接收表';

#
# 易信音乐消息接收表
#
CREATE TABLE `yixin_recvmsg_music` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '封面图片地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  `desc` varchar(2048) NOT NULL DEFAULT '' COMMENT '详情',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信音乐消息接收表';

#
# 易信语音消息接收表
#
CREATE TABLE `yixin_recvmsg_audio` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '音频地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信语音消息接收表'

#
# 易信消息发送汇总表
#
CREATE TABLE `yixin_sendmsg_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `toUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '接受者易信ID',
  `fromUserName` varchar(100) NOT NULL DEFAULT '' COMMENT '发送者易信ID',
  `createTime` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信消息发送汇总表';

#
# 易信音乐消息发送表
#
CREATE TABLE `yixin_sendmsg_music` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `musicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '音乐地址',
  `hqMusicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '高清音乐地址',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信音乐消息发送表';

#
# 易信图文消息发送表
#
CREATE TABLE `yixin_sendmsg_news` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `articleCount` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '文章数量',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信图文消息发送表';

#
# 易信图文消息发送表——文章
#
CREATE TABLE `yixin_sendmsg_news_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应news表ID',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情',
  `picUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '文章地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信图文消息发送表——文章';

#
# 易信文本消息发送表
#
CREATE TABLE `yixin_sendmsg_text` (
  `detail_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `content` varchar(1024) NOT NULL COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='易信文本消息发送表';