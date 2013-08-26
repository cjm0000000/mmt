DROP DATABASE IF EXISTS `mmt_db`;
CREATE DATABASE `mmt_db` 
USE `mmt_db`;

#
# Source for table log_login
#

DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` char(10) NOT NULL COMMENT '部门ID',
  `logintime` datetime DEFAULT NULL COMMENT '登录时间',
  `loginstatus` char(1) DEFAULT NULL COMMENT '登陆状态',
  `loginip` varchar(15) DEFAULT NULL COMMENT '登陆地IP',
  PRIMARY KEY (`id`),
  KEY `IDX_log_login_user` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

#
# Source for table log_proc
#

DROP TABLE IF EXISTS `log_proc`;
CREATE TABLE `log_proc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `proc_name` varchar(60) DEFAULT NULL COMMENT '过程名称',
  `exec_time` datetime DEFAULT NULL COMMENT '执行时间',
  `param` varchar(255) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储过程执行日志';

#
# Source for table log_pwd_modify
#

DROP TABLE IF EXISTS `log_pwd_modify`;
CREATE TABLE `log_pwd_modify` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `password` varchar(200) NOT NULL DEFAULT '' COMMENT '用户密码',
  `modify_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `modify_ip` char(15) NOT NULL DEFAULT '' COMMENT '连接IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户密码修改记录表';

#
# Source for table system_dept
#

DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept` (
  `dept_code` char(10) NOT NULL DEFAULT '' COMMENT '部门编号',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `deptlevcod` char(1) DEFAULT NULL COMMENT '部门等级',
  `supdeptcod` char(10) DEFAULT NULL COMMENT '上级部门编号',
  `sort` decimal(5,0) DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`dept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

#
# Source for table system_menu
#

DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `menu_code` char(10) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `menu_name` char(50) DEFAULT NULL COMMENT '菜单名称',
  `menulevcod` char(1) DEFAULT NULL COMMENT '菜单等级',
  `supmenucode` char(10) DEFAULT NULL COMMENT '上级菜单编号',
  `menuurl` varchar(100) DEFAULT NULL COMMENT '菜单链接',
  `menuico` varchar(60) DEFAULT NULL COMMENT '菜单风格',
  `sort` decimal(4,0) DEFAULT '0' COMMENT '排序号',
  `iconCls` varchar(20) DEFAULT '' COMMENT '功能样式',
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

#
# Source for table system_role
#

DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `zt` char(1) NOT NULL DEFAULT '1' COMMENT '角色状态（1：有效；0：无效）',
  `role_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '角色说明',
  `sort` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '排序号',
  `reload` char(1) NOT NULL DEFAULT '1' COMMENT '在登录的时候是否重新生成菜单(1：是；0：否)',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10014 DEFAULT CHARSET=utf8 COMMENT='角色表';

#
# Source for table system_role_menu
#

DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` char(10) NOT NULL DEFAULT '' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#
# Source for table system_role_menu_default
#

DROP TABLE IF EXISTS `system_role_menu_default`;
CREATE TABLE `system_role_menu_default` (
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_code` char(10) NOT NULL DEFAULT '' COMMENT '菜单编号',
  KEY `IDX_sys_role_menu_default` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色默认权限';

#
# Dumping data for table system_role_menu_default
#
LOCK TABLES `system_role_menu_default` WRITE;
/*!40000 ALTER TABLE `system_role_menu_default` DISABLE KEYS */;

INSERT INTO `system_role_menu_default` VALUES (1,'OA00010000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00010001');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00030000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00030001');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00030002');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00030003');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00040000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00040001');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00040002');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00040003');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00050000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00050002');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00060000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00060001');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00060002');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00060003');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00060004');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00070000');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00070001');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00070002');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00070003');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00070004');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00100009');
INSERT INTO `system_role_menu_default` VALUES (1,'OA00100011');
/*!40000 ALTER TABLE `system_role_menu_default` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table system_user
#

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户登录名',
  `password` char(128) NOT NULL DEFAULT '' COMMENT '用户密码',
  `xm` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `sfzh` char(18) DEFAULT NULL COMMENT '身份证号码',
  `sjhm` char(11) DEFAULT NULL COMMENT '手机号码',
  `lastlogintime` datetime DEFAULT NULL COMMENT '最近登录时间',
  `islock` char(1) NOT NULL DEFAULT '0' COMMENT '锁定状态（0：正常；1：锁定）',
  `bz` varchar(200) DEFAULT NULL COMMENT '备注',
  `lastloginip` char(15) DEFAULT NULL COMMENT '最近登录IP',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '用户状态（1：有效；0：删除）',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Dumping data for table system_user
#
LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
INSERT INTO `system_user` VALUES (37,'admin','7744ECF0A5500EACF229B022A0F41081','系统管理员','111111111111111111','12345678111','2013-06-02 23:14:52','0','','0:0:0:0:0:0:0:1','1');
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table system_user_config
#

DROP TABLE IF EXISTS `system_user_config`;
CREATE TABLE `system_user_config` (
  `user_id` int(11) NOT NULL COMMENT '部门编号',
  `key` varchar(128) DEFAULT NULL COMMENT '配置名称',
  `value` varchar(256) DEFAULT NULL COMMENT '配置内容',
  `create_time` datetime DEFAULT NULL COMMENT '新增日期',
  `lastmodify_time` datetime DEFAULT NULL COMMENT '最近修改日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户配置表';

#
# Dumping data for table system_user_config
#
LOCK TABLES `system_user_config` WRITE;
/*!40000 ALTER TABLE `system_user_config` DISABLE KEYS */;

