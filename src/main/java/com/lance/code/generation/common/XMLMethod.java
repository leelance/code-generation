package com.lance.code.generation.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lance.code.generation.domain.ColumnInfo;
import com.lance.code.generation.domain.TableInfo;

public final class XMLMethod {

	/**
	 * XML Save
	 * @param info
	 * @return
	 * 2016年8月24日上午10:43:56
	 */
	public static String xmlFindOne(TableInfo info) {
		StringBuilder builder = new StringBuilder();
		builder.append(KeyWords.Tab).append("<!-- 根据ID查询对象 -->").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("<select id=\"findOne\" resultType=\"")
		.append(JavaBeanHandler.domainClassName(info.getTableName()))
		.append("\">")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab)
		.append("select * from ")
		.append(info.getTableName())
		.append(" where id=#{value}")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("</select>")
		.append(KeyWords.NEWLINE);
		return builder.toString(); 
	}
	
	/**
	 * XML Delete
	 * @param info
	 * @return
	 * 2016年8月24日上午10:43:56
	 */
	public static String xmlDelete(TableInfo info) {
		StringBuilder builder = new StringBuilder();
		builder.append(KeyWords.Tab).append("<!-- 根据ID删除对象 -->").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("<delete id=\"delete\">")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab)
		.append("delete from ")
		.append(info.getTableName())
		.append(" where id=#{value}")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("</delete>")
		.append(KeyWords.NEWLINE);
		return builder.toString(); 
	}
	
	/**
	 * XML save
	 * @param info
	 * @return
	 * 2016年8月24日上午10:43:56
	 */
	public static String xmlSave(TableInfo info, List<ColumnInfo> columns) {
		StringBuilder builder = new StringBuilder();
		builder.append(KeyWords.Tab).append("<!-- Save对象 -->").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("<insert id=\"save\" "+getPrimaryKey(columns)+" parameterType=\"")
		.append(JavaBeanHandler.domainClassName(info.getTableName()))
		.append("\">")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab)
		.append("insert into ")
		.append(info.getTableName())
		.append("(");
		for(ColumnInfo column: columns) {
			if(!column.isPrimaryKey()) {
				builder.append(column.getColumnName()).append(",");
			}
		}
		builder.append(")")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab)
		.append("values(").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab);
		for(ColumnInfo column: columns) {
			if(!column.isPrimaryKey()) {
				builder.append("#{").append(JavaBeanHandler.attrName(column.getColumnName(), false)).append("},");
			}
		}
		
		builder.append(")").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("</insert>")
		.append(KeyWords.NEWLINE);
		return StringUtils.replace(builder.toString(), ",)", ")");
	}
	
	/**
	 * 获取主键
	 * @param columns
	 * @since 2016年11月2日下午1:41:43
	 */
	private static String getPrimaryKey(List<ColumnInfo> columns){
		for(ColumnInfo column: columns) {
			if(column.isPrimaryKey()) {
				return "useGeneratedKeys=\"true\" keyProperty=\""+JavaBeanHandler.attrName(column.getColumnName(), false)+"\"";
			}
		}
		return "";
	}
	
	/**
	 * XML update.sql
	 * @param info
	 * @param columns
	 * @return
	 * 2016年8月24日下午1:12:49
	 */
	public static String xmlUpdate(TableInfo info, List<ColumnInfo> columns) {
		StringBuilder builder = new StringBuilder();
		builder.append(KeyWords.Tab).append("<!-- Update对象 -->").append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("<update id=\"update\" parameterType=\"")
		.append(JavaBeanHandler.domainClassName(info.getTableName()))
		.append("\">")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append(KeyWords.Tab)
		.append("update ").append(info.getTableName()).append(" set ");
		
		for(ColumnInfo column: columns) {
			if(!StringUtils.equalsIgnoreCase(column.getColumnName(), "id")) {
				builder.append(KeyWords.NEWLINE).append(KeyWords.Tab).append(KeyWords.Tab).append(column.getColumnName()).append(" = ")
				.append("#{").append(JavaBeanHandler.attrName(column.getColumnName(), false)).append("},");
			}
		}
		
		builder.append(" where id=#{id}")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("</update>")
		.append(KeyWords.NEWLINE);
		return StringUtils.replace(builder.toString(), ", where", " where"); 
	}
}
