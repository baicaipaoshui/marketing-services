package cn.lxq.domain.strategy.service;

import cn.lxq.domain.strategy.model.entity.RaffleAwardEntity;
import cn.lxq.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * 抽奖策略接口
 */
public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
