package com.wyait.manage2.other.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 出口货物单
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExportGoodsList implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 收购合同id
     */
    private String buyingContractId;

    /**
     * 售货合同id
     */
    private String sellingContractId;

    /**
     * 收购合同编码
     */
    private String buyingContractNo;

    /**
     * 售货合同编码
     */
    private String sellingContractNo;

    /**
     * 装运口岸
     */
    private String packingKouAn;

    /**
     * 目的口岸
     */
    private String sendingKouAn;

    /**
     * 成交价格
     */
    private BigDecimal dealPrice;

    /**
     * 创建部门
     */
    private String deptName;

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
