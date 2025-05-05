package com.splitwise.dao;

import com.splitwise.model.User;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory factory){
        super(factory);
    }

    public User create(User user){
        return persist(user);
    }

    public Optional<User> findByEmail(String email){
        return currentSession().createQuery("FROM User WHERE email =:email", User.class)
            .setParameter("email", email)
            .uniqueResultOptional();
    }

    public List<User> getAllUsers() {
        return list((Query<User>) namedQuery("User.findAll"));
    }
}
