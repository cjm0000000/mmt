SET MODE MYSQL;

--------------------------- For System ------------------------------
--
-- 系统配置表
--
CREATE TABLE `system_config` (
  `group` varchar(255) NOT NULL DEFAULT '' COMMENT '配置组',
  `key` varchar(128) NOT NULL DEFAULT '' COMMENT '配置名称',
  `value` varchar(255) DEFAULT '' COMMENT '配置内容',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group`,`key`)
)COMMENT='系统配置表';

--
-- 登录日志表
--
CREATE TABLE `system_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `logintime` timestamp DEFAULT CURRENT_TIMESTAMP,
  `loginstatus` char(11) DEFAULT NULL COMMENT '登陆状态',
  `loginip` char(15) DEFAULT NULL COMMENT '登陆地IP',
  PRIMARY KEY (`id`),
  KEY `IDX_log_login_user` (`user_id`,`role_id`)
) COMMENT='登录日志表';

--
-- 用户表
--
CREATE TABLE `system_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户登录名',
  `password` char(128) NOT NULL DEFAULT '' COMMENT '用户密码',
  `xm` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `idcard` char(18) DEFAULT NULL COMMENT '身份证号码',
  `mphone` char(11) DEFAULT NULL COMMENT '手机号码',
  `islock` char(11) NOT NULL DEFAULT '' COMMENT '锁定状态',
  `bz` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '用户状态',
  PRIMARY KEY (`user_id`)
) COMMENT='用户表';

--
-- 角色表
--
CREATE TABLE `system_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '角色说明',
  `status` char(11) NOT NULL DEFAULT '' COMMENT '角色状态',
  `sort` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '排序号',
  `reloadable` char(11) NOT NULL DEFAULT '' COMMENT '在登录的时候是否重新生成菜单',
  PRIMARY KEY (`role_id`)
) COMMENT='角色表';

--
-- 用户角色表
--
CREATE TABLE `system_user_role` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  PRIMARY KEY (`user_id`)
) COMMENT='用户角色表';

--
-- 角色权限表
--
CREATE TABLE `system_role_menu` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu` (`role_id`)
) COMMENT='角色权限表';

--
-- 角色默认权限表
--
CREATE TABLE `system_role_menu_default` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu_default` (`role_id`)
) COMMENT='角色默认权限表';

--
-- 系统菜单表
--
CREATE TABLE `system_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menulevcod` char(1) DEFAULT NULL COMMENT '菜单等级',
  `supmenucode` int(10) NOT NULL DEFAULT '0' COMMENT '上级菜单编号',
  `menuurl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `menuico` varchar(60) DEFAULT NULL COMMENT '菜单风格',
  `sort` decimal(4,0) DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`menu_id`)
) COMMENT='系统菜单表';

--
-- 用户配置表
--
CREATE TABLE `system_user_config` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `key` varchar(128) DEFAULT '' COMMENT '配置名称',
  `value` varchar(255) DEFAULT '' COMMENT '配置内容',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP
) COMMENT='用户配置表';

----------------------------------------------------------  CUSTOMER   ----------------------------------------------------------
--
-- 客户信息表
--
CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `cust_name` varchar(255) NOT NULL DEFAULT '' COMMENT '客户名称',
  `memo` varchar(255) NOT NULL DEFAULT '' COMMENT '客户介绍',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  PRIMARY KEY (`cust_id`)
)  COMMENT='客户信息表';

--
-- 客户自定义菜单表
--
CREATE TABLE `customer_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单编号',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `name` char(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menulevcod` tinyint(1) NOT NULL DEFAULT '1' COMMENT '菜单等级',
  `supmenucode` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级菜单编号',
  `key` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单链接',
  `type` varchar(5) NOT NULL DEFAULT '' COMMENT '类型',
  `sort` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`menu_id`)
)  COMMENT='客户自定义菜单表';

