/*==============================================================*/
/* Table: message_push_log                                      */
/*==============================================================*/
drop table if exists message_push_log;
create table message_push_log
(
   id                   bigint not null auto_increment comment 'ID',
   request_code         varchar(50) comment '此次推送的请求批次码',
   market_id            bigint comment '所属市场',
   user_id              bigint comment '当次推送的用户',
   registration_id      varchar(64) comment '设备注册ID',
   platform             varchar(20) comment '推送平台:android,ios 等',
   alert                varchar(255) comment '通知内容',
   title                varchar(255) comment '通知标题',
   extras               json comment '扩展内容',
   receipt_time         datetime comment '消息接收时间',
   push_state           tinyint comment '消息推送状态',
   push_time            datetime comment '消息推送时间',
   push_channel         tinyint comment '推送通道',
   message_id           varchar(20) comment '第三方消息ID',
   send_no              varchar(20) comment '第三方发送编码',
   failure_code         varchar(10) comment '失败编码',
   failure_message      varchar(100) comment '失败信息',
   primary key (id)
);
alter table message_push_log comment 'APP消息推送记录';
/*==============================================================*/
/* Index: idx_push_log_push_time                                */
/*==============================================================*/
create index idx_push_log_push_time on message_push_log
(
   push_time
);