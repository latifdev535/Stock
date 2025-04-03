package com.stock.dao;

import com.stock.modal.User;
import com.stock.util.SQLQueryConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.stock.util.Constants.*;

@Component
public class UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public User getUser(String username,String password) {
        User loggedUser = null;
        try {
            loggedUser = jdbcTemplate.queryForObject(SQLQueryConstant.SQL_USER_LOGIN,
                    new MapSqlParameterSource(Map.of(USERNAME,username,PASSWORD,password)),(rs,i)->{
                User user = new User();
                user.setId(rs.getInt(ID));
                user.setUserName(rs.getString(USERNAME));
                user.setActiveStatus(rs.getString(ACTIVE_STATUS));
                return user;
            });
        } catch (Exception e) {
            LOGGER.error("Error fetching user from database for username {} ",username,e);
        }
        return loggedUser;
    }





}
