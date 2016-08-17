package com.lance.code.generation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lance.code.generation.domain.ColumnInfo;
import com.lance.code.generation.domain.TableInfo;

public interface TablesMapper {

	/**
	 * 根据Schema查询所有表信息
	 * @param schema
	 * 2016年8月16日上午9:44:53
	 */
	List<TableInfo> findAll(String schema);
	
	/**
	 * 根据表获取表字段
	 * @param tableName
	 * 2016年8月16日下午1:44:29
	 */
	List<ColumnInfo> findColumn(@Param("tableName")String tableName, @Param("schemaName")String schemaName);
}
