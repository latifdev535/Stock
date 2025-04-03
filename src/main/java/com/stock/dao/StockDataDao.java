package com.stock.dao;

import com.stock.modal.Stock;
import com.stock.modal.UserStockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.stock.util.Constants.*;
import static com.stock.util.SQLQueryConstant.*;

@Component
public class StockDataDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockDataDao.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    TransactionTemplate transactionTemplate;


    @Autowired
    JdbcTemplate ijdbcTemplate;

    public List<UserStockData> getUserPortfolio(String userId) {
        List<UserStockData> userStockDataList = new ArrayList<>();
        try {

            userStockDataList = jdbcTemplate.query(SQL_USER_STOCK, Map.of(USERID, userId), (rs, rowNum) -> {
                UserStockData userStockData = new UserStockData();
                userStockData.setId(rs.getString(1));
                userStockData.setStockSymbol(rs.getString(2));
                userStockData.setQty(rs.getInt(3));
                return userStockData;
            });

        } catch (Exception e) {
            LOGGER.error("Error fetching the data for userId {}", userId, e);
        }
        return userStockDataList;
    }


    public UserStockData getStockData(String userId,String stockSymbol) {
        UserStockData userStockDataObj = null;
        try {

            userStockDataObj  = jdbcTemplate.queryForObject(SQL_USER_STOCK_SYMBOL, Map.of(USERID, userId,STOCK_SYMBOL,stockSymbol), new RowMapper<UserStockData>() {
                @Override
                public UserStockData mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserStockData userStockData = new UserStockData();
                    userStockData.setQty(rs.getInt(STOCK_QTY));
                    userStockData.setId(rs.getString(ID));
                    return userStockData;
                }
            });

        } catch (EmptyResultDataAccessException ex) {
            LOGGER.error("Error fetching the data for userId {}", userId);
        } catch (Exception e) {
            LOGGER.error("Error fetching the data for userId {}", userId, e);
        }
        return userStockDataObj;
    }

    public boolean tradeStock(Stock stock, String userId, UserStockData existingData) {

        try {

            transactionTemplate.executeWithoutResult((TransactionStatus ts) -> {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                ijdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection
                            .prepareStatement(Objects.isNull(existingData)?SQL_INSERT_STOCK:SQL_UPDATE_STOCK, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(2, userId);
                    ps.setString(3, stock.getSymbol());

                    int i = Integer.parseInt(stock.getQty()) * (stock.getType().equalsIgnoreCase("Buy") ? 1 : -1) ;
                    int i1 = Objects.isNull(existingData) ? 0 : existingData.getQty();


                    if (i +i1 <0 ) {
                        throw new RuntimeException("Invalid Operation");
                    }
                    ps.setString(1,String.valueOf(i +i1));
                    return ps;
                }, keyHolder);

                int i = (int) Objects.requireNonNull(keyHolder.getKeys()).get("ID");
                int update = ijdbcTemplate.update(SQL_INSERT_STOCK_HIST,new Object[]{i,stock.getQty(),stock.getType()});

            });

        } catch (Exception e) {
            throw e;
        }
        return true;
    }


}
