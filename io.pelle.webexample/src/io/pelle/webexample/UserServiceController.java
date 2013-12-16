package io.pelle.webexample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserServiceController {

	private UserDAO userDAO;

	@Autowired
	public UserServiceController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@RequestMapping(value = "delete/{userId}")
	@ResponseBody
	public void delete(@PathVariable long userId) {
		userDAO.deleteById(userId);
	}

	@RequestMapping(value = "all")
	@ResponseBody
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public User addUser(@RequestParam String userName, @RequestParam String userMail) {
		
		User user = new User();
		user.setName(userName);
		user.setMail(userMail);
		return userDAO.create(user);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public User saveUser(@RequestParam long userId, @RequestParam String userName, @RequestParam String userMail) {
		
		User user = userDAO.getById(userId);
		
		user.setName(userName);
		user.setMail(userMail);
		
		return userDAO.save(user);
	}

}
