package com.lance.code.generation.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 表列字段名称
 * @author Administrator
 */
public class ColumnInfo implements Serializable{
	private static final long serialVersionUID = -80438187908217823L;
	
	private String columnName;
	private String columnComment;
	private String dataType;
	/**PRI 主键*/
	private String columnKey;
	
	private boolean isPrimaryKey = false;
	
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
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public boolean isPrimaryKey() {
		if(StringUtils.equalsIgnoreCase(columnKey, "PRI")){
			isPrimaryKey = true;
		}
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}	
}