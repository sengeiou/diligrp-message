/*==============================================================*/
/* Table: message_market_channel                                */
/*==============================================================*/
drop table if exists message_market_channel;
create table message_market_channel
(
   id                   bigint not null auto_increment comment 'ID',
   market_code          varchar(20) not null comment '市场编码',
   channel              varchar(20) comment '通道编码',
   signature            varchar(50) comment '通道签名',
   company_name         varchar(50) comment '企业名称',
   access_key           varchar(50) comment '通道账号',
   secret               varchar(255) comment '通道密码',
   created              datetime not null default current_timestamp comment '创建时间',
   modified             datetime not null default current_timestamp on update current_timestamp comment '修改时间',
   primary key (id)
);
alter table message_market_channel comment '市场通道信息';
/*==============================================================*/
/* Index: idx_market_channel_market_code                        */
/*==============================================================*/
create index idx_market_channel_market_code on message_market_channel
(
   market_code
);

/*==============================================================*/
/* Table: message_triggers                                      */
/*==============================================================*/
drop table if exists message_triggers;
create table message_triggers
(
   id                   bigint not null auto_increment comment 'ID',
   trigger_code         varchar(50) not null comment '触发点编码',
   market_code          varchar(20) not null comment '市场编码',
   system_code          varchar(20) comment '所属系统',
   scene_code           varchar(50) comment '应用场景',
   whitelist            tinyint comment '是否启用白名单',
   enabled              tinyint comment '是否启用',
   created              datetime default current_timestamp comment '创建时间',
   modified             datetime default current_timestamp on update current_timestamp comment '修改时间',
   primary key (id)
);
alter table message_triggers comment '消息触发点管理';

/*==============================================================*/
/* Index: idx_triggers_market_code                              */
/*==============================================================*/
create index idx_triggers_market_code on message_triggers
(
   market_code
);
/*==============================================================*/
/* Index: idx_triggers_trigger_code                             */
/*==============================================================*/
create index idx_triggers_trigger_code on message_triggers
(
   trigger_code
);
/*==============================================================*/
/* Index: idx_triggers_system_code                              */
/*==============================================================*/
create index idx_triggers_system_code on message_triggers
(
   system_code
);
/*==============================================================*/
/* Index: idx_triggers_scene_code                               */
/*==============================================================*/
create index idx_triggers_scene_code on message_triggers
(
   scene_code
);
/*==============================================================*/
/* Table: message_triggers_template                             */
/*==============================================================*/
drop table if exists message_triggers_template;
create table message_triggers_template
(
   id                   bigint not null auto_increment comment 'ID',
   trigger_code         varchar(50) not null comment '消息触发点',
   market_channel_ids    varchar(255) comment '市场通道ID(如果多个，用#号隔开)',
   channel              varchar(20) comment '模板通道',
   template_name        varchar(50) comment '模板名称',
   template_code        varchar(20) comment '模板编码',
   template_content     varchar(255) comment '模板内容',
   created              datetime not null default current_timestamp comment '创建时间',
   modified             datetime not null default current_timestamp on update current_timestamp comment '修改时间',
   primary key (id)
);
alter table message_triggers_template comment '触发点模板';
/*==============================================================*/
/* Index: idx_template_trigger_code                             */
/*==============================================================*/
create index idx_template_trigger_code on message_triggers_template
(
   trigger_code
);

/*==============================================================*/
/* Table: message_whitelist                                     */
/*==============================================================*/
drop table if exists message_whitelist;
create table message_whitelist
(
   id                   bigint not null auto_increment comment 'ID',
   market_code          varchar(20) not null comment '所属市场',
   customer_name        varchar(20) not null comment '客户姓名',
   cellphone            varchar(20) not null comment '客户手机号',
   start_date           datetime not null comment '开始日期',
   end_date             datetime not null comment '结束日期',
   source               varchar(20) comment '信息来源(系统、手动添加?具体参考枚举定义)',
   source_id            bigint comment '数据如果来源于系统，则记录对应系统中的数据id',
   status               integer comment '白名单状态',
   deleted              tinyint comment '是否删除(1-是;0-否)',
   created              datetime not null default current_timestamp comment '创建时间',
   modified             datetime not null default current_timestamp on update current_timestamp comment '修改时间',
   primary key (id)
);
alter table message_whitelist comment '白名单信息管理';
/*==============================================================*/
/* Index: idx_whitelist_cellphone                               */
/*==============================================================*/
create index idx_whitelist_cellphone on message_whitelist
(
   cellphone
);


/*==============================================================*/
/* Table: message_send_log                                      */
/*==============================================================*/
drop table if exists message_send_log;
create table message_send_log
(
   id                   bigint not null auto_increment comment 'ID',
   request_code         varchar(50) comment '此次消息请求码',
   market_code          varchar(20) comment '来源市场',
   system_code          varchar(20) comment '来源系统',
   scene_code           varchar(50) comment '应用场景',
   cellphone            varchar(1000) comment '电话号码,多个以英文逗号分隔',
   receipt_time         datetime comment '接收时间',
   parameters           json comment '请求参数',
   template_code        varchar(20) COMMENT '指定发送的模板编码',
   content              varchar(255) comment '消息内容',
   send_state           tinyint comment '发送状态',
   send_time            datetime comment '发送时间',
   send_channel         varchar(20) comment '发送通道',
   request_id           varchar(50) comment '请求ID',
   biz_id               varchar(50) comment '发送回执ID',
   remarks              varchar(255) comment '备注',
   primary key (id)
);
alter table message_send_log comment '消息发送日志';

/*==============================================================*/
/* Index: idx_send_log_send_time                                */
/*==============================================================*/
create index idx_send_log_send_time on message_send_log
(
   send_time
);
/*==============================================================*/
/* Index: idx_send_log_scene_code                               */
/*==============================================================*/
create index idx_send_log_scene_code on message_send_log
(
   scene_code
);
/*==============================================================*/
/* Index: idx_send_log_cellphone                                */
/*==============================================================*/
create index idx_send_log_cellphone on message_send_log
(
   cellphone
);