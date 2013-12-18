package io.pelle.webexample;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:ApplicationContext.xml", "classpath:TestApplicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest
{

	@BeforeClass
	public static void initJndi() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:DatasourceApplicationContext.xml");

		DataSource testDataSource = (DataSource) context.getBean("testDataSource");

		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		builder.bind("java:comp/env/jdbc/webexample", testDataSource);
		builder.activate();
	}

}
