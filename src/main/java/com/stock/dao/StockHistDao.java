package com.stock.dao;

import com.stock.modal.StockHist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stock.util.Constants.*;
import static com.stock.util.SQLQueryConstant.SQL_HIST;

@Component
public class StockHistDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<StockHist> getStockHist(String userId) {

        return jdbcTemplate.query(SQL_HIST, Map.of(USERID, userId), (rs, rowNum) -> {
                StockHist stockHist = new StockHist();
                stockHist.setStockSymbol(rs.getString(STOCK_SYMBOL));
                stockHist.setQty(rs.getString(STOCK_QTY));
                stockHist.setDateTime(rs.getString("CREATED_DATE"));
                stockHist.setOperation(rs.getString("STOCK_OPERATION"));
                return stockHist;

        });
    }

}
