package org.example.rowmapper;

import org.example.model.RentModel;
import org.example.model.RentSnowmobileModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RentSnowmobileRowMapper implements RowMapper<RentSnowmobileModel> {
    @Override
    public RentSnowmobileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RentSnowmobileModel(
                rs.getLong("id"),
                rs.getString("vendors"),
                rs.getInt("price"),
                rs.getInt("qty_of_snowmobiles"),
                rs.getInt("qty_of_day"),
                rs.getInt("special_price"),
                rs.getInt("premium_price")


        );
    }
}
