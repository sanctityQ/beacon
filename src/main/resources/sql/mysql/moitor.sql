drop table if exists ge_monitor_alarm;

drop table if exists ge_monitor_application;

drop table if exists ge_monitor_attribute;

drop table if exists ge_monitor_attribute_action;

drop table if exists ge_monitor_attribute_threshold;

drop table if exists ge_monitor_biz_scenario;

drop table if exists GE_MONITOR_BIZ_SCENARIO_URL;

drop table if exists GE_MONITOR_EMAIL_ACTION;

drop table if exists GE_MONITOR_EUM_URL;

drop table if exists GE_MONITOR_EUM_URL_AVA;

drop table if exists GE_MONITOR_EUM_URL_AVA_STA;

drop table if exists GE_MONITOR_EXCEPTION_INFO;

drop table if exists GE_MONITOR_METHOD;

drop table if exists GE_MONITOR_METHOD_RESPONSETIME;

drop table if exists GE_MONITOR_METHOD_TRACE_LOG;

drop table if exists GE_MONITOR_ORACLE_AVA;

drop table if exists GE_MONITOR_ORACLE_AVA_STA;

drop table if exists GE_MONITOR_ORACLE_EVENT_STA;

drop table if exists GE_MONITOR_ORACLE_INFO;

drop table if exists GE_MONITOR_ORACLE_LASTEVENT;

drop table if exists GE_MONITOR_OS;

drop table if exists GE_MONITOR_OS_AVAILABLE;

drop table if exists GE_MONITOR_OS_AVAILABLETEMP;

drop table if exists GE_MONITOR_OS_CPU;

drop table if exists GE_MONITOR_OS_DISK;

drop table if exists GE_MONITOR_OS_RAM;

drop table if exists GE_MONITOR_OS_RESPONDTIME;

drop table if exists GE_MONITOR_OS_SHELL;

drop table if exists GE_MONITOR_OS_STATI;

drop table if exists GE_MONITOR_REQUEST_PER_MINUTE;

drop table if exists GE_MONITOR_RESOURCES;

drop table if exists GE_MONITOR_THRESHOLD;

drop table if exists GE_MONITOR_URL;

drop table if exists GE_MONITOR_URL_METHOD;

drop table if exists GE_MONITOR_URL_RESPONSE_TIME;

drop table if exists GE_MONITOR_URL_TRACE_LOG;

drop table if exists GE_MONITOR_URL_VISITS_STA;

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

alter table ge_monitor_alarm comment '应用系统告警信息表';

/*==============================================================*/
/* Table: ge_monitor_application                                */
/*==============================================================*/
create table ge_monitor_application
(
   ID                   varchar(32) not null comment '主键ID',
   APPLICATION_NAME     varchar(100) not null comment '应用系统英文名称',
   CN_NAME              varchar(300) comment '应用系统中文名称',
   APPLICATION_IP       varchar(100) comment '应用IP',
   APPLICATION_PORT     varchar(5) comment '应用端口',
   CREATE_TIME          datetime not null comment '创建时间',
   CREATOR_ID           varchar(32) not null comment '创建人ID',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFIER_ID          varchar(32) comment '修改人ID',
   `INTERVAL`             numeric(8,0) comment '轮询间隔',
   STATUS               varchar(1) not null default '1' comment '有效状态:1有效,0删除',
   primary key (ID)
);

alter table ge_monitor_application comment '存储需要监控的应用系统信息';

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
/* Table: GE_MONITOR_BIZ_SCENARIO                               */
/*==============================================================*/
create table ge_monitor_biz_scenario
(
   ID                   varchar(32) not null comment '主键ID',
   NAME                 varchar(300) not null comment '业务场景名称',
   BIZ_SCENARIO_GRADE   varchar(15) comment '业务场景级别',
   APPLICATION_ID       varchar(32) not null comment '所属应用系统ID',
   CREATE_TIME          datetime not null comment '创建时间',
   CREATOR_ID           varchar(32) not null comment '创建人ID',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFIER_ID          varchar(32) comment '修改人ID',
   STATUS               varchar(1) not null default '1' comment '有效状态:1有效,0删除',
   primary key (ID)
);

alter table ge_monitor_biz_scenario comment '存储指定应用系统的业务场景信息';

