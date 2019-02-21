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
 * 外汇台账
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ForeignExchangeAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 外商汇款人
     */
    private String remittance;

    private String receiver;

    /**
     * 费用
     */
    private BigDecimal amount;

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
     * 实际发生日期
     */
    private LocalDate actualDate;


}
