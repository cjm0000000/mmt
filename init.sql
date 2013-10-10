#菜单初始化
INSERT INTO `system_menu` (`menu_id`,`menu_name`,`menulevcod`,`supmenucode`,`menuurl`,`menuico`,`sort`) 
VALUES (1,'MMT消息服务平台','1',0,' ',' ',0),(2,'系统管理','2',1,'system','glyphicon glyphicon-cog',1),
(3,'接口管理','2',1,'interface','glyphicon glyphicon-cog',2),
(4,'客户管理','2',1,'customer','glyphicon glyphicon-cog',3),
(5,'用户管理','3',2,'system/user','glyphicon glyphicon-user',1),
(6,'角色管理','3',2,'system/role','glyphicon glyphicon-cog',2),
(7,'菜单管理','3',2,'system/menu','glyphicon glyphicon-list',3),
(8,'微信自定义菜单','3',3,'interface/menu','glyphicon glyphicon-list',2),
(9,'客户信息管理','3',4,'customer/information','glyphicon glyphicon-list',1),
(10,'微信接口配置','3',3,'','',1),
(11,'易信接口配置','3',3,'','',3),
(12,'易信自定义菜单','3',3,'','',4);

#角色初始化
INSERT INTO `system_role` (`role_id`,`role_name`,`role_desc`,`status`,`sort`,`reloadable`) 
VALUES (1,'系统管理员','管理客户接口','AVAILABLE',0,'AVAILABLE'),
(2,'接口管理员','接口配置，参数配置等','AVAILABLE',1,'AVAILABLE'),
(3,'编辑','客户方编辑，可以编辑接口回复的内容等','AVAILABLE',2,'AVAILABLE');

#用户初始化
INSERT INTO `system_user` (`user_id`,`username`,`password`,`xm`,`idcard`,`mphone`,`islock`,`bz`,`status`) 
VALUES (1,'mmtuser','7744ECF0A5500EACF229B022A0F41081','MMTCHAT','330','1234','UNAVAILABLE','bz','AVAILABLE');

#用户配置信息
INSERT INTO `system_user_config` (`user_id`,`key`,`value`,`timestamp`) 
VALUES (1,'EncryptKey','uziV2+-1FkVt)gkfuBsFsR3UgWvagl$yLRFfPbdw_)-x%9-F-W3qLg5umr/DNZh)oWfnIwl18t/BsJ#%PEFJ(GGyOW/atOWcSWlNym6yiomyd^4YQ!HjP=15.)IpYr(0UoTu_Y`UxoGHf)spiDEiCkQG6NW=P20nj/~ZyFPU6W8.vcY=7qeNuYB^G!H$aIzlQaZgrCSI8~)MbNqPP@@Yi1UI4eh7sRn`^5e=aqqiHnXVr%Ur=5@3&Z_.DmfH#J&u','2013-09-12 14:27:26'),
(1,'CUSTOMIZATION_HOME','system/menu','2013-09-17 10:21:36');

#权限信息
INSERT INTO `system_role_menu` (`role_id`,`menu_id`) 
VALUES (1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(1,11),
(1,12);