--
-- 自定义菜单请求日志
--
CREATE TABLE `customer_menu_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` varchar(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `action` varchar(6) NOT NULL DEFAULT '' COMMENT '动作',
  `access_token` varchar(255) NOT NULL DEFAULT '' COMMENT 'ACCESS TOKEN',
  `msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '发送的消息',
  `result` varchar(255) NOT NULL DEFAULT '' COMMENT '请求结果',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)  COMMENT='自定义菜单请求日志';

--
-- 客户服务开通情况表
--
CREATE TABLE `customer_service` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(10) NOT NULL DEFAULT '' COMMENT '服务',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  `expire_time` int NOT NULL DEFAULT '0' COMMENT '服务到期时间（0表示永久有效）',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)  COMMENT='客户服务开通情况表';

----------------------------------------------------------  ACCESS   ----------------------------------------------------------
--
-- 接口接入日志
--
CREATE TABLE `access_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT 'OTHER' COMMENT '服务类型',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  `nonce` varchar(30) DEFAULT NULL COMMENT '接收到的nonce',
  `echostr` varchar(50) DEFAULT NULL COMMENT '接收到的echostr',
  `token` varchar(50) NOT NULL DEFAULT '' COMMENT '接入TOKEN',
  `timestamp_api` varchar(20) DEFAULT NULL COMMENT '接收到的timestamp',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='接口接入日志';

--
-- ACCESS TOKEN表
--
CREATE TABLE `access_token` (
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` varchar(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `access_token` varchar(255) NOT NULL DEFAULT '' COMMENT '有效的AccessToken',
  `expire_time` int(11) NOT NULL DEFAULT '0' COMMENT '有效期',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`,`service_type`)
) COMMENT='ACCESS TOKEN表';

--
-- ACCESS TOKEN请求日志
--
CREATE TABLE `access_token_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT 'APPID',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT 'SECRET',
  `grant_type` varchar(255) NOT NULL DEFAULT 'client_credential' COMMENT 'grant_type',
  `result` varchar(1024) NOT NULL DEFAULT '' COMMENT '请求结果',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='ACCESS TOKEN请求日志';

----------------------------------------------------------  CITY   ----------------------------------------------------------
--
-- 地区表
--
CREATE TABLE `city` (
  `citycode` char(9) NOT NULL COMMENT '地区代码',
  `city_name` char(10) NOT NULL DEFAULT '' COMMENT '地区名称',
  `province` char(10) NOT NULL DEFAULT '' COMMENT '省份',
  `city_alias` varchar(20) NOT NULL DEFAULT '' COMMENT '城市别名',
  PRIMARY KEY (`citycode`)
) COMMENT='地区表';

----------------------------------------------------------  多媒体管理   ----------------------------------------------------------
--
-- 多媒体表
--
CREATE TABLE `media` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `media_type` char(5) NOT NULL DEFAULT '' COMMENT '媒体类型',
  `media_size` int(11) NOT NULL DEFAULT '0' COMMENT '文件大小(KB)',
  `real_name` varchar(255) NOT NULL DEFAULT '' COMMENT '真实文件名',
  `display_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用于显示的文件名',
  `media_path` varchar(255) NOT NULL DEFAULT 'AVAILABLE' COMMENT '文件路径',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新',
  PRIMARY KEY (`id`)
) COMMENT='多媒体表';
--
-- 多媒体同步表
--
CREATE TABLE `media_sync` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `m_id` bigint(20) NOT NULL COMMENT '系统内MediaID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT 'OTHER' COMMENT '服务类型',
  `media_id` char(64) NOT NULL DEFAULT '' COMMENT '服务端media_id',
  `created_at` int(11) NOT NULL DEFAULT '0' COMMENT '服务端文件创建时间戳',
  `expire_time` int(11) NOT NULL DEFAULT '0' COMMENT '文件过期时间戳',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新',
  PRIMARY KEY (`id`)
) COMMENT='多媒体同步表';
--
-- 多媒体同步日志表
--
CREATE TABLE `media_sync_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `media_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '多媒体文件ID',
  `result` varchar(1024) NOT NULL DEFAULT '' COMMENT '请求结果',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) COMMENT='多媒体同步日志';
----------------------------------------------------------  FANS   ----------------------------------------------------------
--
-- 粉丝表
--
CREATE TABLE `fans` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `user_id` char(32) NOT NULL DEFAULT '' COMMENT '用户ID',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `status` char(11) NOT NULL DEFAULT 'AVAILABLE' COMMENT '状态',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新',
  PRIMARY KEY (`id`)
)   COMMENT='粉丝表';

