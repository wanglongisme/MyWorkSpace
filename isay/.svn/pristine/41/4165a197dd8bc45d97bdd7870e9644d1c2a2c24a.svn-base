package com.isay.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("DataDao")
public class DataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	Logger log = Logger.getRootLogger();
	//获取列表
	public List getDataList(int page) {
		int start = (page-1)*14;
		String sql = " SELECT id,name,createTime,isStartUp FROM r_da where status=1 order by id limit ?,14";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, new Object[]{start});
		return res;
	}
	//数据
	public List getList(int id, String date) {
		String sql = "SELECT dateVal, dataVal, areaId FROM r_da_data dd WHERE dd.id=? AND dateVal IN("+date+") ORDER BY dateVal,dataId";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, new Object[]{id});
		return res;
	}
	//地区列
	public List getDataAreaList(int id) {
		String area_sql = "SELECT da.areaId, a.area"
				+ " FROM r_da_area da "
				+ " LEFT JOIN r_area a ON a.areaId=da.areaId "
				+ " LEFT JOIN r_da d ON d.id=da.id "
				+ " WHERE da.id=? AND d.status=1";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(area_sql, new Object[]{id});
		return res;
	}

	//日期列
	public List getDataDateList(int id, int page) {
		int start = (page-1)*12;
		String area_sql = "SELECT dateval FROM r_da_data WHERE id=? GROUP BY dateval ORDER BY dateval LIMIT ?,12";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(area_sql, new Object[]{id, start});
		return res;
	}
}