/*==============================================================*/
/* Table: GE_MONITOR_BIZ_SCENARIO_URL                           */
/*==============================================================*/
create table ge_monitor_biz_scenario_url
(
   URL_ID               varchar(32) not null comment 'URLID',
   BIZ_SCENARIO_ID      varchar(32) comment '业务场景ID'
);

alter table ge_monitor_biz_scenario_url comment '应用系统业务场景URL信息表';

/*==============================================================*/
/* Table: GE_MONITOR_EMAIL_ACTION                               */
/*==============================================================*/
create table GE_MONITOR_EMAIL_ACTION
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

alter table GE_MONITOR_EMAIL_ACTION comment '应用系统邮件动作信息表';

/*==============================================================*/
/* Table: GE_MONITOR_EUM_URL                                    */
/*==============================================================*/
create table GE_MONITOR_EUM_URL
(
   ID                   varchar(32) not null comment '主键ID',
   URL                  varchar(300) not null comment 'URL地址',
   APPLICATION_ID       varchar(32) comment '所属应用系统ID',
   RECORD_TIME          datetime comment '记录时间',
   URL_ID               varchar(32) comment 'URL的ID',
   primary key (ID)
);

alter table GE_MONITOR_EUM_URL comment '业务仿真URL信息表';

/*==============================================================*/
/* Table: GE_MONITOR_EUM_URL_AVA                                */
/*==============================================================*/
create table GE_MONITOR_EUM_URL_AVA
(
   ID                   varchar(32) not null comment '主键ID',
   EUM_URL_ID           varchar(32) comment '业务仿真ID',
   STATE                varchar(1) comment '状态
            1---可用
            0---不可用',
   RECORD_TIME          datetime comment '记录时间',
   `INTERVAL`           numeric(8,0) not null comment '轮询间隔',
   primary key (ID)
);

alter table GE_MONITOR_EUM_URL_AVA comment '应用系统可用性临时表';

/*==============================================================*/
/* Table: GE_MONITOR_EUM_URL_AVA_STA                            */
/*==============================================================*/
create table GE_MONITOR_EUM_URL_AVA_STA
(
   ID                   varchar(32) not null comment '主键ID',
   EUM_URL_ID           varchar(32) comment '业务仿真URLID',
   NORMAL_RUNTIME       numeric(8,0) comment '正常运行时间',
   TOTAL_FAILURE_TIME   numeric(8,0) comment '总失败时间',
   FAILURE_COUNT        numeric(8,0) comment '停止次数',
   AVG_FAILURE_TIME     numeric(8,0) comment '平均故障间隔时间',
   RECORD_TIME          datetime comment '记录时间',
   primary key (ID)
);

alter table GE_MONITOR_EUM_URL_AVA_STA comment '业务仿真URL可用性统计表';

/*==============================================================*/
/* Table: GE_MONITOR_EXCEPTION_INFO                             */
/*==============================================================*/
create table GE_MONITOR_EXCEPTION_INFO
(
   ID                   varchar(32) not null comment '主键ID',
   APPLICATION_ID       varchar(32) comment '所属应用系统',
   EXCEPTION_DESCRIPTION varchar(500) comment '异常描述',
   EXCEPTION_STACK_TRACE text comment '异常堆栈',
   RECORD_TIME          datetime comment '异常时间',
   URL_TRACE_LOG_ID     varchar(32) comment 'URL追踪日志ID',
   ALARM_ID             varchar(32) comment '告警消息ID',
   URL                  varchar(300) comment 'URL地址',
   REQUEST_PARAMS       text comment 'URL请求参数',
   primary key (ID)
);

alter table GE_MONITOR_EXCEPTION_INFO comment '记录所有的异常信息';

/*==============================================================*/
/* Table: GE_MONITOR_METHOD                                     */
/*==============================================================*/
create table GE_MONITOR_METHOD
(
   ID                   varchar(32) not null comment '主键ID',
   DESCRIPTION          varchar(300) comment '方法描述',
   CLASS_NAME           varchar(500) not null comment '方法所属类名',
   METHOD_NAME          varchar(100) not null comment '方法名称',
   THRESHOLD            numeric(6,0) not null comment '方法阈值，单位ms',
   CREATE_TIME          datetime not null comment '创建时间',
   CREATOR_ID           varchar(32) not null comment '创建人ID',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFIER_ID          varchar(32) comment '修改人ID',
   STATUS               varchar(1) not null default '1' comment '有效状态（1--有效 0--删除）',
   primary key (ID)
);

