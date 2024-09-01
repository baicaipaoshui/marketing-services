package cn.lxq.infrastructure.persistent.po;


import lombok.Data;

import java.util.Date;

@Data
// 抽奖策略
public class Strategy {
    //自增id
    private Long id;
    //抽奖策略id
    private Long strategyId;
    //抽奖策略描述
    private String strategyDesc;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    // 策略模型
    private String ruleModels;




}
