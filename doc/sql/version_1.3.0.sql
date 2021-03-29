ALTER TABLE `message_center`.`message_send_log` ADD COLUMN `business_market_code` VARCHAR(20) DEFAULT NULL COMMENT '产生业务短信的市场' AFTER market_code;
update `message_center`.`message_send_log` set business_market_code = market_code;

/*==============================================================*/
/* Table: message_blacklist     黑名单数据管理                     */
/*==============================================================*/
drop table if exists `message_center`.message_blacklist;
create table `message_center`.message_blacklist
(
    id                   bigint not null auto_increment comment 'ID',
    market_code          varchar(20) not null comment '所属市场',
    customer_name        varchar(20) not null comment '客户姓名',
    cellphone            varchar(20) not null comment '客户手机号',
    start_time           datetime not null comment '开始日期',
    end_time             datetime not null comment '结束日期',
    status               int comment '黑名单状态',
    creator_id           bigint comment '创建人ID',
    created              datetime not null default current_timestamp comment '创建时间',
    modified             datetime not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id)
);
alter table `message_center`.message_blacklist comment '黑名单信息管理';
/*==============================================================*/
/* Index: idx_blacklist_cellphone                               */
/*==============================================================*/
create index idx_blacklist_cellphone on `message_center`.message_blacklist
(
   cellphone
);

alter table `message_center`.message_triggers add column blacklist tinyint COMMENT '是否应用黑名单,1-是;0-否' AFTER scene_code;
update `message_center`.message_triggers set blacklist = 0;

INSERT INTO `uap`.`data_dictionary_value` (`dd_code`,`order_number`, `name`, `code`, `description`, `created`, `modified`, `state`) VALUES ('log_business_type', 138, '短信黑名单', 'message_blacklist', '短信黑名单', now(), now(), 1);