alter table GE_MONITOR_METHOD comment '应用系统方法信息表';

/*==============================================================*/
/* Table: GE_MONITOR_METHOD_RESPONSETIME                        */
/*==============================================================*/
create table GE_MONITOR_METHOD_RESPONSETIME
(
   ID                   varchar(32) not null comment '主键ID',
   METHOD_NAME          varchar(300) comment '方法名',
   URL_ID               varchar(32) comment 'URL信息ID',
   MIN_RESPONSE_TIME    numeric(8,0) comment '最小响应时间',
   MAX_RESPONSE_TIME    numeric(8,0) comment '最大响应时间',
   TOTAL_RESPONSE_TIME  numeric(8,0) comment '总响应时间',
   TOTAL_COUNT          numeric(8,0) comment '总次数',
   RECORD_TIME          datetime comment '记录时间',
   APPLICATION_ID       varchar(32) comment '所属应用系统',
   METHOD_ID            varchar(32) comment '方法信息ID',
   primary key (ID)
);

alter table GE_MONITOR_METHOD_RESPONSETIME comment '方法响应时间';

/*==============================================================*/
/* Table: GE_MONITOR_METHOD_TRACE_LOG                           */
/*==============================================================*/
create table GE_MONITOR_METHOD_TRACE_LOG
(
   ID                   varchar(32) not null comment '主键ID',
   URL_TRACE_LOG_ID     varchar(32) comment 'URL追踪日志ID',
   METHOD_NAME          varchar(100) not null comment '方法名',
   CLASS_NAME           varchar(200) comment '方法所属类名',
   IN_PARAM             varchar(3000) comment '方法输入参数',
   OUT_PARAM            varchar(3000) comment '方法输出参数',
   BEGIN_TIME           datetime comment '方法开始时间',
   END_TIME             datetime comment '方法结束时间',
   CONSUME_TIME         numeric(8,0) comment 'URL执行时间',
   RECORD_TIME          datetime comment '日志记录时间',
   METHOD_ID            varchar(32) comment '方法信息ID',
   primary key (ID)
);

alter table GE_MONITOR_METHOD_TRACE_LOG comment '方法追踪日志信息表';

/*==============================================================*/
/* Table: GE_MONITOR_ORACLE_AVA                                 */
/*==============================================================*/
create table GE_MONITOR_ORACLE_AVA
(
   ID                   varchar(32) not null comment '主键ID',
   DATABASE_ID          varchar(32) comment '数据库ID',
   RECORD_TIME          datetime comment '记录时间',
   STATE                varchar(1),
   `INTERVAL`           numeric(8,0),
   primary key (ID)
);

alter table GE_MONITOR_ORACLE_AVA comment 'ORACLE可用性临时表';

/*==============================================================*/
/* Table: GE_MONITOR_ORACLE_AVA_STA                             */
/*==============================================================*/
create table GE_MONITOR_ORACLE_AVA_STA
(
   ID                   varchar(32) not null comment '主键ID',
   DATABASE_ID          varchar(32) comment '数据库ID',
   NORMAL_RUNTIME       numeric(8,0),
   TOTAL_POWEROFF_TIME  numeric(8,0),
   POWEROFF_COUNT       numeric(8,0),
   AVG_FAILURE_TIME     numeric(8,0),
   AVA_RECORD_TIME      datetime comment '记录时间',
   AV_COUNT             numeric(8,0),
   UNAV_COUNT           numeric(8,0),
   UNKONW_TIME          numeric(8,0),
   RECORD_TIME          datetime,
   primary key (ID)
);

alter table GE_MONITOR_ORACLE_AVA_STA comment 'ORACLE可用性统计表';

/*==============================================================*/
/* Table: GE_MONITOR_ORACLE_EVENT_STA                           */
/*==============================================================*/
create table GE_MONITOR_ORACLE_EVENT_STA
(
   ID                   varchar(32) not null comment '主键ID',
   DATABASE_ID          varchar(32) comment '数据库ID',
   EVENT_TYPE           varchar(2),
   MIN                  numeric(8,0) comment '活动连接数',
   MAX                  numeric(8,0) comment '活动连接数最大值',
   AVG                  numeric(8,0),
   ENVENT_RECORD_TIME   datetime comment '记录时间',
   EVENT_COUNT          numeric(38,0),
   RECORD_TIME          datetime,
   primary key (ID)
);

