package cn.lxq.domain.strategy.service.armory;

// 抽奖策略调度
public interface IStrategyDispatch {

    /**
     * 获取抽奖策略装配的随机结果
     * @param strategyId
     * @return
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

}
