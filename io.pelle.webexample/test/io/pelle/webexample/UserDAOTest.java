package io.pelle.webexample;

import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:/ApplicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class UserDAOTest {

	@Autowired
	private UserDAO userDAO;

	@BeforeClass
	public static void initJndi() throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:TestApplicationContext.xml");
		
		DataSource testDataSource = (DataSource) context
				.getBean("testDataSource");
		
		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		builder.bind("java:comp/env/jdbc/webexample", testDataSource);
		builder.activate();
	}

	@Test
	public void testGetAllUsers() {
		
		Assert.assertTrue(userDAO.getAllUsers().isEmpty());
		
		User user = new User();
		user.setName("Peter");
		userDAO.create(user);

		List<User> users = userDAO.getAllUsers();
		Assert.assertEquals(1, users.size());
		Assert.assertEquals("Peter", users.get(0).getName());
		
		User userById = userDAO.getById(users.get(0).getId());
		Assert.assertEquals("Peter", userById.getName());

		userById.setName("Klaus");
		userDAO.save(userById);
		userById = userDAO.getById(userById.getId());
		Assert.assertEquals("Klaus", userById.getName());

		userDAO.deleteById(userById.getId());
		Assert.assertTrue(userDAO.getAllUsers().isEmpty());
	}


	public void setUserService(UserDAO userService) {
		this.userDAO = userService;
	}

}
