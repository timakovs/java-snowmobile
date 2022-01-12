package org.example.rowmapper;

import org.example.model.RentModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RentRowMapper implements RowMapper<RentModel> {
    @Override
    public RentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RentModel(
                rs.getLong("id"),
                rs.getLong("id_snowmobile"),
                rs.getString("vendors"),
                rs.getInt("qty_of_snowmobiles_for_rent"),
                rs.getInt("qty_of_day_for_rent"),
                rs.getString("client_full_name"),
                rs.getLong("phone_number"),
                rs.getInt("total_price")

        );
    }
}