INSERT INTO `system_user_config` VALUES (37,'EncryptKey','uziV2+-1FkVt)gkfuBsFsR3UgWvagl$yLRFfPbdw_)-x%9-F-W3qLg5umr/DNZh)oWfnIwl18t/BsJ#%PEFJ(GGyOW/atOWcSWlNym6yiomyd^4YQ!HjP=15.)IpYr(0UoTu_Y`UxoGHf)spiDEiCkQG6NW=P20nj/~ZyFPU6W8.vcY=7qeNuYB^G!H$aIzlQaZgrCSI8~)MbNqPP@@Yi1UI4eh7sRn`^5e=aqqiHnXVr%Ur=5@3&Z_.DmfH#J&u','2013-06-02 22:16:15','2013-06-02 22:16:15');
/*!40000 ALTER TABLE `system_user_config` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table system_user_role
#

DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `dept_id` char(10) NOT NULL DEFAULT '' COMMENT '部门编号',
  PRIMARY KEY (`id`),
  KEY `IDX_sys_user_role` (`user_id`,`role_id`,`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

#
# Dumping data for table system_user_role
#
LOCK TABLES `system_user_role` WRITE;
/*!40000 ALTER TABLE `system_user_role` DISABLE KEYS */;
INSERT INTO `system_user_role` VALUES (30,37,9999,'0577000001');
/*!40000 ALTER TABLE `system_user_role` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table weixin_log_msg
#

DROP TABLE IF EXISTS `weixin_log_msg`;
CREATE TABLE `weixin_log_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `msgType` varchar(8) DEFAULT NULL,
  `msg` text,
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信信息日志';

#
# Source for table weixin_log_siteaccess
#

DROP TABLE IF EXISTS `weixin_log_siteaccess`;
CREATE TABLE `weixin_log_siteaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) NOT NULL DEFAULT '0',
  `signature` varchar(255) NOT NULL DEFAULT '' COMMENT '签名',
  `timestamp` varchar(20) DEFAULT NULL,
  `nonce` varchar(30) DEFAULT NULL,
  `echostr` varchar(50) DEFAULT NULL,
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='微信接入日志';

#
# Source for procedure system_createMenuCode
#

DROP PROCEDURE IF EXISTS `system_createMenuCode`;
CREATE DEFINER=`hippuser`@`%` PROCEDURE `system_createMenuCode`(out newmenucode char(10))
    COMMENT '生成菜单编号'
begin
     declare maxcode char(10);     
     declare tmp char(8);     
     insert into log_proc(proc_name,exec_time,param)     
            select 'system_createMenuCode',now(),concat('newmenucode=',newmenucode);
     select max(menu_code) from system_menu into maxcode;     
     select cast(substring(maxcode,3,8)+1 as char(8)) into tmp;     
     while (length(tmp)<8)  do  
     begin     
               select concat('0',tmp) into tmp;
     end;     
     end while;     
     select concat(substring(maxcode,1,2),tmp) into newmenucode;
end;


#
# Source for procedure system_createDeptCode
#

DROP PROCEDURE IF EXISTS `system_createDeptCode`;
CREATE DEFINER=`hippuser`@`%` PROCEDURE `system_createDeptCode`(out newmenucode char(10))
    COMMENT '生成部门ID'
begin
     declare maxcode char(10);     
     declare tmp char(6);     
     insert into log_proc(proc_name,exec_time,param)     
            select 'system_createDeptCode',now(),concat('newmenucode=',newmenucode);
     select max(dept_code) from system_dept into maxcode;     
     select cast(substring(maxcode,5,6)+1  as char(6)) into tmp;     
     while (length(tmp)<6)  do     
               select concat('0',tmp) into tmp;   
     end while;  
     select concat(substring(maxcode,1,4),tmp) into newmenucode;
end;
