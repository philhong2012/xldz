package com.wyait.manage.utils;

import java.util.List;

/**
 *
 * @项目名称：wyait-manage
 * @类名称：PageDateResult
 * @类描述：封装DTO分页数据（记录数和所有记录）
 * @创建人：wyait
 * @创建时间：2017年12月31日14:49:34
 * @version：2.0.0
 */
public class EasyUIPageDataResult {

	//总记录数量
	private Integer total;
	//当前页数据列表
	private List<?> rows;

	private Integer code=200;

	public EasyUIPageDataResult() {
	}

	public EasyUIPageDataResult(Integer total,
                                List<?> rows) {
		this.total = total;
		this.rows = rows;
	}


	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
