drop table if exists sys_dic_item;

drop table if exists sys_dictionary;

/*==============================================================*/
/* Table: sys_dic_item                                          */
/*==============================================================*/
create table sys_dic_item
(
   id                   varchar(32) not null comment '主键',
   dic_id               varchar(32) comment '字典Id',
   code                 varchar(50) comment '编码',
   name                 varchar(32) comment '名称',
   value                varchar(32) comment '值',
   description          varchar(100) comment '描述',
   sort_no              numeric(3) comment '排序号',
   extend5              varchar(50) comment '扩展字段1',
   extend4              varchar(50) comment '扩展字段1',
   extend3              varchar(50) comment '扩展字段1',
   extend2              varchar(50) comment '扩展字段1',
   extend               varchar(50) comment '扩展字段1',
   create_time          datetime comment '创建日期',
   create_user_id       varchar(32) comment '创建人',
   update_time          datetime,
   update_user_id       varchar(32),
   update_user_name     varchar(50) comment '更新人名称',
   create_user_name     varchar(50) comment '创建人名称',
   primary key (id)
);

alter table sys_dic_item comment '字典项';

/*==============================================================*/
/* Table: sys_dictionary                                        */
/*==============================================================*/
create table sys_dictionary
(
   id                   varchar(32) not null comment '主键',
   parent_id            varchar(32) comment '父id',
   name                 varchar(50) comment '名称',
   code                 varchar(50) comment '编码',
   description          varchar(100) comment '描述',
   create_time          datetime comment '创建日期',
   create_user_id       varchar(32) comment '创建人',
   update_time          datetime,
   update_user_id       varchar(32),
   update_user_name     varchar(50) comment '更新人名称',
   create_user_name     varchar(50) comment '创建人名称',
   primary key (id)
);

alter table sys_dictionary comment '数据字典';
