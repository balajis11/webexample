package io.pelle.webexample;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = false)
@Transactional
public class UserDAOTest extends BaseTest
{

	@Autowired
	private UserDAO userDAO;

	@Test
	public void testGetAllUsers()
	{

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

	public void setUserService(UserDAO userService)
	{
		this.userDAO = userService;
	}

}