alter table GE_MONITOR_ORACLE_EVENT_STA comment 'ORACLE事件统计表';

/*==============================================================*/
/* Table: GE_MONITOR_ORACLE_INFO                                */
/*==============================================================*/
create table GE_MONITOR_ORACLE_INFO
(
   ID                   varchar(32) not null comment '主键ID',
   NAME                 varchar(80) comment '名称',
   VERSION              varchar(50) comment '版本',
   IP_ADDRESS           varchar(30) comment 'IP地址',
   SUBNET_MASK          varchar(30) comment '子网掩码',
   PORT                 varchar(5) comment '端口',
   PULL_INTERVAL        numeric(8,0) comment '轮询间隔',
   USERNAME             varchar(100) comment '用户名',
   PASSWORD             varchar(50) comment '密码',
   INSTANCE_NAME        varchar(20) comment '服务名',
   SYS_TIME             datetime,
   START_TIME           varchar(50),
   primary key (ID)
);

alter table GE_MONITOR_ORACLE_INFO comment 'Oracle数据库信息表';


/*==============================================================*/
/* Table: GE_MONITOR_ORACLE_LASTEVENT                           */
/*==============================================================*/
create table GE_MONITOR_ORACLE_LASTEVENT
(
   ID                   varchar(32) not null comment '主键ID',
   DATABASE_ID          varchar(32) comment '数据库ID',
   ACTIVE_COUNT         numeric(8,0) comment '活动连接数',
   CONNECT_TIME         numeric(8,0) comment '连接时间',
   BUFFER_HIT_RATE      float comment '缓冲区命中率',
   DICK_HIT_RATE        float,
   BUFFER_LIB_HIT_RATE  float,
   RECORD_TIME          datetime comment '记录时间',
   primary key (ID)
);

alter table GE_MONITOR_ORACLE_LASTEVENT comment 'ORACLE最近一小时事件记录';

/*==============================================================*/
/* Table: GE_MONITOR_OS                                         */
/*==============================================================*/
create table GE_MONITOR_OS
(
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   NAME                 varchar(16) comment 'OS名称',
   TYPE                 varchar(16) comment 'OS类型',
   IP_ADDR              varchar(16) comment 'OSIP地址',
   SUBNET_MASK          varchar(16) comment 'OS子网掩码',
   INTERCYCLE_TIME      numeric(8,0) comment '轮询间隔',
   primary key (OS_INFO_ID)
);

alter table GE_MONITOR_OS comment '操作系统信息';

/*==============================================================*/
/* Table: GE_MONITOR_OS_AVAILABLE                               */
/*==============================================================*/
create table GE_MONITOR_OS_AVAILABLE
(
   ID                   varchar(32) not null comment 'ID',
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   NORMAL_RUN           numeric(8,0) comment '正常运行时间',
   CRASH_TIME           numeric(8,0) comment '总停机时间',
   AVE_REPAIR_TIME      numeric(8,0) comment '平均修复时间',
   AVE_FAULT_TIME       numeric(8,0) comment '平均故障间隔时间',
   TIME_SPAN            datetime comment '时间段',
   STOP_COUNT           numeric(8,0),
   primary key (ID)
);

alter table GE_MONITOR_OS_AVAILABLE comment '系统可用性';

/*==============================================================*/
/* Table: GE_MONITOR_OS_AVAILABLETEMP                           */
/*==============================================================*/
create table GE_MONITOR_OS_AVAILABLETEMP
(
   ID                   varchar(32) not null comment 'ID',
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   SAMPLE_DATE          datetime,
   STATUS               varchar(2),
   INTERCYCLE_TIME      numeric(8,0),
   primary key (ID)
);

/*==============================================================*/
/* Table: GE_MONITOR_OS_CPU                                     */
/*==============================================================*/
create table GE_MONITOR_OS_CPU
(
   ID                   varchar(32) not null comment 'CPU信息表',
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   SAMPLE_DATE          datetime comment '采样时间',
   UTILI_ZATION         varchar(10) comment 'CPU使用率',
   RUN_QUEUE            varchar(10) comment '运行队列',
   BLOCK_PROCESS        varchar(10) comment '阻塞进程',
   USER_TIME            varchar(10) comment '用户时间',
   SYS_TIME             varchar(10) comment '系统时间',
   IO_WAIT              varchar(10) comment 'I/o等待时间',
   CPU_IDLE             varchar(10) comment '空闲时间',
   INTER_RUPT           varchar(10) comment '中断',
   primary key (ID)
);

