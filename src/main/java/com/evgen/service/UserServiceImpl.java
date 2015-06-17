/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.service;

import com.evgen.dao.UserDao;
import com.evgen.domain.User;
import com.evgen.domain.Vote;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

/**
 *
 * @author ieshua
 */
public class UserServiceImpl implements UserService{
    private UserDao userDao;
    private static final Logger LOGGER= LogManager.getLogger();
    
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
    
    
    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        Assert.notEmpty(users, "Empty list of Users");
        return users;
    }
    
    @Override
    public User getUserByUserName(String username) {
        Assert.notNull(username, "username can't be null");
        User user = null;
        
        try {
            user = userDao.getUserByUserName(username);
        } catch(EmptyResultDataAccessException ex) {
            LOGGER.error("user with login = '{}' doesn't exist", username);
        }
        return user;
    }
    
    @Override
    public void addUser(User user) {
        Assert.notNull(user, "User can't be null");
        Assert.notNull(user.getUsername(), "username can't be null");
        Assert.notNull(user.getName(),"name can't be null");
        Assert.notNull(user.getPassword(), "password can't be null");
        User existingUser = getUserByUserName(user.getUsername());
        
        if(existingUser != null) {
            LOGGER.error("user with login = '{}' already exist", user.getUsername());
            throw new IllegalArgumentException("user is already present in DB");
        }
        
        
        
        userDao.addUser(user);
    }
    
    public void removeUser(String username) {
        Assert.notNull(username);
        User existingUser = new User();
        
        try {
            existingUser = userDao.getUserByUserName(username);
        } catch(EmptyResultDataAccessException ex) {
            LOGGER.error("user with login = '{}' doesn't exist", username);
            throw new IllegalArgumentException("user with this login doesn't exist");
        }
        
        if(existingUser.getUsername().equals("admin")) {
            LOGGER.error("can't remove admin's profile", username);
            throw new IllegalArgumentException("can't remove admin's profile");
        }
        
        userDao.removeUser(username);
    }
    
    public void updateUser(User user) {
        Assert.notNull(user, "User can't be null");
        Assert.notNull(user.getUsername(), "username can't be null");
        Assert.notNull(user.getName(),"name can't be null");
        Assert.notNull(user.getPassword(), "password can't be null");
        
        User existingUser = getUserByUserName(user.getUsername());
        if(existingUser == null) {
            LOGGER.error("User with login= '{}' doesn't exist");
            throw new IllegalArgumentException("User with this login doesn't exist");
        }
        
        userDao.updateUser(user);
        
    }
        
}
