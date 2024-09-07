package cn.lxq.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001","业务异常，策略规则中 rule_weght 权重规则已适用但未配置"),
    STRATEGY_NOT_FOUND("ERR_BIZ_002", "策略未找到"),
    AWARD_NOT_FOUND("ERR_BIZ_003", "奖品未找到"),
    AWARD_RULE_NOT_FOUND("ERR_BIZ_004", "奖品规则未找到");
    ;

    private String code;
    private String info;

}