alter table GE_MONITOR_OS_CPU comment 'CPU信息表';

/*==============================================================*/
/* Table: GE_MONITOR_OS_DISK                                    */
/*==============================================================*/
create table GE_MONITOR_OS_DISK
(
   ID                   varchar(32) not null comment '磁盘信息表ID',
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   DISK_PATH            varchar(32),
   TOTAL                varchar(20) comment '总量',
   USED                 varchar(20) comment '使用量',
   FREE                 varchar(20) comment '空闲量',
   USED_UTILI_ZATION    varchar(20) comment '使用率',
   FREE_UTILI_ZATION    varchar(20) comment '空闲率',
   SAMPLE_DATE          datetime comment '采样时间',
   MOUNT_POINT          varchar(20) comment '挂载点',
   TOTAL_UTILI_ZATION   varchar(20) comment '总利用率',
   primary key (ID)
);

alter table GE_MONITOR_OS_DISK comment '磁盘信息表';

/*==============================================================*/
/* Table: GE_MONITOR_OS_RAM                                     */
/*==============================================================*/
create table GE_MONITOR_OS_RAM
(
   ID                   varchar(32) not null,
   OS_INFO_ID           varchar(32) not null comment 'OS信息表ID',
   SAMPLE_DATE          datetime,
   MEM_TOTAL            varchar(20),
   MEM_USED             varchar(20),
   MEM_UTILI_ZATION     varchar(8),
   SWAP_TOTAL           varchar(20),
   SWAP_USED            varchar(20),
   SWAP_UTILI_ZATION    varchar(8),
   primary key (ID)
);

alter table GE_MONITOR_OS_RAM comment '内存信息表';

/*==============================================================*/
/* Table: GE_MONITOR_OS_RESPONDTIME                             */
/*==============================================================*/
create table GE_MONITOR_OS_RESPONDTIME
(
   ID                   varchar(32) not null,
   OS_INFO_ID           varchar(32) comment 'OS信息表ID',
   SAMPLE_DATE          datetime comment '采样时间',
   RESPOND_TIME         varchar(32) comment '应答时间',
   primary key (ID)
);

alter table GE_MONITOR_OS_RESPONDTIME comment '应答时间';

/*==============================================================*/
/* Table: GE_MONITOR_OS_SHELL                                   */
/*==============================================================*/
create table GE_MONITOR_OS_SHELL
(
   ID                   varchar(32) not null comment 'ID',
   TYPE                 varchar(2) comment '类别',
   TEMPLATE             text comment '脚本',
   primary key (ID)
);

alter table GE_MONITOR_OS_SHELL comment '操作系统脚本信息';

/*==============================================================*/
/* Table: GE_MONITOR_OS_STATI                                   */
/*==============================================================*/
create table GE_MONITOR_OS_STATI
(
   ID                   varchar(32) not null,
   OSID                 varchar(32) comment '操作系统OS',
   TYPE                 varchar(2) comment '类型',
   RECORD_TIME          datetime comment '时间',
   MIN_VALUE            varchar(20) comment '最小值',
   MAX_VALUE            varchar(20) comment '最大值',
   AVERAGE_VALUE        varchar(20) comment '平均值',
   primary key (ID)
);

alter table GE_MONITOR_OS_STATI comment 'OS信息统计表

系统可用性时间点 1/-1';

/*==============================================================*/
/* Table: GE_MONITOR_REQUEST_PER_MINUTE                         */
/*==============================================================*/
create table GE_MONITOR_REQUEST_PER_MINUTE
(
   ID                   varchar(32) not null comment '主键ID',
   APPLICATION_ID       varchar(32) comment '所属应用系统ID',
   REQUEST_NUMBER       numeric(8,0) comment '请求数',
   RECORD_TIME          datetime comment '记录时间',
   primary key (ID)
);

alter table GE_MONITOR_REQUEST_PER_MINUTE comment '应用系统每分钟请求数';

