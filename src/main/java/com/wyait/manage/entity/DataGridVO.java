package com.wyait.manage.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/29
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public class DataGridVO<T> {
    private List<T> rows;

    private List<Map<String,String>> footer;

    private Integer total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<Map<String, String>> getFooter() {
        return footer;
    }

    public void setFooter(List<Map<String, String>> footer) {
        this.footer = footer;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
