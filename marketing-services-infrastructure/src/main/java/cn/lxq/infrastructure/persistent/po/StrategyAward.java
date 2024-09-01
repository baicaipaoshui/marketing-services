package cn.lxq.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/** 策略奖品明细配置 */
@Data
public class StrategyAward {
    /** '自增id'*/
    private Long id;
    /** '抽奖策略id',*/
    private Long strategyId;
    /** '抽奖奖品id'*/
    private Integer awardId;
    /**'奖品库存总量'*/
    private Integer awardCount;
    /** '奖品库存剩余'*/
    private Integer awardCountSurplus;
    /** '奖品中奖概率'*/
    private BigDecimal awardRate;
    /** '创建时间'*/
    private Date createTime;
    /** '更新时间'*/
    private Date updateTime;
    /** '排序'*/
    private Integer sort;
    /** '抽奖奖品的副标题'*/
    private String awardSubtitle;
    /** '抽奖奖品的标题'*/
    private String awardTitle;
    /** '规则模型，rule配置规则记录。'*/
    private String ruleModels;

}
