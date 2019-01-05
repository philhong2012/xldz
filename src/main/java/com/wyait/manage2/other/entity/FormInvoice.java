package com.wyait.manage2.other.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 关联售货合同
     */
    private String sellingContractId;

    /**
     * 编码
     */
    private String code;
    //@TableField(exist = false)
    private String buyer;
    //@TableField(exist = false)
    private String seller;

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

    private String createUserName;


    private String updateUserName;


}
