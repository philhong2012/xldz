package com.wyait.manage2.other.service.impl;

import com.wyait.manage2.other.mapper.StatisticsMapper;
import com.wyait.manage2.other.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 外汇台账 Mapper 接口
 * </p>
 *
 * @author hongxubing
 * @since 2019-01-05
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    StatisticsMapper mapper;

    @Override
    public List<Map<String, Object>> getTurnoverExportCostProfit(LocalDate start, LocalDate end, String deptId) {
        Map<String,Object> map = new HashMap<>();
        map.put("startDate",start);
        map.put("endDate",end);
        map.put("deptId",deptId);

        return mapper.getTurnoverExportCostProfit(map);
    }
}
