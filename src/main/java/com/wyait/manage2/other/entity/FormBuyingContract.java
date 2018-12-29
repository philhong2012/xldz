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
 * 采购合同
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormBuyingContract implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 关联售货合同
     */
    private String sellingContractId;

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
    private LocalDate signDate;

    /**
     * 包装
     */
    private String packing;

    /**
     * 装运唛头
     */
    private String packingMaiTou;

    /**
     * 交货期限
     */
    private LocalDate deliveryDate;

    /**
     * 合同有效期
     */
    private LocalDate validityDate;

    /**
     * 合同额总计
     */
    private BigDecimal total;

    /**
     * 合同金额大写
     */
    private String totalCap;

    /**
     * 创建部门
     */
    private String deptId;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserId;

    private LocalDateTime updateTime;

    private String updateUserId;


}
