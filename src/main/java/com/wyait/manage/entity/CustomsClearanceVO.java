package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.BuyingContractDetail;
import com.wyait.manage2.other.entity.CustomsClearance;
import com.wyait.manage2.other.entity.CustomsClearanceDetail;
import com.wyait.manage2.other.entity.FormBuyingContract;

import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/29
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class CustomsClearanceVO {
    private CustomsClearance contract;

    private List<CustomsClearanceDetail> details;

    private List<CustomsClearanceDetail>  toDeletes;

    public CustomsClearance getContract() {
        return contract;
    }

    public void setContract(CustomsClearance contract) {
        this.contract = contract;
    }

    public List<CustomsClearanceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<CustomsClearanceDetail> details) {
        this.details = details;
    }

    public List<CustomsClearanceDetail> getToDeletes() {
        return toDeletes;
    }

    public void setToDeletes(List<CustomsClearanceDetail> toDeletes) {
        this.toDeletes = toDeletes;
    }
}
