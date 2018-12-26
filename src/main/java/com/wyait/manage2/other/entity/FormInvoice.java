package com.wyait.manage2.other.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发票
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 关联售货合同
     */
    private String sellingContractId;

    /**
     * 编码
     */
    private String code;

    /**
     * 开票日期
     */
    private LocalDate invoiceDate;

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
     * 创建部门
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;


}
