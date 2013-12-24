

drop table if exists ge_monitor_alarm;

drop table if exists ge_monitor_attribute;

drop table if exists ge_monitor_attribute_action;

drop table if exists ge_monitor_attribute_threshold;

drop table if exists ge_monitor_email_action;

drop table if exists ge_monitor_resources;

drop table if exists ge_monitor_threshold;

/*==============================================================*/
/* Table: ge_monitor_alarm                                      */
/*==============================================================*/
create table ge_monitor_alarm
(
   ID                   varchar(32) not null comment '主键ID',
   SEVERITY             varchar(10) comment '严重级别(severity)',
   MESSAGE              varchar(3000) comment '警报信息(message)',
   ALARM_SOURCE         varchar(20) comment '警报来源(日志,异常)',
   MONITOR_ID           varchar(32) comment '监视器ID',
   MONITOR_TYPE         varchar(50) comment '监视器类型',
   ATTRIBUTE_ID         varchar(32) comment '属性ID',
   CREATE_TIME          datetime comment '创建时间(createtime)',
   OWNER_NAME           varchar(100) comment '所有者(ownername)',
   SUB_RESOURCE_TYPE    varchar(32) comment '子资源类型',
   SUB_RESOURCE_ID      varchar(32) comment '子资源ID',
   primary key (ID)
);

alter table ge_monitor_alarm comment '告警信息';


/*==============================================================*/
/* Table: ge_monitor_attribute                                  */
/*==============================================================*/
create table ge_monitor_attribute
(
   ID                   varchar(32) not null comment '主键ID',
   RESOURCE_TYPE        varchar(32) comment '资源类型',
   ATTRIBUTE            varchar(255) comment '属性',
   UNITS                varchar(10) comment '单位',
   ATTRIBUTE_CN         varchar(255) comment '属性中文名称',
   primary key (ID)
);

alter table ge_monitor_attribute comment '属性信息表';

/*==============================================================*/
/* Table: ge_monitor_attribute_action                           */
/*==============================================================*/
create table ge_monitor_attribute_action
(
   ID                   varchar(32) not null comment '主键ID',
   RESOURCE_ID          varchar(32) not null comment '资源ID',
   ATTRIBUTE_ID         varchar(32) not null comment '属性ID',
   ACTION_ID            varchar(32) not null comment '动作ID',
   ACTION_TYPE          varchar(20) comment '动作类型',
   SEVERITY             varchar(32) not null comment '严重程度',
   primary key (ID)
);

alter table ge_monitor_attribute_action comment '属性动作信息表';

/*==============================================================*/
/* Table: GE_MONITOR_ATTRIBUTE_THRESHOLD                        */
/*==============================================================*/
create table ge_monitor_attribute_threshold
(
   ID                   varchar(32) not null comment '主键ID',
   RESOURCE_ID          varchar(32) not null comment '资源ID',
   ATTRIBUTE_ID         varchar(32) not null comment '属性ID',
   THRESHOLD_ID         varchar(32) not null comment '阈值ID',
   primary key (ID)
);

alter table ge_monitor_attribute_threshold comment '属性阈值信息表';


/*==============================================================*/
/* Table: GE_MONITOR_EMAIL_ACTION                               */
/*==============================================================*/
create table ge_monitor_email_action
(
   ID                   varchar(32) not null comment '主键ID',
   FROM_ADDRESS         varchar(250) not null comment '发件地址(fromaddress)',
   TO_ADDRESS           varchar(250) not null comment '收件地址(toaddress)',
   SUBJECT              varchar(100) not null comment '主题(subject)',
   CONTENT              varchar(3000) comment '内容(CONTENT)',
   SMTP_SERVER          varchar(100) comment '邮件服务器(smtpserver)',
   SMTP_PORT            varchar(5) comment '邮件服务器端口(smtpport)',
   MAIL_FORMAT          varchar(1) comment '邮件格式(mailformat)',
   APPEND_MESSAGE       varchar(100) comment '附加信息(appendmessage)',
   NAME                 varchar(100) not null comment '动作名称(name)',
   primary key (ID)
);

alter table ge_monitor_email_action comment '邮件动作信息表';


/*==============================================================*/
/* Table: GE_MONITOR_RESOURCES                                  */
/*==============================================================*/
create table ge_monitor_resources
(
   RESOURCE_ID          varchar(32) not null comment '资源ID',
   RESOURCE_NAME        varchar(300) comment '资源名称',
   RESOURCE_TYPE        varchar(100) not null comment '资源类型',
   primary key (RESOURCE_ID)
);