/*==============================================================*/
/* Table: GE_MONITOR_RESOURCES                                  */
/*==============================================================*/
create table GE_MONITOR_RESOURCES
(
   RESOURCE_ID          varchar(32) not null comment '资源ID',
   RESOURCE_NAME        varchar(300) comment '资源名称',
   RESOURCE_TYPE        varchar(100) not null comment '资源类型',
   primary key (RESOURCE_ID)
);

alter table GE_MONITOR_RESOURCES comment '资源表';

/*==============================================================*/
/* Table: GE_MONITOR_THRESHOLD                                  */
/*==============================================================*/
create table GE_MONITOR_THRESHOLD
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

alter table GE_MONITOR_THRESHOLD comment '阈值信息表';

/*==============================================================*/
/* Table: GE_MONITOR_URL                                        */
/*==============================================================*/
create table GE_MONITOR_URL
(
   ID                   varchar(32) not null comment '主键ID',
   DESCRIPTION          varchar(300) not null comment 'URL描述',
   URL                  varchar(500) not null comment 'URL地址',
   THRESHOLD            numeric(6,0) not null comment 'URL阈值，单位ms',
   CREATE_TIME          datetime not null comment '创建时间',
   CREATOR_ID           varchar(32) not null comment '创建人ID',
   MODIFY_TIME          datetime comment '修改时间',
   MODIFIER_ID          varchar(32) comment '修改人ID',
   STATUS               varchar(1) not null default '1' comment '有效状态（1--有效 0--删除）',
   primary key (ID)
);

alter table GE_MONITOR_URL comment '应用系统URL信息表';

/*==============================================================*/
/* Table: GE_MONITOR_URL_METHOD                                 */
/*==============================================================*/
create table GE_MONITOR_URL_METHOD
(
   URL_ID               varchar(32) not null comment 'URLID',
   METHOD_ID            varchar(32) not null comment '方法ID'
);

alter table GE_MONITOR_URL_METHOD comment '应用系统URL方法信息表';

/*==============================================================*/
/* Table: GE_MONITOR_URL_RESPONSE_TIME                          */
/*==============================================================*/
create table GE_MONITOR_URL_RESPONSE_TIME
(
   ID                   varchar(32) not null comment '主键ID',
   URL                  varchar(300) comment 'URL地址',
   URL_ID               varchar(32) comment 'URL信息ID',
   MIN_RESPONSE_TIME    numeric(8,0) comment '最小响应时间',
   MAX_RESPONSE_TIME    numeric(8,0) comment '最大响应时间',
   TOTAL_RESPONSE_TIME  numeric(8,0) comment '总响应时间',
   TOTAL_COUNT          numeric(8,0) comment '总次数',
   RECORD_TIME          datetime comment '记录时间',
   APPLICATION_ID       varchar(32) comment '所属应用系统',
   primary key (ID)
);

alter table GE_MONITOR_URL_RESPONSE_TIME comment 'URL响应时间';

/*==============================================================*/
/* Table: GE_MONITOR_URL_TRACE_LOG                              */
/*==============================================================*/
create table GE_MONITOR_URL_TRACE_LOG
(
   ID                   varchar(32) not null comment '主键ID',
   URL                  varchar(500) not null comment 'URL地址',
   URL_ID               varchar(32) comment 'URL信息ID',
   BIZ_SCENARIO_ID      varchar(32) comment '所属业务场景ID',
   BEGIN_TIME           datetime comment 'URL开始时间',
   END_TIME             datetime comment 'URL结束时间',
   CONSUME_TIME         numeric(8,0) comment 'URL执行时间',
   SESSION_ID           varchar(32) comment '会话ID',
   USER_ID              varchar(32) comment '用户ID',
   USER_IP              varchar(100) comment '用户IP',
   TRACE_ID             varchar(32) comment '追踪ID',
   REQUEST_PARAMS       text comment '请求参数信息',
   ALARM_ID             varchar(32) comment '告警信息ID',
   RECORD_TIME          datetime comment '日志记录时间',
   USERNAME             varchar(200) comment '用户名',
   primary key (ID)
);

alter table GE_MONITOR_URL_TRACE_LOG comment '记录应用系统URL追踪日志信息';

