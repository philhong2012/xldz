package com.wyait.manage2.other.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售货合同
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormSellingContract implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 买方
     */
    private String buyer;

    /**
     * 买方英文
     */
    private String buyerEn;

    /**
     * 卖方
     */
    private String seller;

    /**
     * 卖方英文
     */
    private String sellerEn;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 签约地
     */
    private String signAddress;

    /**
     * 签订日期
     */
    private Long signDate;

    /**
     * 包装
     */
    private String packing;

    /**
     * 装运唛头
     */
    private String packingMaiTou;

    /**
     * 装运口岸
     */
    private String packingKouAn;

    /**
     * 目的口岸
     */
    private String sendingKouAn;

    /**
     * 装运期限天
     */
    private String packingExpiredDays;

    /**
     * 装运期限截止日期
     */
    private Long packingExpiredDate;

    /**
     * 保险方式
     */
    private String insuranceType;

    /**
     * 付款方式
     */
    private String payingType;

    /**
     * 付款期限
     */
    private Long payingExpiredDate;

    /**
     * 装运单据
     */
    private String packingForms;

    /**
     * 是否出品质/数量证明
     */
    private String checkingQuality;

    /**
     * 创建部门
     */
    private Long deptId;

    /**
     * 创建日期
     */
    private Long createTime;

    /**
     * 创建人
     */
    private Long createUserId;


}
