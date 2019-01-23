package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.BuyingContractDetail;
import com.wyait.manage2.other.entity.ExportGoodsList;
import com.wyait.manage2.other.entity.ExportGoodsListDetail;
import com.wyait.manage2.other.entity.FormBuyingContract;

import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/29
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class ExportGoodsListVO {
    private ExportGoodsList contract;

    private List<ExportGoodsListDetail> details;

    private List<ExportGoodsListDetail> toDeletes;

    public ExportGoodsList getContract() {
        return contract;
    }

    public void setContract(ExportGoodsList contract) {
        this.contract = contract;
    }

    public List<ExportGoodsListDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ExportGoodsListDetail> details) {
        this.details = details;
    }

    public List<ExportGoodsListDetail> getToDeletes() {
        return toDeletes;
    }

    public void setToDeletes(List<ExportGoodsListDetail> toDeletes) {
        this.toDeletes = toDeletes;
    }
}