--
-- 粉丝订阅/退订日志表
--
CREATE TABLE `fans_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `action` varchar(11) NOT NULL DEFAULT '' COMMENT '动作',
  `service_type` char(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `user_id` char(32) NOT NULL DEFAULT '' COMMENT '用户ID',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
  PRIMARY KEY (`id`)
) COMMENT='粉丝订阅/退订日志表';

----------------------------------------------------------  MESSAGE   ----------------------------------------------------------
--
-- 易信语音消息接收表
--
CREATE TABLE `msg_recv_audio_yixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '音频地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  PRIMARY KEY (`detail_id`)
) COMMENT='易信语音消息接收表';

--
-- 消息接收汇总表
--
CREATE TABLE `msg_recv_detail` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` char(8) NOT NULL DEFAULT 'OTHER' COMMENT '服务类型',
  `toUserName` varchar(255) NOT NULL DEFAULT '' COMMENT '接受者ID',
  `fromUserName` varchar(255) NOT NULL DEFAULT '' COMMENT '发送者ID',
  `createTime` int(11) NOT NULL DEFAULT '0' COMMENT '服务器创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `msgId` bigint(20) DEFAULT '0' COMMENT '信息ID',
  `timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '接收时间',
  PRIMARY KEY (`id`)
) COMMENT='消息接收汇总表';

--
-- 事件消息接收表
--
CREATE TABLE `msg_recv_event` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `eventType` varchar(11) NOT NULL DEFAULT '' COMMENT '事件类型',
  `eventKey` varchar(255) NOT NULL DEFAULT '' COMMENT '事件KEY',
  `ticket` varchar(255) NOT NULL DEFAULT '' COMMENT '二维码的ticket',
  `latitude` varchar(20) NOT NULL DEFAULT '' COMMENT '地理位置纬度',
  `longitude` varchar(20) NOT NULL DEFAULT '' COMMENT '地理位置经度',
  `precision` varchar(20) NOT NULL DEFAULT '' COMMENT '地理位置精度',
  PRIMARY KEY (`detail_id`)
)  COMMENT='事件消息接收表';

--
-- 图片消息接收表
--
CREATE TABLE `msg_recv_image` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `picUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片链接',
  `mediaId` char(64) DEFAULT '',
  PRIMARY KEY (`detail_id`)
) COMMENT='图片消息接收表';

--
-- 链接消息接收表
--
CREATE TABLE `msg_recv_link` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '链接地址',
  PRIMARY KEY (`detail_id`)
) COMMENT='链接消息接收表';

--
-- 地理位置消息接收表
--
CREATE TABLE `msg_recv_location` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `location_X` varchar(15) NOT NULL DEFAULT '0' COMMENT 'X坐标',
  `location_Y` varchar(15) NOT NULL DEFAULT '0' COMMENT 'Y坐标',
  `scale` int(11) NOT NULL DEFAULT '0' COMMENT '放大倍数',
  `label` varchar(255) DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`detail_id`)
) COMMENT='地理位置消息接收表';

--
-- 消息接收日志
--
CREATE TABLE `msg_recv_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` varchar(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `msg` varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
  `timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) COMMENT='消息接收日志';

--
-- 易信音乐消息接收表
--
CREATE TABLE `msg_recv_music_yixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '封面图片地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  `desc` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情',
  PRIMARY KEY (`detail_id`)
) COMMENT='易信音乐消息接收表';

--
-- 文本消息接收表
--
CREATE TABLE `msg_recv_text` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) COMMENT='文本消息接收表';

--
-- 微信视频消息接收表
--
CREATE TABLE `msg_recv_video_weixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `mediaId` char(64) NOT NULL,
  `thumbMediaId` varchar(255) NOT NULL,
  PRIMARY KEY (`detail_id`)
) COMMENT='微信视频消息接收表';

--
-- 易信视频消息接收表
--
CREATE TABLE `msg_recv_video_yixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '音频地址',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `mimeType` char(10) NOT NULL DEFAULT '' COMMENT '类型',
  PRIMARY KEY (`detail_id`)
) COMMENT='易信视频消息接收表';

--
-- 微信语音消息接收表
--
CREATE TABLE `msg_recv_voice_weixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `mediaId` char(64) NOT NULL,
  `format` varchar(10) NOT NULL DEFAULT '' COMMENT '格式',
  `recognition` varchar(255) default '' COMMENT '语音识别结果',
  PRIMARY KEY (`detail_id`)
) COMMENT='微信语音消息接收表';

--
-- 一级消息库
--
CREATE TABLE `msg_repo_l1` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) COMMENT='一级消息库';

