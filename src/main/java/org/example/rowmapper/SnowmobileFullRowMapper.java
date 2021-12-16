package org.example.rowmapper;

import org.example.model.SnowmobileFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SnowmobileFullRowMapper implements RowMapper<SnowmobileFullModel> {
    @Override
    public SnowmobileFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SnowmobileFullModel(
                rs.getLong("id"),
                rs.getString("vendors"),
                rs.getInt("model_year"),
                rs.getInt("price"),
                rs.getInt("qty_of_snowmobiles"),
                (String[]) rs.getArray("colors").getArray(),
                (Integer[]) rs.getArray("track_parameters").getArray(),
                rs.getInt("horse_power")
        );
    }
}
