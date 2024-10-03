package cn.lxq.domain.strategy.service.rule.tree.impl;

import cn.lxq.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.lxq.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import cn.lxq.domain.strategy.repository.IStrategyRepository;
import cn.lxq.domain.strategy.service.armory.IStrategyDispatch;
import cn.lxq.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.lxq.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 库存扣减节点
 * @create 2024-01-27 11:25
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Resource
    private IStrategyDispatch strategyDispatch;

    private IStrategyRepository strategyRepository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String rulevalue) {
        log.info("规则过滤-库存扣减 userId:{} strategyId:{} awardId:{}",userId,strategyId,awardId);
        // 扣减库存
        Boolean status = strategyDispatch.substractionAwardStock(strategyId,awardId);
        // true : 扣减成功
        if(status) {
            // 写入延迟队列，延迟消费更新数据库记录。【在 trigger 的 job；UpdateAwardStockJob 下消费队列，跟新数据库记录】
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build()
            );
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue("ruleValue")
                            .build())
                    .build();
        }

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
