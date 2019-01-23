package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.entity.SellingContractDetail;

import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/23
 * Time: 下午8:38
 * To change this template use File | Settings | File Templates.
 */
public class SellingContractVO {
    private FormSellingContract contract;
    private List<SellingContractDetail> details;

    private List<SellingContractDetail> toDeletes;

    public FormSellingContract getContract() {
        return contract;
    }

    public void setContract(FormSellingContract contract) {
        this.contract = contract;
    }

    public List<SellingContractDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SellingContractDetail> details) {
        this.details = details;
    }

    public List<SellingContractDetail> getToDeletes() {
        return toDeletes;
    }

    public void setToDeletes(List<SellingContractDetail> toDeletes) {
        this.toDeletes = toDeletes;
    }
}
