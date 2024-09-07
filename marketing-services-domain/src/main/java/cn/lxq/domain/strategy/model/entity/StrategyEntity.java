package cn.lxq.domain.strategy.model.entity;

import cn.lxq.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  StrategyEntity {
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 抽奖规则模型 rule_weight,rule_blacklist */
    private String ruleModels;

    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        // 确保 ruleModels() 返回的数组不为 null
        String[] ruleModels = this.ruleModels();

        // 如果 ruleModels 为 null 或空数组，则直接返回 null
        if (ruleModels == null || ruleModels.length == 0) {
            return null; // 或者返回默认值，例如 "" 或 "default_rule"
        }

        // 遍历数组，寻找 "rule_weight"
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) {
                return ruleModel;
            }
        }

        // 如果未找到 "rule_weight"，返回 null
        return null;
    }
}
