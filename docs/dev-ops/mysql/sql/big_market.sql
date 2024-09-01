create table strategy
(
    id            int unsigned auto_increment comment '自增id'
        primary key,
    strategy_id   int                                not null comment '抽奖策略id',
    strategy_desc varchar(128)                       not null comment '抽奖策略描述',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    rule_models   varchar(256)                       null comment '策略模型'
);

create table strategy_award
(
    id                  int unsigned auto_increment comment '自增id'
        primary key,
    strategy_id         int                                not null comment '抽奖策略id',
    award_id            int                                not null comment '抽奖奖品id',
    award_count         int                                not null comment '奖品库存总量',
    award_count_surplus int                                not null comment '奖品库存剩余',
    award_rate          decimal(6, 4)                      not null comment '奖品中奖概率',
    create_time         datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time         datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    sort                int      default 0                 not null comment '排序',
    award_subtitle      varchar(128)                       null comment '抽奖奖品的副标题',
    award_title         varchar(128)                       not null comment '抽奖奖品的标题',
    rule_models         varchar(256)                       null comment '规则模型，rule配置规则记录。'
);

create table strategy_rule
(
    id          int unsigned auto_increment comment '自增id'
        primary key,
    strategy_id int                                not null comment '抽奖策略id',
    award_id    int                                null comment '抽奖奖品id',
    rule_type   int      default 0                 not null comment '抽奖规则类型【1-策略规则、2-奖品规则】',
    rule_model  varchar(16)                        not null comment '抽奖规则类型【rule_lock】',
    rule_value  varchar(128)                       not null comment '抽奖规则比值',
    rule_desc   varchar(128)                       not null comment '抽奖规则描述',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间'
);

CREATE TABLE `award` (
                         `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                         `award_id` int NOT NULL COMMENT '抽奖奖品ID - 内部轮流使用',
                         `award_key` varchar(32) NOT NULL COMMENT '奖品对接标识 - 每一个都是一个对应的发奖策略',
                         `award_config` varchar(32) NOT NULL COMMENT '奖品配置信息',
                         `award_desc` varchar(128) NOT NULL COMMENT '奖品内容描述',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci



