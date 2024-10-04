package cn.lxq.domain.strategy.service.rule.tree.impl;

import cn.lxq.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.lxq.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.lxq.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);

        String[] split = ruleValue.split(Constants.COLON);
        if (split.length == 0) {
            log.error("规则过滤-兜底奖品，兜底奖品未配置告警 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);
        }

        // 去除字符串中的逗号
        String cleanedLuckAwardId = split[0].replace(",", "");
        String awardRuleValue = split.length > 1 ? split[1] : "";

        try {
            // 将清理后的字符串转换为整数
            Integer luckAwardId = Integer.valueOf(cleanedLuckAwardId);
            log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} awardRuleValue:{}", userId, strategyId, luckAwardId, awardRuleValue);

            // 返回兜底奖品
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(luckAwardId)
                            .awardRuleValue(awardRuleValue)
                            .build())
                    .build();
        } catch (NumberFormatException e) {
            log.error("规则过滤-兜底奖品，转换奖品ID失败 userId:{} strategyId:{} awardId:{} luckAwardId:{}", userId, strategyId, awardId, cleanedLuckAwardId, e);
            throw new RuntimeException("规则过滤-兜底奖品，转换奖品ID失败 " + cleanedLuckAwardId, e);
        }
    }
}