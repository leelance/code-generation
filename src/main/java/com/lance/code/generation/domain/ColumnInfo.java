package com.lance.code.generation.domain;

import java.io.Serializable;

/**
 * 表列字段名称
 * @author Administrator
 */
public class ColumnInfo implements Serializable{
	private static final long serialVersionUID = -80438187908217823L;
	
	private String columnName;
	private String columnComment;
	private String dataType;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}	
}