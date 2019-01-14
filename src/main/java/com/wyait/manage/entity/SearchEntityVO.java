package com.wyait.manage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wyait.manage.config.JacksonConfig;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/30
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */
public class SearchEntityVO {
    private String code;
    private String name;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startCreateTime;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endCreateTime;


    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startSignDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endSignDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDeclareDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDeclareDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOutboundDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startInboundDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOutboundDate;

    @JsonFormat(pattern = JacksonConfig.DEFAULT_DATE_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endInboundDate;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(LocalDate startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public LocalDate getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(LocalDate endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public LocalDate getStartSignDate() {
        return startSignDate;
    }

    public void setStartSignDate(LocalDate startSignDate) {
        this.startSignDate = startSignDate;
    }

    public LocalDate getEndSignDate() {
        return endSignDate;
    }

    public void setEndSignDate(LocalDate endSignDate) {
        this.endSignDate = endSignDate;
    }

    public LocalDate getEndDeclareDate() {
        return endDeclareDate;
    }

    public void setEndDeclareDate(LocalDate endDeclareDate) {
        this.endDeclareDate = endDeclareDate;
    }

    public LocalDate getStartDeclareDate() {
        return startDeclareDate;
    }

    public void setStartDeclareDate(LocalDate startDeclareDate) {
        this.startDeclareDate = startDeclareDate;
    }



    public LocalDate getStartOutboundDate() {
        return startOutboundDate;
    }

    public void setStartOutboundDate(LocalDate startOutboundDate) {
        this.startOutboundDate = startOutboundDate;
    }

    public LocalDate getStartInboundDate() {
        return startInboundDate;
    }

    public void setStartInboundDate(LocalDate startInboundDate) {
        this.startInboundDate = startInboundDate;
    }

    public LocalDate getEndOutboundDate() {
        return endOutboundDate;
    }

    public void setEndOutboundDate(LocalDate endOutboundDate) {
        this.endOutboundDate = endOutboundDate;
    }

    public LocalDate getEndInboundDate() {
        return endInboundDate;
    }

    public void setEndInboundDate(LocalDate endInboundDate) {
        this.endInboundDate = endInboundDate;
    }
}
