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
 * 出库单
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormOutbound implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 关联售货合同
     */
    private String sellingContractId;

    /**
     * 合同编号
     */
    private String code;

    /**
     * 供货厂家
     */
    private String provider;

    /**
     * 销售客户
     */
    private String customer;

    /**
     * 总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 数量单位
     */
    private String quantityUnit;

    private String priceUnit;

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

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 入库日期
     */
    private LocalDate inboundDate;

    /**
     * 出库日期
     */
    private LocalDate outboundDate;

}