--
-- 二级消息库
--
CREATE TABLE `msg_repo_l2` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) COMMENT='二级消息库';

--
-- 通用消息库
--
CREATE TABLE `msg_repo_l3` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `key` varchar(100) NOT NULL DEFAULT '' COMMENT '关键字',
  `value` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  PRIMARY KEY (`id`)
) COMMENT='通用消息库';

--
-- 消息发送汇总表
--
CREATE TABLE `msg_send_detail` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` varchar(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `toUserName` varchar(255) NOT NULL DEFAULT '' COMMENT '接受者ID',
  `fromUserName` varchar(255) NOT NULL DEFAULT '' COMMENT '发送者ID',
  `createTime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `msgType` varchar(10) NOT NULL DEFAULT '' COMMENT '信息类型',
  `timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发送时间',
  PRIMARY KEY (`id`)
) COMMENT='消息发送汇总表';

--
-- 消息发送日志
--
CREATE TABLE `msg_send_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `service_type` varchar(8) NOT NULL DEFAULT '' COMMENT '服务类型',
  `msg` varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
  `timestamp` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) COMMENT='消息发送日志';

--
-- 微信音乐消息发送表
--
CREATE TABLE `msg_send_music_weixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `musicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '音乐地址',
  `hqMusicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '高清音乐地址',
  PRIMARY KEY (`detail_id`)
) COMMENT='微信音乐消息发送表';

--
-- 易信音乐消息发送表
--
CREATE TABLE `msg_send_music_yixin` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `musicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '音乐地址',
  `hqMusicUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '高清音乐地址',
  PRIMARY KEY (`detail_id`)
) COMMENT='易信音乐消息发送表';

--
-- 图文消息发送表
--
CREATE TABLE `msg_send_news` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `articleCount` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '文章数量',
  PRIMARY KEY (`detail_id`)
) COMMENT='图文消息发送表';

--
-- 图文消息发送表——文章
--
CREATE TABLE `msg_send_news_article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应news表ID',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '详情',
  `picUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片地址',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '文章地址',
  PRIMARY KEY (`id`)
) COMMENT='图文消息发送表——文章';

--
-- 文本消息发送表
--
CREATE TABLE `msg_send_text` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应发送detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '文本内容',
  PRIMARY KEY (`detail_id`)
) COMMENT='文本消息发送表';

--
-- KeyEvent接收表
--
CREATE TABLE `event_recv_key` (
  `detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应detail表ID',
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户编号',
  `eventKey` varchar(255) NOT NULL DEFAULT '' COMMENT '事件KEY',
  PRIMARY KEY (`detail_id`)
) COMMENT='KeyEvent接收表';

----------------------------------------------------------  CONFIG   ----------------------------------------------------------
--
-- 微信配置信息表
--
CREATE TABLE `weixin_config` (
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户ID',
  `wx_account` varchar(50) NOT NULL DEFAULT '' COMMENT '微信号',
  `account_type` char(2) NOT NULL DEFAULT 'DY' COMMENT '帐户类型',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '微信接入TOKEN',
  `subscribe_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '订阅事件需要发送的消息',
  `welcome_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '欢迎信息',
  `biz_class` varchar(255) NOT NULL DEFAULT '' COMMENT '业务代码实现类',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证密钥',
  `api_url` char(40) NOT NULL DEFAULT '' COMMENT '客户微信API的URL(UNIQUE)',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`)
) COMMENT='微信配置信息表';

--
-- 易信配置信息表
--
CREATE TABLE `yixin_config` (
  `cust_id` int(11) NOT NULL DEFAULT '0' COMMENT '客户ID',
  `yx_account` varchar(50) NOT NULL DEFAULT '' COMMENT '易信号',
  `token` varchar(255) NOT NULL DEFAULT '' COMMENT '易信接入TOKEN',
  `subscribe_msg` varchar(1024) DEFAULT '' COMMENT '订阅事件需要发送的消息',
  `welcome_msg` varchar(1024) NOT NULL DEFAULT '' COMMENT '欢迎信息',
  `biz_class` varchar(255) NOT NULL DEFAULT '' COMMENT '业务代码实现类',
  `appid` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证',
  `secret` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方用户唯一凭证密钥',
  `api_url` char(40) NOT NULL DEFAULT '' COMMENT '客户易信API的URL(UNIQUE)',
  `timestamp` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cust_id`)
) COMMENT='易信配置信息表';