/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.service;

import com.evgen.domain.User;
import com.evgen.domain.Vote;
import java.util.List;

/**
 *
 * @author ieshua
 */
public interface UserService {
    public List<User> getAllUsers();
    public User getUserByUserName(String username);
    public void addUser(User user);
    public void removeUser(String userLogin);
    public void updateUser(User user);
}
