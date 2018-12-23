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
 * 合同明细
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SellingContractDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 售货合同id
     */
    private String sellingContractId;

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
