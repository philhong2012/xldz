package com.wyait.manage2.other.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典项
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDicItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 字典Id
     */
    private String dicId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序号
     */
    private BigDecimal sortNo;

    /**
     * 扩展字段1
     */
    private String extend5;

    /**
     * 扩展字段1
     */
    private String extend4;

    /**
     * 扩展字段1
     */
    private String extend3;

    /**
     * 扩展字段1
     */
    private String extend2;

    /**
     * 扩展字段1
     */
    private String extend;

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
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 创建人名称
     */
    private String createUserName;


}