alter table ge_monitor_resources comment '资源表';

/*==============================================================*/
/* Table: GE_MONITOR_THRESHOLD                                  */
/*==============================================================*/
create table ge_monitor_threshold
(
   ID                   varchar(32) not null comment '主键ID',
   NAME                 varchar(100) comment '名称',
   TYPE                 varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '阈值类型：字符串/数值类型',
   DESCRIPTION          varchar(250) comment '描述',
   CRITICAL_THRESHOLD_CONDITION varchar(2) comment '临界阈值条件',
   CRITICAL_THRESHOLD_VALUE varchar(200) comment '临界阈值值',
   CRITICAL_THRESHOLD_MESSAGE varchar(250) comment '临界阈值信息',
   WARNING_THRESHOLD_CONDITION varchar(2) comment '警告阈值条件',
   WARNING_THRESHOLD_VALUE varchar(200) comment '警告阈值值',
   WARNING_THRESHOLD_MESSAGE varchar(250) comment '警告阈值信息',
   INFO_THRESHOLD_CONDITION varchar(2) comment '正常阈值条件',
   INFO_THRESHOLD_VALUE varchar(200) comment '正常阈值值',
   INFO_THRESHOLD_MESSAGE varchar(250) comment '正常阈值信息',
   primary key (ID)
);

alter table ge_monitor_threshold comment '阈值信息表';




