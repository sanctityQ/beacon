/*
 Navicat MySQL Data Transfer

 Source Server         : beacon_l
 Source Server Version : 50613
 Source Host           : localhost
 Source Database       : beacon_db_v5

 Target Server Version : 50613
 File Encoding         : utf-8

 Date: 12/19/2013 21:58:56 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ge_monitor_wls_ejbcache`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_ejbcache`;
CREATE TABLE `ge_monitor_wls_ejbcache` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `cache_access_count` int(11) DEFAULT NULL COMMENT '缓存访问数量',
  `activation_count` int(11) DEFAULT NULL COMMENT '激活数量',
  `cache_beans_current_count` int(11) DEFAULT NULL COMMENT '缓存bean当前数量',
  `cache_hit_count` int(11) DEFAULT NULL COMMENT '缓存击中数量',
  `cache_miss_count` int(11) DEFAULT NULL COMMENT '缓存错失数量',
  `passivation_count` int(11) DEFAULT NULL COMMENT '钝化数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7984 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_ejbpool`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_ejbpool`;
CREATE TABLE `ge_monitor_wls_ejbpool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `beans_inUse_count` int(11) DEFAULT NULL COMMENT '使用中bean数量',
  `beans_inUser_current_count` int(11) DEFAULT NULL COMMENT '当前使用中bean数量',
  `access_total_count` int(11) DEFAULT NULL COMMENT '总计访问数',
  `destroyed_total_count` int(11) DEFAULT NULL COMMENT '总计销毁数',
  `idle_beans_count` int(11) DEFAULT NULL COMMENT '空闲bean数量',
  `miss_total_count` int(11) DEFAULT NULL COMMENT '总计错失数量',
  `pooled_beans_current_count` int(11) DEFAULT NULL COMMENT '当前池化bean数量',
  `timeout_total_count` int(11) DEFAULT NULL COMMENT '总计超时数量',
  `waiter_current_count` int(11) DEFAULT NULL COMMENT '当前等待数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8199 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_jdbc`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_jdbc`;
CREATE TABLE `ge_monitor_wls_jdbc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `name` varchar(255) DEFAULT NULL COMMENT '连接池名称',
  `active_count` int(11) DEFAULT NULL COMMENT '活动链接数',
  `active_high` int(11) DEFAULT NULL COMMENT '连接最高值',
  `curr_capacity` int(11) DEFAULT NULL COMMENT '连接池大小',
  `leak_count` int(11) DEFAULT NULL COMMENT '连接泄露数',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29324 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_jms`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_jms`;
CREATE TABLE `ge_monitor_wls_jms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `bytes_current_count` int(11) DEFAULT NULL COMMENT '当前字节数',
  `bytes_high_count` int(11) DEFAULT NULL COMMENT '最高字节数',
  `bytes_pending_count` int(11) DEFAULT NULL COMMENT '挂起字节数',
  `bytes_received_count` int(11) DEFAULT NULL COMMENT '接收字节数',
  `messages_current_count` int(11) DEFAULT NULL COMMENT '当前消息数',
  `messages_high_count` int(11) DEFAULT NULL COMMENT '最高消息数',
  `messages_pending_count` int(11) DEFAULT NULL COMMENT '挂起消息数',
  `messages_received_count` int(11) DEFAULT NULL COMMENT '接收消息数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7985 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_jvm`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_jvm`;
CREATE TABLE `ge_monitor_wls_jvm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '入库时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `free_heap` varchar(255) DEFAULT NULL COMMENT '空闲heap',
  `current_heap` varchar(255) DEFAULT NULL COMMENT '当前heap使用数',
  `free_percent` varchar(255) DEFAULT NULL COMMENT '空闲heap百分比',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15778 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_resource`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_resource`;
CREATE TABLE `ge_monitor_wls_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_number` int(11) DEFAULT NULL COMMENT '服务器数量',
  `run_server_number` int(11) DEFAULT NULL COMMENT '运行服务器数量',
  `cpu_idle` int(11) DEFAULT NULL COMMENT 'cpu空闲',
  `mem_free` varchar(255) DEFAULT NULL,
  `os_type` varchar(255) DEFAULT NULL COMMENT 'os类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8191 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_server`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_server`;
CREATE TABLE `ge_monitor_wls_server` (
  `site_name` varchar(255) NOT NULL DEFAULT '' COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `version` varchar(20) DEFAULT NULL COMMENT 'weblogic版本',
  `listen_address` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `listen_port` int(10) DEFAULT NULL COMMENT '端口',
  `interval_` int(11) DEFAULT NULL COMMENT '轮询时间',
  `weblogic_ip` varchar(255) DEFAULT NULL COMMENT 'weblogic监听地址',
  `weblogic_port` int(11) DEFAULT NULL COMMENT 'weblogic监听端口',
  `domain_name` varchar(255) DEFAULT 'domain名称',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_SSL` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '有效位：1-有效0-无效',
  PRIMARY KEY (`site_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ge_monitor_wls_server`
-- ----------------------------
BEGIN;
INSERT INTO `ge_monitor_wls_server` VALUES ('115.28.16.154', '2013-11-25 23:44:53', 'weblogic 8.x', '115.28.16.154', '9001', '30', '115.28.16.154', '7001', 'baseDomain', 'weblogic', 'weblogic10', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `ge_monitor_wls_svr`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_svr`;
CREATE TABLE `ge_monitor_wls_svr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `listen_address` varchar(255) DEFAULT NULL COMMENT '监听地址',
  `listen_port` varchar(255) DEFAULT NULL COMMENT '监听端口',
  `health` varchar(255) DEFAULT NULL COMMENT '健康状态',
  `state` varchar(255) DEFAULT NULL COMMENT '状态',
  `open_socket_num` int(11) DEFAULT NULL COMMENT '开启端口数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7630 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_sysrec`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_sysrec`;
CREATE TABLE `ge_monitor_wls_sysrec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `agent_version` varchar(255) DEFAULT NULL COMMENT 'Agent版本',
  `domain_version` varchar(255) DEFAULT NULL COMMENT '中间件版本',
  `system_boot` varchar(255) DEFAULT NULL COMMENT '系统启动时间',
  `os_type` varchar(255) DEFAULT NULL COMMENT 'os类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_thread`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_thread`;
CREATE TABLE `ge_monitor_wls_thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `idle_count` int(11) DEFAULT NULL COMMENT '空闲数量',
  `standby_count` int(11) DEFAULT NULL COMMENT '备用数量',
  `total_count` int(11) DEFAULT NULL COMMENT '总量',
  `thoughput` int(11) DEFAULT NULL COMMENT '吞吐量',
  `queue_length` int(11) DEFAULT NULL COMMENT '队列大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15551 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ge_monitor_wls_webapp`
-- ----------------------------
DROP TABLE IF EXISTS `ge_monitor_wls_webapp`;
CREATE TABLE `ge_monitor_wls_webapp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL COMMENT '站点名称',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  `server_name` varchar(255) DEFAULT NULL COMMENT 'Server名称',
  `name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `deployment_state` varchar(255) DEFAULT NULL COMMENT '部署状态',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `component_name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `open_sessions_high_count` int(11) DEFAULT NULL COMMENT '最高会话数',
  `open_sessions_current_count` int(11) DEFAULT NULL COMMENT '当前会话数',
  `sessions_opened_total_count` int(11) DEFAULT NULL COMMENT '累计打开会话数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15916 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
