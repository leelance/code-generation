package com.lance.code.generation.common;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lance.code.generation.domain.ColumnInfo;
import com.lance.code.generation.domain.TableInfo;

public class JavaBeanHandler {

	public static String domainPath(){
		StringBuilder builder = new StringBuilder(ConfigConstants.FILE_PATH);
		return builder.append("\\").append(domainPackagePath()).toString();
	}
	
	public static String domainPackage(){
		StringBuilder builder = new StringBuilder(ConfigConstants.ROOT_PACKAGE);
		return builder.append(KeyWords.DOT).append(ConfigConstants.DOMAIN_PACKAGE).toString();
	}
	
	public static String domainPackagePath(){
		String path = domainPackage();
		return StringUtils.replace(path, ".", "\\");
	}
	
	/**
	 * 生成包文件头 Ex:package com.lance.code.generation.common
	 * 2016年8月16日下午2:18:16
	 */
	public static String domainPackageHeader(){
		StringBuilder builder = new StringBuilder(KeyWords.PACKAGE);
		return builder.append(KeyWords.SPACE).append(domainPackage()).toString();
	}
	
	/**
	 * 创建Domain
	 * @param info
	 * @param columns
	 * 2016年8月16日下午2:46:00
	 */
	public static String createDomain(TableInfo info, List<ColumnInfo> columns) {
		StringBuilder builder = new StringBuilder(domainPackageHeader());
		builder.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		//导入包
		.append(KeyWords.IMPORT).append(KeyWords.SPACE).append("java.io.Serializable").append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE);
		
		//导入资源包
		Set<String> set = importPackage(columns);
		if(!set.isEmpty()) {
			for(String p: set) {
				builder.append(KeyWords.IMPORT).append(KeyWords.SPACE).append(p).append(KeyWords.SEMICOLON)
				.append(KeyWords.NEWLINE);
			}
		}
		builder.append(KeyWords.NEWLINE);
		
		//实体类注释
		builder.append("/**")
		.append(KeyWords.NEWLINE)
		.append("* ").append(info.getTableComment())
		.append(KeyWords.NEWLINE)
		.append("* ").append("@author Lance")
		.append(KeyWords.NEWLINE)
		.append("* ").append("@since ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
		.append(KeyWords.NEWLINE)
		.append("*/")
		.append(KeyWords.NEWLINE)
		
		//生成public class A implements b 
		.append(KeyWords.PUBLIC).append(KeyWords.SPACE).append(KeyWords.CLASS)
		.append(KeyWords.SPACE).append(domainClassName(info.getTableName()))
		.append(KeyWords.SPACE).append(KeyWords.IMPLEMENTS).append(KeyWords.SPACE)
		.append(KeyWords.Serial).append(KeyWords.SPACE)
		.append("{").append(KeyWords.NEWLINE)
		
		//serialVersionUID
		.append(KeyWords.Tab)
		.append("private static final long serialVersionUID = ")
		.append(RandomUtils.nextInt(1, Integer.MAX_VALUE)).append("L")
		.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE);
		
