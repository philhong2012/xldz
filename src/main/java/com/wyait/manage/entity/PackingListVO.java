package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.BuyingContractDetail;
import com.wyait.manage2.other.entity.FormBuyingContract;
import com.wyait.manage2.other.entity.PackingList;
import com.wyait.manage2.other.entity.PackingListDetail;

import java.util.List;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/29
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class PackingListVO {
    private PackingList contract;

    private List<PackingListDetail> details;

    public PackingList getContract() {
        return contract;
    }

    public void setContract(PackingList contract) {
        this.contract = contract;
    }

    public List<PackingListDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PackingListDetail> details) {
        this.details = details;
    }
}
