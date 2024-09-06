package cn.lxq.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则物料实体对象，用于过滤规则的必要参数信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {
    // 用户ID
    private String userId;
    // 策略ID
    private Long strategyId;
    // 抽奖讲评ID (规则类型为策略，则不需要奖品)
    private Integer awardId;
    // 抽奖规则类型
    private String ruleModel;
}
