package org.example.rowmapper;

import org.example.model.SnowmobileBasicModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SnowmobileBasicRowMapper implements RowMapper<SnowmobileBasicModel> {
    @Override
    public SnowmobileBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SnowmobileBasicModel(
                rs.getLong("id"),
                rs.getString("vendors"),
                rs.getInt("model_year"),
                rs.getInt("price")
        );
    }
}
