package com.wyait.manage2.other.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 业务结算卡
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SettlementList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 销售收入
     */
    private BigDecimal sellingIncome;

    /**
     * 销售收入rmb
     */
    private BigDecimal sellingIncomeRmb;

    /**
     * 代购代销收入
     */
    private BigDecimal dgdxIncome;

    /**
     * 退税收入
     */
    private BigDecimal taxRefund;

    /**
     * 工厂退款
     */
    private BigDecimal factoryRefund;

    /**
     * 支出退款
     */
    private BigDecimal payRefund;

    /**
     * 支出运杂费
     */
    private BigDecimal payOtherRefund;

    /**
     * 支出保险费
     */
    private BigDecimal payInsurance;

    /**
     * 支出银行费用
     */
    private BigDecimal payBank;

    /**
     * 利息支出
     */
    private BigDecimal payIntrest;

    /**
     * 减间接费用
     */
    private BigDecimal payJjj;

    /**
     * 毛利润额
     */
    private BigDecimal grossProfit;

    /**
     * 利润净额
     */
    private BigDecimal netProfit;

    /**
     * 主管
     */
    private String masterUserId;

    /**
     * 经办人
     */
    private String jbUserId;

    /**
     * 主管人名
     */
    private String masterUserName;

    /**
     * 经办人名称
     */
    private String jbUserName;

    /**
     * 缴纳税费
     */
    private BigDecimal payTax;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    private BigDecimal payOther;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    private String updateUserId;

    private String approvalUserId;

    private String approvalUserName;

    /**
     * 更新人名称
     */
    private String updateUserName;

    private LocalDateTime updateTime;

    private String remark;

    private LocalDate settleDate;


}