-- ----------------------------
-- add by tuxedo
--  Table structure for GE_MONITOR_APP_SERVER
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_app_server;
CREATE TABLE ge_monitor_app_server (
  siteName varchar(255) COLLATE utf8_bin NOT NULL COMMENT '站点名称',
  siteType varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点类型',
  siteIP varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点IP',
  sitePort int(11) DEFAULT NULL COMMENT '站点端口',
  siteAuth varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS用户',
  siteWd varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS密码',
  wlsAdminUrl varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS控制台地址',
  wlsVer varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS版本',
  wlsSSL int(11) DEFAULT NULL COMMENT 'WLS是否使用SSL',
  databaseFile varchar(255) COLLATE utf8_bin DEFAULT NULL,
  siteFlag varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `interval` int(10) DEFAULT NULL,
  PRIMARY KEY (siteName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_CLT
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_clt;
CREATE TABLE ge_monitor_tux_clt (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  connTime varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端连接时间',
  clientStatus varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端状态',
  clientAddr varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端地址',
  clientPid varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端进程号',
  clientName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端名称',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for GE_MONITOR_TUX_CLT_STATS
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_clt_stats;
CREATE TABLE ge_monitor_tux_clt_stats (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  busyCount varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '繁忙客户端数量',
  totalCount varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '总计客户端数量',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_QUE
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_que;
CREATE TABLE ge_monitor_tux_que (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  progName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Server程序名称',
  queued int(11) DEFAULT NULL COMMENT '队列排队数量',
  svrCnt int(11) DEFAULT NULL COMMENT '队列上Server数量',
  ipcsId varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'IPCS值',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1816 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_QUE_STATS
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_que_stats;
CREATE TABLE ge_monitor_tux_que_stats (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  queueNum int(11) DEFAULT NULL COMMENT '队列排队数量',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_resource;
CREATE TABLE ge_monitor_tux_resource (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  tuxRunSvr int(11) DEFAULT NULL COMMENT '总计运行server数量',
  tuxRunQueue int(11) DEFAULT NULL COMMENT '总计运行队列数量',
  tuxRunClt int(11) DEFAULT NULL COMMENT '总计运行客户端数量',
  cpuIdle int(11) DEFAULT NULL COMMENT 'cpu空闲清空',
  memFree float DEFAULT NULL COMMENT '内存剩余情况',
  allSvrCpuUse float DEFAULT NULL COMMENT '总计运行server占用CPU数量',
  allSvrMemUsed int(11) DEFAULT NULL COMMENT '总计运行server占用内存数量',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_SETTING
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_setting;
CREATE TABLE ge_monitor_tux_setting (
  siteName varchar(255) COLLATE utf8_bin NOT NULL,
  siteType varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点类型',
  siteSetting longtext COLLATE utf8_bin COMMENT '站点设置',
  flag varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (siteName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_SVR
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_svr;
CREATE TABLE ge_monitor_tux_svr (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  progName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Server程序名称',
  memoryUse int(11) DEFAULT NULL COMMENT '内存使用数量',
  cpuUse float DEFAULT NULL COMMENT 'CPU使用数量',
  currenctSvc varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '当前执行的service',
  svrMin int(11) DEFAULT NULL COMMENT '此类server最小个数',
  svrMax int(11) DEFAULT NULL COMMENT '此类server最大个数',
  rqDone int(11) DEFAULT NULL COMMENT 'server执行数量',
  processId varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '进程号ID',
  queueName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所在队列名称',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_SVR_STATS
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_svr_stats;
CREATE TABLE ge_monitor_tux_svr_stats (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  rqDone int(11) DEFAULT NULL COMMENT '总计server执行数量',
  tpsDone float DEFAULT NULL COMMENT 'Server执行数量TPS',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_SYSRECS
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_tux_sysrecs;
CREATE TABLE ge_monitor_tux_sysrecs (
  id int(11) NOT NULL AUTO_INCREMENT,
  siteName varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  recTime datetime DEFAULT NULL COMMENT '记录保存时间',
  productVer varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '中间件版本',
  systemBoot varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '系统启动时间',
  osType varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作系统类型',
  agentVer varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'agent版本',
  create_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  update_at varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留ruby',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

DROP TABLE IF EXISTS ge_monitor_tux_server;
CREATE TABLE ge_monitor_tux_server (
  siteName varchar(255) COLLATE utf8_bin NOT NULL COMMENT '站点名称',
  siteIP varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点IP',
  sitePort int(11) DEFAULT NULL COMMENT '站点端口',
  siteAuth varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS用户',
  siteWd varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点WLS密码',
  `interval` int(10) DEFAULT NULL,
  record_time datetime DEFAULT NULL,
  PRIMARY KEY (siteName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


DROP TABLE IF EXISTS ge_monitor_account;

create table ge_monitor_account
(
  ID                   varchar(32) not null comment '主键ID',
  LOGIN_NAME						varchar(100) comment '登陆名称',
  PASSWORD             varchar(50) comment '密码',
  NAME									varchar(50) comment '姓名',
  EMAIL								varchar(100) comment '邮件',
  PHONE         				varchar(30) comment '手机号',
  CREATE_TIME          datetime comment '创建时间',
  STATUS       				varchar(1) comment '状态',
  primary key (ID)
);

alter table ge_monitor_account comment '用户管理';

DROP TABLE IF EXISTS ge_monitor_group;
CREATE TABLE ge_monitor_group (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


DROP TABLE IF EXISTS ge_monitor_account_group;
CREATE TABLE ge_monitor_account_group (
  user_id varchar(32) COLLATE utf8_bin NOT NULL,
  group_id bigint(20) NOT NULL,
  KEY `FKFE85CB3EDE3FB930` (group_id),
  KEY `FKFE85CB3E836A7D10` (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

BEGIN;

INSERT INTO ge_monitor_group VALUES ('1', 'admin'), ('2', 'user');

INSERT INTO ge_monitor_account_group VALUES ('1', '1');

INSERT INTO ge_monitor_account VALUES ('1', 'admin', 'admin', 'Admin', 'admin@datasource.cn', '', '2012-12-25 19:36:34', '1');
COMMIT;


-- ----------------------------
--  `GE_MONITOR_ATTRIBUTE 监控属性
-- ----------------------------
BEGIN;
INSERT INTO ge_monitor_attribute VALUES
('1', 'WEBLOGIC', 'ServerDied', null, '宕机'),
('2', 'WEBLOGIC', 'FreeHeap', 'M', '可用内存'),
('3', 'WEBLOGIC', 'ThreadUtilization', null, '线程使用率'),
('4', 'WEBLOGIC', 'JdbcUtilization', null, '数据库连接使用率'),
('5', 'WEBLOGIC', 'CPUUtilization', null, 'CPU利用率'),
('6', 'WEBLOGIC', 'SystemStop', null, '系统停止'),
('18', 'Tuxedo', 'ServerDied', null, '进程异常退出'),
('19', 'Tuxedo', 'ServerNoTrans', '', '无交易'),
('20', 'Tuxedo', 'ServerBusy', '', '服务繁忙'),
('21', 'Tuxedo', 'UsedMemory', 'M', '已用内存'),
('22', 'Tuxedo', 'QueuedNumber', '', '队列大小'),
('23', 'Tuxedo', 'CPUUtilization', '%', 'CPU利用率'),
('24', 'Tuxedo', 'SystemStop', null, '系统停止');
COMMIT;


/* -----统计报表 start-----*/
DROP TABLE IF EXISTS ge_monitor_report_statistics;
CREATE TABLE ge_monitor_report_statistics (
  id int(11) NOT NULL AUTO_INCREMENT,
  resource_id varchar(32) DEFAULT NULL,
  resource_type varchar(32) DEFAULT NULL,
  attribute_name varchar(255) DEFAULT NULL,
  start_time datetime DEFAULT NULL,
  end_time datetime DEFAULT NULL,
  max decimal(10,2) DEFAULT NULL,
  min decimal(10,2) DEFAULT NULL,
  avg decimal(10,2) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS ge_monitor_report_statistics_top;
CREATE TABLE ge_monitor_report_statistics_top (
  id int(11) NOT NULL AUTO_INCREMENT,
  resource_id varchar(32) COLLATE utf8_bin DEFAULT NULL,
  resource_type varchar(32) COLLATE utf8_bin DEFAULT NULL,
  attribute_name varchar(255) COLLATE utf8_bin DEFAULT NULL,
  start_time datetime DEFAULT NULL,
  end_time datetime DEFAULT NULL,
  top_value longtext COLLATE utf8_bin,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/* ----- webLogic start-----*/
DROP TABLE IF EXISTS ge_monitor_wls_ejbcache;
CREATE TABLE ge_monitor_wls_ejbcache (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  name varchar(255) DEFAULT NULL COMMENT '名称',
  cache_access_count int(11) DEFAULT NULL COMMENT '缓存访问数量',
  activation_count int(11) DEFAULT NULL COMMENT '激活数量',
  cache_beans_current_count int(11) DEFAULT NULL COMMENT '缓存bean当前数量',
  cache_hit_count int(11) DEFAULT NULL COMMENT '缓存击中数量',
  cache_miss_count int(11) DEFAULT NULL COMMENT '缓存错失数量',
  passivation_count int(11) DEFAULT NULL COMMENT '钝化数量',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_ejbpool
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_ejbpool;
CREATE TABLE ge_monitor_wls_ejbpool (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  name varchar(255) DEFAULT NULL COMMENT '名称',
  beans_inUse_count int(11) DEFAULT NULL COMMENT '使用中bean数量',
  beans_inUser_current_count int(11) DEFAULT NULL COMMENT '当前使用中bean数量',
  access_total_count int(11) DEFAULT NULL COMMENT '总计访问数',
  destroyed_total_count int(11) DEFAULT NULL COMMENT '总计销毁数',
  idle_beans_count int(11) DEFAULT NULL COMMENT '空闲bean数量',
  miss_total_count int(11) DEFAULT NULL COMMENT '总计错失数量',
  pooled_beans_current_count int(11) DEFAULT NULL COMMENT '当前池化bean数量',
  timeout_total_count int(11) DEFAULT NULL COMMENT '总计超时数量',
  waiter_current_count int(11) DEFAULT NULL COMMENT '当前等待数量',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_jdbc
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_jdbc;
CREATE TABLE ge_monitor_wls_jdbc (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  name varchar(255) DEFAULT NULL COMMENT '连接池名称',
  active_count int(11) DEFAULT NULL COMMENT '活动链接数',
  active_high int(11) DEFAULT NULL COMMENT '连接最高值',
  curr_capacity int(11) DEFAULT NULL COMMENT '连接池大小',
  leak_count int(11) DEFAULT NULL COMMENT '连接泄露数',
  state varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_jms
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_jms;
CREATE TABLE ge_monitor_wls_jms (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  name varchar(255) DEFAULT NULL COMMENT '名称',
  bytes_current_count int(11) DEFAULT NULL COMMENT '当前字节数',
  bytes_high_count int(11) DEFAULT NULL COMMENT '最高字节数',
  bytes_pending_count int(11) DEFAULT NULL COMMENT '挂起字节数',
  bytes_received_count int(11) DEFAULT NULL COMMENT '接收字节数',
  messages_current_count int(11) DEFAULT NULL COMMENT '当前消息数',
  messages_high_count int(11) DEFAULT NULL COMMENT '最高消息数',
  messages_pending_count int(11) DEFAULT NULL COMMENT '挂起消息数',
  messages_received_count int(11) DEFAULT NULL COMMENT '接收消息数',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_jvm
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_jvm;
CREATE TABLE ge_monitor_wls_jvm (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '入库时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  free_heap varchar(255) DEFAULT NULL COMMENT '空闲heap',
  current_heap varchar(255) DEFAULT NULL COMMENT '当前heap使用数',
  free_percent varchar(255) DEFAULT NULL COMMENT '空闲heap百分比',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_resource
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_resource;
CREATE TABLE ge_monitor_wls_resource (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_number int(11) DEFAULT NULL COMMENT '服务器数量',
  run_server_number int(11) DEFAULT NULL COMMENT '运行服务器数量',
  cpu_idle int(11) DEFAULT NULL COMMENT 'cpu空闲',
  mem_free varchar(255) DEFAULT NULL,
  os_type varchar(255) DEFAULT NULL COMMENT 'os类型',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_server
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_server;
CREATE TABLE ge_monitor_wls_server (
  site_name varchar(255) NOT NULL DEFAULT '' COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  version varchar(20) DEFAULT NULL COMMENT 'weblogic版本',
  listen_address varchar(255) DEFAULT NULL COMMENT 'IP地址',
  listen_port int(10) DEFAULT NULL COMMENT '端口',
  interval_ int(11) DEFAULT NULL COMMENT '轮询时间',
  weblogic_ip varchar(255) DEFAULT NULL COMMENT 'weblogic监听地址',
  weblogic_port int(11) DEFAULT NULL COMMENT 'weblogic监听端口',
  domain_name varchar(255) DEFAULT 'domain名称',
  user_name varchar(255) DEFAULT NULL COMMENT '用户名',
  password varchar(255) DEFAULT NULL COMMENT '密码',
  is_SSL int(11) DEFAULT NULL,
  status int(11) DEFAULT NULL COMMENT '有效位：1-有效0-无效',
  PRIMARY KEY (site_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_svr
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_svr;
CREATE TABLE ge_monitor_wls_svr (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  listen_address varchar(255) DEFAULT NULL COMMENT '监听地址',
  listen_port varchar(255) DEFAULT NULL COMMENT '监听端口',
  health varchar(255) DEFAULT NULL COMMENT '健康状态',
  state varchar(255) DEFAULT NULL COMMENT '状态',
  open_socket_num int(11) DEFAULT NULL COMMENT '开启端口数',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_sysrec
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_sysrec;
CREATE TABLE ge_monitor_wls_sysrec (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  agent_version varchar(255) DEFAULT NULL COMMENT 'Agent版本',
  domain_version varchar(255) DEFAULT NULL COMMENT '中间件版本',
  system_boot varchar(255) DEFAULT NULL COMMENT '系统启动时间',
  os_type varchar(255) DEFAULT NULL COMMENT 'os类型',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_thread
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_thread;
CREATE TABLE ge_monitor_wls_thread (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  idle_count int(11) DEFAULT NULL COMMENT '空闲数量',
  standby_count int(11) DEFAULT NULL COMMENT '备用数量',
  total_count int(11) DEFAULT NULL COMMENT '总量',
  thoughput int(11) DEFAULT NULL COMMENT '吞吐量',
  queue_length int(11) DEFAULT NULL COMMENT '队列大小',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for ge_monitor_wls_webapp
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_wls_webapp;
CREATE TABLE ge_monitor_wls_webapp (
  id int(11) NOT NULL AUTO_INCREMENT,
  site_name varchar(255) DEFAULT NULL COMMENT '站点名称',
  rec_time datetime DEFAULT NULL COMMENT '记录时间',
  server_name varchar(255) DEFAULT NULL COMMENT 'Server名称',
  name varchar(255) DEFAULT NULL COMMENT '应用名称',
  deployment_state varchar(255) DEFAULT NULL COMMENT '部署状态',
  status varchar(255) DEFAULT NULL COMMENT '状态',
  component_name varchar(255) DEFAULT NULL COMMENT '组件名称',
  open_sessions_high_count int(11) DEFAULT NULL COMMENT '最高会话数',
  open_sessions_current_count int(11) DEFAULT NULL COMMENT '当前会话数',
  sessions_opened_total_count int(11) DEFAULT NULL COMMENT '累计打开会话数',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;