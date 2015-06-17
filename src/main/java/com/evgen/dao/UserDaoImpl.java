/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgen.dao;

import com.evgen.domain.User;
import com.evgen.domain.Vote;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.joda.time.LocalDateTime;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
/**
 *
 * @author ieshua
 */

public class UserDaoImpl implements UserDao{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    
    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

   
    @Override
    public User getUserByUserName(String username) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("username", username);
        return namedParameterJdbcTemplate.queryForObject("select * from users where username = :username",
        parameters,new UserMapper());
    }
    
    
    @Override
    public void addUser(User user) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        namedParameterJdbcTemplate.update("insert into users(username , name, password, enabled) values (:username, :name, :password, :enabled)", 
                parameterSource);
        
        Map<String, Object> parameters = new HashMap(2);
        parameters.put("username", user.getUsername());
        parameters.put("ROLE", "ROLE_USER");
        namedParameterJdbcTemplate.update("insert into user_roles(username, ROLE) values(:username, :ROLE)", 
                parameters);
        
    }
    
    
    @Override
    public List<User> getAllUsers() {
        return namedParameterJdbcTemplate.query("select * from users", new UserMapper());
    } 
    
    @Override
    public void removeUser(String username) {
        Map<String, Object> parameters = new HashMap(1);
        parameters.put("username", username);
        namedParameterJdbcTemplate.update("delete from user_roles where username = :username", parameters);
        namedParameterJdbcTemplate.update("delete from users where username = :username", parameters);        
    }
    
    @Override
    public void updateUser(User user) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        namedParameterJdbcTemplate.update("update user set name = :name, password = :password, enabled = :enabled where username = :username", 
                parameterSource);
    }
       
    
    
    public class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEnabled(resultSet.getInt("enabled"));
            return user;
        }
    }
    
}


