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
 * 报关单明细
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CustomsClearanceDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 关联销售合同id
     */
    private String customsClearanceId;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 货物英文名称
     */
    private String goodsNameEn;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量单位
     */
    private String goodsUnit;

    /**
     * 价格单位
     */
    private String priceUnit;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 源产地
     */
    private String fromCountry;

    /**
     * 最终目的国
     */
    private String toCountry;

    /**
     * 境内货源地
     */
    private String innerFrom;

    /**
     * 征免
     */
    private String taxType;

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

    /**
     * 更新人名称
     */
    private String updateUserName;


}
