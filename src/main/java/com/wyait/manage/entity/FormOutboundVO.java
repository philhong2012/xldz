package com.wyait.manage.entity;

import com.wyait.manage2.other.entity.FormOutbound;
import com.wyait.manage2.other.entity.FormOutboundDetail;
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
public class FormOutboundVO {
    private FormOutbound contract;

    private List<FormOutboundDetail> details;

    private List<FormOutboundDetail> toDeletes;

    public FormOutbound getContract() {
        return contract;
    }

    public void setContract(FormOutbound contract) {
        this.contract = contract;
    }

    public List<FormOutboundDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FormOutboundDetail> details) {
        this.details = details;
    }

    public List<FormOutboundDetail> getToDeletes() {
        return toDeletes;
    }

    public void setToDeletes(List<FormOutboundDetail> toDeletes) {
        this.toDeletes = toDeletes;
    }
}
