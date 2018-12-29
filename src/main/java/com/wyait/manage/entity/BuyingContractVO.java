package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.BuyingContractDetail;
import com.wyait.manage2.other.entity.FormBuyingContract;

import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/29
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class BuyingContractVO {
    private FormBuyingContract contract;

    private List<BuyingContractDetail> details;

    public FormBuyingContract getContract() {
        return contract;
    }

    public void setContract(FormBuyingContract contract) {
        this.contract = contract;
    }

    public List<BuyingContractDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BuyingContractDetail> details) {
        this.details = details;
    }
}
