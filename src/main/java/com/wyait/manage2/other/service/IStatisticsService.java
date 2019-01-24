package com.wyait.manage2.other.service;

import java.time.LocalDate;
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
public interface IStatisticsService {
    @Deprecated
    List<Map<String,Object>> getTurnoverExportCostProfit(LocalDate start,
                                                         LocalDate end,
                                                         String deptId);


    List<Map<String,Object>>  getTurnOverExportAmountProfit(LocalDate start, LocalDate end, String deptId);

}
