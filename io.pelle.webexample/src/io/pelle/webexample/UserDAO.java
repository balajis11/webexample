package io.pelle.webexample;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDAO {

	@Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	public User create(User user) {
		return (User) sessionFactory.getCurrentSession().merge(user);
	}

	public User getById(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public void deleteById(long id) {
		sessionFactory.getCurrentSession().delete(getById(id));
	}

	public User save(User user) {
		return (User) sessionFactory.getCurrentSession().merge(user);
	}
}
