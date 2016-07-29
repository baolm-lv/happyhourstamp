package com.lvmama.vst.hhs.downgrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DownGradeServiceImpl implements DownGradeService {

	private Logger LOGGER = LoggerFactory.getLogger(DownGradeServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional("transactionManager")
	public List<String> getDownDradeList(String serviceName) {
		String query = "SELECT METHOD_NAME FROM down_grade_method "
				+ "where APP_NAME=? and DISABLED=? and IP_ADDRESS is null or IP_ADDRESS=?";

		List<String> rows = new ArrayList<>();
		try {
			rows = jdbcTemplate.queryForList(query, new Object[] { serviceName, "Y", IPUtil.getIpAddress() }, String.class);
		} catch (Exception e) {
		}
		return rows;

	}

	// be carefull METHOD_NAME include path and method
	@Override
	@Transactional("transactionManager")
	public void downGradeByMethods(List<String> methods, String sericeName, boolean local) {
		// update or insert into down_grade_method where app_name='hhs' and
		if (CollectionUtils.isEmpty(methods))
			return;

		String sql = "insert into down_grade_method(ID,APP_NAME,METHOD_NAME,DISABLED,UPDATE_DATE,CREATE_DATE,IP_ADDRESS) values";

		String [] valueClauses = new String[methods.size()];
		Arrays.fill(valueClauses, "(?,?,?,?,?,?,?)");
		
		sql += StringUtils.join(valueClauses, ',');
		
		Date now = new Date();
		
		List<Object> values = new ArrayList<Object>();
		for (String method : methods) {
			values.add(UUID.randomUUID().toString());
			values.add(sericeName);
			values.add(method);
			values.add("Y");
			values.add(now);
			values.add(now);
			values.add(local ? IPUtil.getIpAddress() : null);
		}

		LOGGER.info("down grade by method, sql: {}", sql);
		jdbcTemplate.update(sql, values.toArray(new Object[values.size()]));
	}

	@Override
	public void downGradeByIds(List<String> ids, boolean local) {
		updateDownGradeStatus(ids, "Y", local);
	}

	@Override
	public void recoverByIds(List<String> ids, boolean local) {
		updateDownGradeStatus(ids, "N", local);
	}

	@Transactional("transactionManager")
	private void updateDownGradeStatus(List<String> ids, String disabled, boolean local) {
		if (CollectionUtils.isEmpty(ids))
			return;

		String [] idClauses = new String[ids.size()];
		Arrays.fill(idClauses, "?");

		String sql = "update down_grade_method set DISABLED=? where ID in (" + StringUtils.join(idClauses, ',') + ")" 
				+ (local ? " and IP_ADDRESS=?" : "");

		LOGGER.info("update down grade by Id, sql: {}", sql);
		
		List<Object> params = new ArrayList<Object>();
		params.add(disabled);
		params.addAll(ids);
		if(local) 
			params.add(IPUtil.getIpAddress());
		jdbcTemplate.update(sql, params.toArray(new Object[params.size()]));
	}

	@Override
	@Transactional("transactionManager")
	public Map<String, Map<String, String>> getDownGradeMap(String serviceName) {
		String query = "SELECT ID,METHOD_NAME,DISABLED FROM down_grade_method where APP_NAME=?";
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, serviceName);
			for (Map<String, Object> row : rows) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", row.get("ID").toString());
				m.put("disabled", row.get("DISABLED").toString());
				map.put(row.get("METHOD_NAME").toString(), m);
			}
		} catch (Exception e) {
			LOGGER.error("error when call db", e);
		}
		return map;
	}
	
	@Override
	@Transactional("transactionManager")
	public void recoverByService(String serviceName) {
		if(StringUtils.isBlank(serviceName))
			return ;
		
		String sql = "delete from down_grade_method where APP_NAME=?";
		jdbcTemplate.update(sql, serviceName);
	}
}
