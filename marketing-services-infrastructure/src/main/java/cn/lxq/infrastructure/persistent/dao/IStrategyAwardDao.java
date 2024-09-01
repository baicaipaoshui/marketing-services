package cn.lxq.infrastructure.persistent.dao;

import cn.lxq.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyAwardDao {
    List<Award> queryStrategyAwardList();
}
