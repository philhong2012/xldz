package com.wyait.manage2.other.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wyait.manage.config.JacksonConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 售货合同
 * </p>
 *
 * @author hongxubing
 * @since 2018-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FormSellingContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 买方
     */
    private String buyer;

    /**
     * 买方英文
     */
    private String buyerEn;

    /**
     * 卖方
     */
    private String seller;

    /**
     * 卖方英文
     */
    private String sellerEn;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 签约地
     */
    private String signAddress;

    /**
     * 签订日期
     */
    private LocalDate signDate;

    /**
     * 包装
     */
    private String packing;

    /**
     * 装运唛头
     */
    private String packingMaiTou;

    /**
     * 装运口岸
     */
    private String packingKouAn;

    /**
     * 目的口岸
     */
    private String sendingKouAn;

    /**
     * 装运期限天
     */
    private String packingExpiredDays;

    /**
     * 装运类型
     */
    private String packingExpiredType;

    /**
     * 装运期限截止日期
     */
    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate packingExpiredDate;

    /**
     * 保险方式
     */
    private String insuranceType;

    /**
     * 付款方式
     */
    private String payingType;

    /**
     * 付款期限
     */
    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    private LocalDate payingExpiredDate;

    /**
     * 装运单据
     */
    private String packingForms;

    /**
     * 是否出品质/数量证明
     */
    private String checkingQuality;

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

    /**
     *
     */
    private String updateUserId;

    private String updateUserName;


    private LocalDateTime updateTime;

    private String deptName;


}
