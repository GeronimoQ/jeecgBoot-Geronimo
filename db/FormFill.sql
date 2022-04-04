create table `jeecg-boot`.plat_fill_test
(
    id        varchar(36) not null
        primary key,
    task_id   varchar(36) null comment '任务ID',
    user_id   varchar(36) null comment '创建者ID',
    fill_date datetime    null comment '填报时间',
    form_data json        null comment '填报数据'
);

create table `jeecg-boot`.plat_formmodel_test
(
    id          varchar(36) not null
        primary key,
    user_id     varchar(36) null comment '创建人',
    create_time datetime    null comment '创建日期',
    form_instr  varchar(50) null comment '模板介绍',
    item_list   json        null comment '表单组件',
    form_config json        null comment '表单配置'
);

create table `jeecg-boot`.plat_task_test
(
    id          varchar(36)          not null
        primary key,
    user_id     varchar(36)          null comment '创建人',
    create_time datetime             null comment '创建日期',
    title       varchar(50)          null comment '标题',
    instr       varchar(50)          null comment '简介',
    model_id    varchar(36)          null comment '模板ID',
    disabled    tinyint(1) default 0 null comment '是否弃用'
);


