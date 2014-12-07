package com.cathaylife.admin.persist;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.cathaylife.admin.persist.domain.SampleDomain;
import com.cathaylife.admin.persist.provider.SampleProvider;

public interface SampleDao {
	 @SelectProvider(type = SampleProvider.class, method = "getSampleSQL")  
	 @Options(useCache = true, flushCache = false, timeout = 10000)  
	 @Results(value = {  
	            @Result(id = true, property = "id", column = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),  
	            @Result(property = "name", column = "text", javaType = String.class, jdbcType = JdbcType.VARCHAR)})
	public SampleDomain getDomain(@Param("id")Integer id);
}
