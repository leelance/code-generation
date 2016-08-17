# code-generation
Mybatis Auto Code Generation Mapper/Service/Domain/SQL.xml, 主要是针对某个数据, 统一生成Domain/Service/Mapper,以及SQL.xml文件，
如：针对t_customer, 通过该应用生成CustonerInfo.java, CustomerSerivce.java/CustomerServiceImpl.java, CustomerMapper.java 以及 Customer-mapper.xml文件. 小工具类

Project run MainTest
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes=SimpleApplication.class)
public class MainTest {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TableService tableService;

	@Test
	public void main() {
		long startTime = System.currentTimeMillis();
		logger.info("...........start application.........");
		
		tableService.run();
		
		logger.info("...end application...Time: {}",(System.currentTimeMillis()-startTime));
	}
}
```

Generate Code Config
```java
public interface ConfigConstants {
	/**定义Schema*/
	String SCHEMA = "longchou-loan";
	
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
	String ROOT_PACKAGE = "com.lance.code";
	
	/**JavaBean包名称*/
	String DOMAIN_PACKAGE = "domain";
	
	/**service包名称*/
	String SERVICE_PACKAGE = "service";
	
	/**serviceImpl包名称*/
	String SERVICE_impl_PACKAGE = "serviceImpl";
	
	/**mapper包名称*/
	String MAPPER_PACKAGE = "mapper";
}
```

Add dependencies to pom
```xml
<parent>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-parent</artifactId>
   <version>1.4.0.RELEASE</version>
</parent>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Mybatis -->
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.1.1</version>
</dependency>
<!-- MYSQL -->
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>fastjson</artifactId>
	<version>1.2.12</version>
</dependency>
<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-lang3</artifactId>
	<version>3.4</version>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<scope>test</scope>
</dependency>
```
