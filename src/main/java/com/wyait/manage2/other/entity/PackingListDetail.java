package com.wyait.manage2.other.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 装箱单明细
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PackingListDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 售货合同id
     */
    private String packingListId;

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
     * 毛重
     */
    private BigDecimal grossWeight;

    /**
     * 净重
     */
    private BigDecimal netWeight;

    /**
     * 箱数
     */
    private BigDecimal packageQuantity;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

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
     * 创建人名称
     */
    private String createUserName;

    /**
     * 更新人名称
     */
    private String updateUserName;


}
