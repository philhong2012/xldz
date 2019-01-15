package com.wyait.manage2.other.entity;

import java.beans.Transient;
import java.math.BigDecimal;
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
 * 出口货物单明细
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExportGoodsListDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 售货合同id
     */
    private String exportGoodsListId;
    /**
     * 货源
     */
    //@TableField(exist = false)
    private String goodsProducer;

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
     * 数量单位
     */
    private String goodsUnit;

    /**
     * 销售价格
     */
    private BigDecimal sellingPrice;

    /**
     * 收购价格
     */
    private BigDecimal buyingPrice;

    /**
     * 税费
     */
    private BigDecimal taxCost;

    /**
     * 可退税额
     */
    private BigDecimal refundTax;

    /**
     * 出口成本
     */
    private BigDecimal exportCost;

    /**
     * 收购价格合计
     */
    private BigDecimal subtotalBuyingPrice;

    /**
     * 销售价格合计
     */
    private BigDecimal subtotalSellingPrice;

    /**
     * 销售价格单位
     */
    private String sellingPriceUnit;

    /**
     * 退税率
     */
    private BigDecimal refundPercent;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

    private BigDecimal packageQuantity;

    /**
     * 换汇成本
     */
    private BigDecimal exchangeCost;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    /**
     * 创建部门
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

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
