package com.cathaylife.admin.persist.provider;

import java.util.Map;
import org.apache.ibatis.jdbc.SQL;


public class SampleProvider {
	
	private static final String TABLE_NAME = "sample";
	
	/**
	 * @param parameters
	 * @return
	 */
	public String getSampleSQL(Map<String, Object> parameters) {
		SQL sql = new SQL();
		sql.SELECT("id, text")
		.FROM(TABLE_NAME)
		.WHERE("id = #{id,javaType=Integer,jdbcType=INTEGER}");
		return sql.toString();
	}
}