		//属性
		for(ColumnInfo c: columns) {
			builder.append(KeyWords.Tab)
			.append("/**").append(c.getColumnComment()).append("*/")
			.append(KeyWords.NEWLINE).append(KeyWords.Tab)
			.append(KeyWords.PRIVATE).append(KeyWords.SPACE)
			.append(changeType(c.getDataType())).append(KeyWords.SPACE)
			.append(attrName(c.getColumnName(), false)).append(KeyWords.SEMICOLON)
			.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE);
		}
		
		//GET/SET
		for(ColumnInfo c: columns) {
			builder.append(KeyWords.Tab)
			.append(KeyWords.PUBLIC).append(KeyWords.SPACE)
			.append(changeType(c.getDataType())).append(KeyWords.SPACE)
			.append(KeyWords.GET).append(attrName(c.getColumnName(), true)).append("() {")
			.append(KeyWords.NEWLINE).append(KeyWords.Tab).append(KeyWords.Tab)
			.append(KeyWords.RETURN).append(attrName(c.getColumnName(), false)).append(KeyWords.SEMICOLON)
			.append(KeyWords.NEWLINE)
			.append(KeyWords.Tab).append("}")
			.append(KeyWords.NEWLINE)
			.append(KeyWords.NEWLINE);
			
			builder.append(KeyWords.Tab)
			.append(KeyWords.PUBLIC).append(KeyWords.SPACE).append(KeyWords.VOID)
			.append(KeyWords.SET).append(attrName(c.getColumnName(), true)).append("(")
			.append(changeType(c.getDataType())).append(KeyWords.SPACE).append(attrName(c.getColumnName(), false)).append(") {")
			.append(KeyWords.NEWLINE).append(KeyWords.Tab).append(KeyWords.Tab)
			.append(KeyWords.THIS).append(KeyWords.DOT).append(attrName(c.getColumnName(), false)).append(KeyWords.EQUAL)
			.append(attrName(c.getColumnName(), false)).append(KeyWords.SEMICOLON)
			.append(KeyWords.NEWLINE)
			.append(KeyWords.Tab).append("}")
			.append(KeyWords.NEWLINE)
			.append(KeyWords.NEWLINE);
		}
		
		builder.append("}");
		
		return builder.toString();
	}
	
	//------------------------------------生成Mapper-----------------------------------------
	public static String mapperPackage(){
		StringBuilder builder = new StringBuilder(ConfigConstants.ROOT_PACKAGE);
		return builder.append(KeyWords.DOT).append(ConfigConstants.MAPPER_PACKAGE).toString();
	}
	
	public static String mapperPackagePath(){
		String path = mapperPackage();
		return StringUtils.replace(path, ".", "\\");
	}
	
	public static String mapperPath(){
		StringBuilder builder = new StringBuilder(ConfigConstants.FILE_PATH);
		return builder.append("\\").append(mapperPackagePath()).toString();
	}
	
	public static String mapperPackageHeader(){
		StringBuilder builder = new StringBuilder(KeyWords.PACKAGE);
		return builder.append(KeyWords.SPACE).append(mapperPackage()).toString();
	}
	
	/**
	 * 创建Mapper
	 * @param info
	 * 2016年8月16日下午2:46:00
	 */
	public static String createMapper(TableInfo info) {
		StringBuilder builder = new StringBuilder(mapperPackageHeader());
		builder.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		//实体类注释
		.append("/**")
		.append(KeyWords.NEWLINE)
		.append("* ").append(info.getTableComment())
		.append(KeyWords.NEWLINE)
		.append("* ").append("@author Lance")
		.append(KeyWords.NEWLINE)
		.append("* ").append("@since ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
		.append(KeyWords.NEWLINE)
		.append("*/")
		.append(KeyWords.NEWLINE)
		
		//生成public interface AMapper 
		.append(KeyWords.PUBLIC).append(KeyWords.SPACE).append(KeyWords.INTERFACE)
		.append(KeyWords.SPACE).append(className(info.getTableName(), ConfigConstants.MAPPER_PACKAGE))
		.append(KeyWords.SPACE).append("{")
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		.append("}");
		return builder.toString();
	}
	
	//------------------------------------生成XML-----------------------------------------
	public static String xmlPath(){
		return ConfigConstants.SQL_PATH;
	}
	
	public static String createXMLMapper(TableInfo info) {
		StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		builder.append(KeyWords.NEWLINE)
		.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >")
		.append(KeyWords.NEWLINE)
		.append("<mapper namespace=\"").append(mapperPackage()).append(KeyWords.DOT)
		.append(className(info.getTableName(), ConfigConstants.MAPPER_PACKAGE)).append("\">")
		.append(KeyWords.NEWLINE)
		//加入EhcacheCache缓存, 可以后期替换掉RedisCache
		.append(KeyWords.Tab).append("<cache type=\"org.mybatis.caches.ehcache.EhcacheCache\"/>")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab).append("<!-- ").append(info.getTableComment()).append(" -->")
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		.append("</mapper>");
		return builder.toString();
	}
	
	//------------------------------------生成Service-----------------------------------------
	public static String servicePackage(){
		StringBuilder builder = new StringBuilder(ConfigConstants.ROOT_PACKAGE);
		return builder.append(KeyWords.DOT).append(ConfigConstants.SERVICE_PACKAGE).toString();
	}
	
	public static String servicePackagePath(){
		String path = servicePackage();
		return StringUtils.replace(path, ".", "\\");
	}
	
	public static String servicePath(){
		StringBuilder builder = new StringBuilder(ConfigConstants.FILE_PATH);
		return builder.append("\\").append(servicePackagePath()).toString();
	}
	
	public static String servicePackageHeader(){
		StringBuilder builder = new StringBuilder(KeyWords.PACKAGE);
		return builder.append(KeyWords.SPACE).append(servicePackage()).toString();
	}
	
	/**
	 * 创建Service
	 * @param info
	 * 2016年8月16日下午2:46:00
	 */
	public static String createService(TableInfo info) {
		StringBuilder builder = new StringBuilder(servicePackageHeader());
		builder.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		//实体类注释
		.append("/**")
		.append(KeyWords.NEWLINE)
		.append("* ").append(info.getTableComment())
		.append(KeyWords.NEWLINE)
		.append("* ").append("@author Lance")
		.append(KeyWords.NEWLINE)
		.append("* ").append("@since ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
		.append(KeyWords.NEWLINE)
		.append("*/")
		.append(KeyWords.NEWLINE)
		
		//生成public interface AService 
		.append(KeyWords.PUBLIC).append(KeyWords.SPACE).append(KeyWords.INTERFACE)
		.append(KeyWords.SPACE).append(className(info.getTableName(), ConfigConstants.SERVICE_PACKAGE))
		.append(KeyWords.SPACE).append("{")
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		.append("}");
		return builder.toString();
	}
	
	/**
	 * 创建ServiceImpl
	 * @param info
	 * 2016年8月16日下午2:46:00
	 */
	public static String createServiceImpl(TableInfo info) {
		//Service导入的Mapper接口类
		String importMapper = className(info.getTableName(), ConfigConstants.MAPPER_PACKAGE);
		
		StringBuilder builder = new StringBuilder(servicePackageHeader());
		builder.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		//导入包
		.append(KeyWords.IMPORT).append(KeyWords.SPACE).append("org.springframework.beans.factory.annotation.Autowired").append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE)
		.append(KeyWords.IMPORT).append(KeyWords.SPACE).append("org.springframework.stereotype.Service").append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		.append(KeyWords.IMPORT).append(KeyWords.SPACE).append(mapperPackage())
		.append(KeyWords.DOT).append(importMapper)
		.append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE).append(KeyWords.NEWLINE)
		
		//实体类注释
		.append("/**")
		.append(KeyWords.NEWLINE)
		.append("* ").append(info.getTableComment())
		.append(KeyWords.NEWLINE)
		.append("* ").append("@author Lance")
		.append(KeyWords.NEWLINE)
		.append("* ").append("@since ").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
		.append(KeyWords.NEWLINE)
		.append("*/")
		.append(KeyWords.NEWLINE)
		.append("@Service")
		.append(KeyWords.NEWLINE)
		
		//生成public class AServiceImpl 
		.append(KeyWords.PUBLIC).append(KeyWords.SPACE).append(KeyWords.CLASS)
		.append(KeyWords.SPACE).append(className(info.getTableName(), ConfigConstants.SERVICE_impl_PACKAGE))
		.append(KeyWords.SPACE).append(KeyWords.IMPLEMENTS).append(KeyWords.SPACE)
		.append(className(info.getTableName(), ConfigConstants.SERVICE_PACKAGE)).append("{")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab)
		.append("@Autowired")
		.append(KeyWords.NEWLINE)
		.append(KeyWords.Tab)
		.append(KeyWords.PRIVATE).append(KeyWords.SPACE).append(importMapper)
		.append(KeyWords.SPACE).append(StringUtils.uncapitalize(importMapper)).append(KeyWords.SEMICOLON)
		.append(KeyWords.NEWLINE)
		.append(KeyWords.NEWLINE)
		.append("}");
		return builder.toString();
	}
	
	
	/**------------------------------------------------------------------------------
	 * 处理类名称
	 * @param className
	 * @return
	 * 2016年8月16日下午2:42:20
	 */
	public static String domainClassName(String className){
		className = StringUtils.substringAfter(className, ConfigConstants.REMOVE_TABLE_PREFIX);
		String[] _names = StringUtils.split(className, "_");
		
		StringBuilder builder = new StringBuilder();
		for(String name: _names){
			builder.append(StringUtils.capitalize(name));
		}
		return builder.append(StringUtils.capitalize(ConfigConstants.DOMAIN_SUFFIX)).toString();
	}
	
	/**
	 * 处理类名称
	 * @param className
	 * @param suffix
	 * 2016年8月16日下午5:44:13
	 */
	public static String className(String className, String suffix){
		className = StringUtils.substringAfter(className, ConfigConstants.REMOVE_TABLE_PREFIX);
		String[] _names = StringUtils.split(className, "_");
		
		StringBuilder builder = new StringBuilder();
		for(String name: _names){
			builder.append(StringUtils.capitalize(name));
		}
		return builder.append(StringUtils.capitalize(suffix)).toString();
	}
	
	/**
	 * 处理属性名字
	 * @param attr
	 * 2016年8月16日下午4:15:12
	 */
	public static String attrName(String attr, boolean isCapitalize){
		String[] _attrs = StringUtils.split(attr, "_");
		
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<_attrs.length; i++){
			if(i == 0 && !isCapitalize) {
				builder.append(_attrs[0]);
			}else {
				builder.append(StringUtils.capitalize(_attrs[i]));
			}
		}
		return builder.toString();
	}
	
	/**
	 * 转化类型
	 * @param type
	 * @return
	 * 2016年8月16日下午4:29:29
	 */
	public static String changeType(String type){
		switch (type.toLowerCase()) {
			case "date": ;
			case "timestamp": ;
			case "datetime": return "Date";
			case "decimal": return "BigDecimal";
			case "text": ;
			case "varchar": return "String";
			case "tinyint": return "int";
		}
		return type;
	}
	
	public static Set<String> importPackage(List<ColumnInfo> columns){
		Set<String> set = new HashSet<>(); 
		for(ColumnInfo c: columns) {
			switch (c.getDataType().toLowerCase()) {
				case "datetime": set.add("java.util.Date"); break;
				case "decimal": set.add("java.math.BigDecimal"); break;
			}
		}
		return set;
	}
}
