package com.wyait.manage2.other.entity;

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
 * 报关单
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CustomsClearance implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 关联销售合同id
     */
    private String sellingContractId;

    /**
     * 境内发货人
     */
    private String innerSender;

    /**
     * 境外收货人
     */
    private String outerReceiver;

    /**
     * 出境关别
     */
    private String departurePortType;

    /**
     * 出口日期
     */
    private LocalDate exportDate;

    /**
     * 申报日期
     */
    private LocalDate declareDate;

    /**
     * 备案号
     */
    private String fillingNo;

    /**
     * 运输方式
     */
    private String transportType;

    /**
     * transport_tools_voyage_no
     */
    private String transportToolsVoyageNo;

    /**
     * bill_of_landing_no
     */
    private String billOfLandingNo;

    /**
     * 生产销售
     */
    private String producer;

    /**
     * 监管方式
     */
    private String monitorType;

    /**
     * 征免性质
     */
    private String expropriationType;

    /**
     * 许可证号
     */
    private String licenceNo;

    /**
     * 合同协议号
     */
    private String contractNo;

    /**
     * 贸易国地区
     */
    private String tradingCountryAddr;

    /**
     * 运抵国地区
     */
    private String landingCountryAddr;

    /**
     * 指运港
     */
    private String pointingPort;

    /**
     * 离境口岸
     */
    private String departurePort;

    /**
     * 包装种类
     */
    private String packingType;

    /**
     * 件数
     */
    private String quantity;

    /**
     * 毛重
     */
    private String grossWeight;

    /**
     * 净重
     */
    private String netWeight;

    /**
     * 成交方式
     */
    private String dealType;

    /**
     * 运费
     */
    private String transportCost;

    /**
     * 保费
     */
    private String insuranceCost;

    /**
     * 杂费
     */
    private String otherCost;

    /**
     * 随附单证及编号
     */
    private String attachmentDocument;

    /**
     * 标记唛码及备注
     */
    private String labelMaimaAndRemark;

    /**
     * 特殊关系确认
     */
    private String specialRelationConfirm;

    /**
     * 价格影响确认
     */
    private String priceEffectConfirm;

    /**
     * 支付特许权使用费确认
     */
    private String payConcessionCostConfirm;

    /**
     * 自报自缴
     */
    private String selfReported;

    /**
     * 报关人员
     */
    private String customsOfficer;

    /**
     * 报关人员证号
     */
    private String customsOfficerNo;

    /**
     * 电话
     */
    private String tel;

    /**
     * 申报单位
     */
    private String declareUnit;

    /**
     * 海关批注及签章
     */
    private String customsRemark;

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
     * 部门id
     */
    private String deptId;

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