/*==============================================================*/
/* Table: GE_MONITOR_URL_VISITS_STA                             */
/*==============================================================*/
create table GE_MONITOR_URL_VISITS_STA
(
   ID                   varchar(32) not null comment '主键ID',
   URL_ID               varchar(32) comment 'URL信息ID',
   VISIT_NUMBER         numeric(8,0) comment '访问量',
   RECORD_TIME          datetime comment '记录时间',
   APPLICATION_ID       varchar(32) comment '所属应用系统ID',
   primary key (ID)
);

alter table GE_MONITOR_URL_VISITS_STA comment '应用系统URL访问量统计';

/*==============================================================*/
/* Table: GE_MONITOR_ACCOUNT                                    */
/*==============================================================*/
create table GE_MONITOR_ACCOUNT
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

alter table GE_MONITOR_ACCOUNT comment '用户管理';

alter table GE_MONITOR_BIZ_SCENARIO add constraint FK_BUSINESS_SCENARIO foreign key (APPLICATION_ID)
      references ge_monitor_application (ID) on delete restrict on update restrict;

alter table GE_MONITOR_BIZ_SCENARIO_URL add constraint FK_BIZ_SCENARIO_URL_BIZ foreign key (BIZ_SCENARIO_ID)
      references GE_MONITOR_BIZ_SCENARIO (ID) on delete restrict on update restrict;

alter table GE_MONITOR_BIZ_SCENARIO_URL add constraint FK_BIZ_SCENARIO_URL_URL foreign key (URL_ID)
      references GE_MONITOR_URL (ID) on delete restrict on update restrict;

alter table GE_MONITOR_EUM_URL_AVA add constraint FK_EUM_URL_AVA_EUM_URL foreign key (EUM_URL_ID)
      references GE_MONITOR_EUM_URL (ID) on delete restrict on update restrict;

alter table GE_MONITOR_EUM_URL_AVA_STA add constraint FK_EUM_URL_AVA_STA_EUM_URL foreign key (EUM_URL_ID)
      references GE_MONITOR_EUM_URL (ID) on delete restrict on update restrict;

alter table GE_MONITOR_ORACLE_AVA add constraint FK_ava_databaseid_fk_info_id foreign key (DATABASE_ID)
      references GE_MONITOR_ORACLE_INFO (ID) on delete restrict on update restrict;

alter table GE_MONITOR_ORACLE_AVA_STA add constraint FK_ava_sta_databaseid_fk_info_id foreign key (DATABASE_ID)
      references GE_MONITOR_ORACLE_INFO (ID) on delete restrict on update restrict;

alter table GE_MONITOR_ORACLE_EVENT_STA add constraint FK_sta_databaseid_fk_info_id foreign key (DATABASE_ID)
      references GE_MONITOR_ORACLE_INFO (ID) on delete restrict on update restrict;

alter table GE_MONITOR_ORACLE_LASTEVENT add constraint FK_lastevent_databaseid_fk_info_id foreign key (DATABASE_ID)
      references GE_MONITOR_ORACLE_INFO (ID) on delete restrict on update restrict;

alter table GE_MONITOR_OS_AVAILABLE add constraint FK_GE_MONIT_AvALIABLE_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_OS_AVAILABLETEMP add constraint FK_GE_MONIT_AVAILABLET_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_OS_CPU add constraint FK_GE_MONIT_CPU_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_OS_DISK add constraint FK_GE_MONIT_DISK_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_OS_RAM add constraint FK_GE_MONIT_RAM_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_OS_RESPONDTIME add constraint FK_GE_MONIT_RESPOTIME_GE_MONIT foreign key (OS_INFO_ID)
      references GE_MONITOR_OS (OS_INFO_ID) on delete cascade;

alter table GE_MONITOR_URL_METHOD add constraint FK_URL_METHOD_METHOD foreign key (METHOD_ID)
      references GE_MONITOR_METHOD (ID) on delete restrict on update restrict;

alter table GE_MONITOR_URL_METHOD add constraint FK_URL_METHOD_URL foreign key (URL_ID)
      references GE_MONITOR_URL (ID) on delete restrict on update restrict;



