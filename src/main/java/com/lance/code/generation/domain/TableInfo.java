package com.lance.code.generation.domain;

import java.io.Serializable;

public class TableInfo implements Serializable{
	private static final long serialVersionUID = -7812734394886606427L;
	
	private String tableName;
	private String tableComment;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
