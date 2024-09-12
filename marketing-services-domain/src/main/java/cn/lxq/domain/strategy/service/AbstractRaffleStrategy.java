package cn.lxq.domain.strategy.service;
/**
 * 抽奖策略抽象类
 */

import cn.lxq.domain.strategy.model.entity.RaffleAwardEntity;
import cn.lxq.domain.strategy.model.entity.RaffleFactorEntity;
import cn.lxq.domain.strategy.model.entity.RuleActionEntity;
import cn.lxq.domain.strategy.model.entity.StrategyEntity;
import cn.lxq.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.lxq.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.lxq.domain.strategy.repository.IStrategyRepository;
import cn.lxq.domain.strategy.service.armory.IStrategyDispatch;
import cn.lxq.domain.strategy.service.rule.factory.DefaultLogicFactory;
import cn.lxq.types.enums.ResponseCode;
import cn.lxq.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务
    protected IStrategyRepository repository;

    // 策略调度服务
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();

        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 策略查询
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        if (strategy == null) {
            throw new AppException(ResponseCode.STRATEGY_NOT_FOUND.getCode(), "策略未找到");
        }

        // 3. 抽奖前 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(
                RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(),
                strategy.ruleModels()
        );

        if (ruleActionEntity != null && RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())) {
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 黑名单返回固定的奖品ID
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData() != null ? ruleActionEntity.getData().getAwardId() : null)
                        .build();
            } else if (DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 权重根据返回的信息进行抽奖
                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
                String ruleWeightValueKey = (raffleBeforeEntity != null) ? raffleBeforeEntity.getRuleWeightValueKey() : null;
                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awardId)
                        .build();
            }
        }

        // 4. 默认抽奖流程
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        if (awardId == null) {
            throw new AppException(ResponseCode.AWARD_NOT_FOUND.getCode(), "奖品未找到");
        }

        // 5. 查询奖品规则
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (strategyAwardRuleModelVO == null) {
            throw new AppException(ResponseCode.AWARD_RULE_NOT_FOUND.getCode(), "奖品规则未找到");
        }

        // 6. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(
                RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId)
                        .build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList()
        );

        if (ruleActionCenterEntity != null && RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())) {
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }

        // 7. 返回奖品结果
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    // 抽象方法：抽奖前规则检查
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    // 抽象方法：抽奖中规则检查
    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}