-- ----------------------------
-- add by tuxedo
--  Table structure for GE_MONITOR_APP_SERVER
-- ----------------------------
DROP TABLE IF EXISTS GE_MONITOR_APP_SERVER;
CREATE TABLE GE_MONITOR_APP_SERVER (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_CLT;
CREATE TABLE GE_MONITOR_TUX_CLT (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_CLT_STATS;
CREATE TABLE GE_MONITOR_TUX_CLT_STATS (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_QUE;
CREATE TABLE GE_MONITOR_TUX_QUE (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_QUE_STATS;
CREATE TABLE GE_MONITOR_TUX_QUE_STATS (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_RESOURCE;
CREATE TABLE GE_MONITOR_TUX_RESOURCE (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_SETTING;
CREATE TABLE GE_MONITOR_TUX_SETTING (
  siteName varchar(255) COLLATE utf8_bin NOT NULL,
  siteType varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '站点类型',
  siteSetting longtext COLLATE utf8_bin COMMENT '站点设置',
  flag varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (siteName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;


-- ----------------------------
--  Table structure for GE_MONITOR_TUX_SVR
-- ----------------------------
DROP TABLE IF EXISTS GE_MONITOR_TUX_SVR;
CREATE TABLE GE_MONITOR_TUX_SVR (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_SVR_STATS;
CREATE TABLE GE_MONITOR_TUX_SVR_STATS (
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
DROP TABLE IF EXISTS GE_MONITOR_TUX_SYSRECS;
CREATE TABLE GE_MONITOR_TUX_SYSRECS (
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


-- ----------------------------
--  `GE_MONITOR_ACCOUNT` 用户登陆
-- ----------------------------
DROP TABLE IF EXISTS ge_monitor_account_group;
CREATE TABLE ge_monitor_account_group (
  user_id varchar(32) COLLATE utf8_bin NOT NULL,
  group_id bigint(20) NOT NULL,
  KEY `FKFE85CB3EDE3FB930` (group_id),
  KEY `FKFE85CB3E836A7D10` (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

BEGIN;
INSERT INTO ge_monitor_account_group VALUES ('1', '1');
COMMIT;

BEGIN;
INSERT INTO GE_MONITOR_ACCOUNT VALUES ('1', 'admin', 'admin', 'Admin', 'admin@springside.org.cn', '13800138001', '2012-12-25 19:36:34', '1');
COMMIT;



DROP TABLE IF EXISTS ge_monitor_group;
CREATE TABLE ge_monitor_group (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `ge_monitor_group`
-- ----------------------------
BEGIN;
INSERT INTO ge_monitor_group VALUES ('1', 'admin'), ('2', 'user');
COMMIT;

-- ----------------------------
--  `GE_MONITOR_ATTRIBUTE` 监控属性
-- ----------------------------
BEGIN;
INSERT INTO GE_MONITOR_ATTRIBUTE VALUES ('1', 'APPLICATION', 'ResponseTime', 'ms', '响应时间'),
('10', 'DB', 'Availability', null, '可用性'),
('11', 'DB', 'ResponseTime', 'ms', '响应时间'),
('12', 'DB', 'Health', null, '健康度'),
('13', 'OS', 'Availability', null, '可用性'),
('14', 'OS', 'DiskUtilization', null, '磁盘使用率'),
('15', 'OS', 'ResponseTime', 'ms', '响应时间'),
('16', 'DB', 'BufferHitRatio', null, '缓存击中率'),
('17', 'OS', 'CPUUtilization', null, 'CPU利用率'),
('18', 'APP_SERVER', 'ServerDied', null, '宕机'),
('19', 'APP_SERVER', 'ServerNoTrans', '', '无交易'),
('2', 'APPLICATION', 'Exception', null, '异常'),
('20', 'APP_SERVER', 'ServerBusy', '', '服务繁忙'),
('21', 'APP_SERVER', 'UsedMemory', '', '已用内存'),
('22', 'APP_SERVER', 'QueuedNumber', '', '队列大小'),
('23', 'APP_SERVER', 'CPUUtilization', '', 'CPU利用率'),
('24', 'APP_SERVER', 'SystemStop', null, '系统停止'),
('3', 'APPLICATION', 'Availability', null, '可用性'),
('4', 'APPLICATION', 'Health', null, '健康度'),
('5', 'OS', 'PhysicalMemoryUtilization', null, '物理内存'),
('6', 'OS', 'Health', null, '健康度'),
('7', 'OS', 'SwapMemoryUtilization', null, '交换内存'),
('8', 'DB', 'Sql', null, 'SQL'),
('9', 'DB', 'ActiveConnection', null, '连接数');
COMMIT;