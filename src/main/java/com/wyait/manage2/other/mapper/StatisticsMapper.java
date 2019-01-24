package com.wyait.manage2.other.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyait.manage2.other.entity.ForeignExchangeAccount;

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
public interface StatisticsMapper  {
    @Deprecated
    List<Map<String,Object>> getTurnoverExportCostProfit(Map<String,Object> map);

    List<Map<String,Object>>  getTurnOverExportAmountProfit(Map<String,Object> map);

}
