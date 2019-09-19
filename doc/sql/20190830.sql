alter table message_send_log add column template_code varchar(20) COMMENT '指定发送的模板编码' AFTER parameters;
alter table message_send_log add column remote_ip varchar(64) COMMENT '请求来源的IP地址' AFTER cellphone;
alter table message_whitelist add column status integer COMMENT '白名单状态' AFTER source_id;
update message_whitelist mw set mw.status = 10 where mw.start_date > NOW();
update message_whitelist mw set mw.status = 30 where mw.end_date < NOW();
update message_whitelist mw set mw.status = 20 where mw.end_date > NOW() AND mw.start_date < NOW();