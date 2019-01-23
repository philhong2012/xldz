package com.wyait.manage2.other.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyait.manage2.other.entity.FormSellingContract;
import com.wyait.manage2.other.mapper.FormSellingContractMapper;
import com.wyait.manage2.other.mapper.StatisticsMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/12/15
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IStatisticsServiceTest {
    @Autowired
    private IStatisticsService statisticsService;

    @Test
    public void testSelect() {
        LocalDate start = LocalDate.of(2018,1,1);
        LocalDate end = LocalDate.of(2020,1,1);
        String deptId = null;
        statisticsService.getTurnoverExportCostProfit(start,end,deptId);

    }
}