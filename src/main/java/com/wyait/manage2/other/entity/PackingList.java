package com.wyait.manage2.other.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 装箱单
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PackingList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 关联售货合同
     */
    private String sellingContractId;

    private LocalDate packingDate;

    private BigDecimal totalQuantity;

    private BigDecimal totalGrossWeight;

    /**
     * 创建部门
     */
    private String deptId;

    private BigDecimal totalPackages;

    private BigDecimal totalNetWeight;

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

    private String createUserName;

    private String updateUserName;


}
