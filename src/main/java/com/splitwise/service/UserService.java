package com.splitwise.service;

import com.splitwise.dao.UserDAO;
import com.splitwise.model.User;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User registerUser(String name, String email, String password) {
        //hash password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);

        return userDAO.create(user);
    }

    public Optional<User> authenticateUser(String email, String password){
        return userDAO.findByEmail(email).filter(user -> BCrypt.checkpw(password, user.getPasswordHash()));
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }
}
