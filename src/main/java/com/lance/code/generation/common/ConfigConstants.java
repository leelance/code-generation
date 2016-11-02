package com.lance.code.generation.common;

public interface ConfigConstants {
	/**定义Schema*/
	String SCHEMA = "web-system";
	
	/**移除表前缀*/
	String REMOVE_TABLE_PREFIX = "t_";
	
	/**Domain后缀, CustomerInfo*/
	String DOMAIN_SUFFIX = "";
	
	/**生成文件路径*/
	String FILE_PATH = "E:\\gitwork\\code-generation\\src\\main\\java";
	
	/**MyBatis SQL生成文件路径*/
	String SQL_PATH = "E:\\gitwork\\code-generation\\src\\main\\resources\\mappers";
	
	/**生成Mapper.xml后缀名字, EX: customer-mapper.xml*/
	String SQL_MAPPER_SUFFIX = "-mapper";
	
	/**生成包名称*/
	String ROOT_PACKAGE = "com.lance";
	
	/**JavaBean包名称*/
	String DOMAIN_PACKAGE = "domain";
	
	/**service包名称*/
	String SERVICE_PACKAGE = "service";
	
	/**serviceImpl包名称*/
	String SERVICE_impl_PACKAGE = "serviceImpl";
	
	/**mapper包名称*/
	String MAPPER_PACKAGE = "mapper";
}
