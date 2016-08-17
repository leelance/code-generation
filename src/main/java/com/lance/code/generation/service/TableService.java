package com.lance.code.generation.service;

import java.util.List;

import com.lance.code.generation.domain.TableInfo;

public interface TableService {

	/**
	 * 根据Schema查询所有表信息
	 * @param schema
	 * @return
	 * 2016年8月16日上午9:44:53
	 */
	List<TableInfo> findAll(String schema);
	
	void run();
}
