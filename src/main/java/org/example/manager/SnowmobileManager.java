package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.SnowmobileGetAllResponceDTO;
import org.example.dto.SnowmobileGetByIdResponceDTO;
import org.example.dto.SnowmobileSaveRequestDTO;
import org.example.dto.SnowmobileSaveResponseDTO;
import org.example.exception.SnowmobileNotFoundException;
import org.example.model.SnowmobileBasicModel;
import org.example.model.SnowmobileFullModel;
import org.example.rowmapper.SnowmobileBasicRowMapper;
import org.example.rowmapper.SnowmobileFullRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SnowmobileManager {
    private final NamedParameterJdbcTemplate template;
    private final SnowmobileBasicRowMapper snowmobileBasicRowMapper;
    private final SnowmobileFullRowMapper snowmobileFullRowMapper;
    private final int percentSpecialDiscount = 10;
    private final int percentPremiumDiscount = 15;

    public SnowmobileGetAllResponceDTO getAll() {
        final List<SnowmobileBasicModel> items = template.query(
                // language=PostgreSQL
                """
                        SELECT id, vendors, model_year,price FROM snowmobiles
                        WHERE removed = FALSE
                        ORDER BY id
                        LIMIT 100
                                                
                        """,
                snowmobileBasicRowMapper
        );

        final SnowmobileGetAllResponceDTO responceDTO = new SnowmobileGetAllResponceDTO(new ArrayList<>(items.size()));
        for (SnowmobileBasicModel item : items) {
            responceDTO.getSnowmobiles().add(new SnowmobileGetAllResponceDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice()
            ));

        }
        return responceDTO;
    }

    public SnowmobileGetByIdResponceDTO getById(long id) {
        try {
            final SnowmobileFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, vendors,model_year,price, qty_of_snowmobiles, colors, track_parameters, horse_power  FROM snowmobiles
                            WHERE id=:id AND removed=FALSE
                            """,
                    Map.of("id", id),
                    snowmobileFullRowMapper
            );
            item.setSpecialPrice(item.getPrice() - (item.getPrice() * percentSpecialDiscount /100));

            item.setPremiumPrice(item.getPrice()-(item.getPrice() * percentPremiumDiscount /100));

            final SnowmobileGetByIdResponceDTO responceDTO = new SnowmobileGetByIdResponceDTO(new SnowmobileGetByIdResponceDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice(),
                    item.getQtyOfSnowmobiles(),
                    item.getColors(),
                    item.getTrackParameters(),
                    item.getHorsePower(),
                    item.getSpecialPrice(),
                    item.getPremiumPrice()
            ));
            return responceDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new SnowmobileNotFoundException(e);
        }
    }

    public SnowmobileSaveResponseDTO save(SnowmobileSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    private SnowmobileSaveResponseDTO create(SnowmobileSaveRequestDTO requestDTO) {
        final SnowmobileFullModel item = template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO snowmobiles (vendors,model_year, price, qty_of_snowmobiles, colors,track_parameters,horse_power) 
                        VALUES (:vendors, :model_year, :price, :qty_of_snowmobiles, :colors, :track_parameters, :horse_power)
                        RETURNING id, vendors,model_year, price, qty_of_snowmobiles, colors,track_parameters, horse_power
                        """,
                Map.of(
                        "vendors", requestDTO.getVendors(),
                        "modelYear", requestDTO.getModelYear(),
                        "price", requestDTO.getPrice(),
                        "qtyOfSnowmobiles", requestDTO.getQtyOfSnowmobiles(),
                        "colors", requestDTO.getColors(),
                        "trackParameters", requestDTO.getTrackParameters(),
                        "horsePower", requestDTO.getHorsePower()
                ),
                snowmobileFullRowMapper
        );
        final SnowmobileSaveResponseDTO responseDTO = new SnowmobileSaveResponseDTO(new SnowmobileSaveResponseDTO.Snowmobile(
                item.getId(),
                item.getVendors(),
                item.getModelYear(),
                item.getPrice(),
                item.getQtyOfSnowmobiles(),
                item.getColors(),
                item.getTrackParameters(),
                item.getHorsePower()
        ));

        return responseDTO;
    }

    public void removeById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                        UPDATE snowmobiles SET removed = TRUE WHERE id = :id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new SnowmobileNotFoundException("snowmobile with id " + id + " not found");
        }
    }

    private SnowmobileSaveResponseDTO update(SnowmobileSaveRequestDTO requestDTO) {
        try {
            final SnowmobileFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            UPDATE snowmobiles SET vendors = :vendors,model_year= :model_year, price = :price, qty_of_snowmobiles = :qty_of_snowmobiles, colors= :colors, track_parameters= :track_parameters, horse_power= :horse_power
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, vendors,model_year, price, qty_of_snowmobiles, colors, track_parameters, horse_power
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "vendors", requestDTO.getVendors(),
                            "model_year", requestDTO.getModelYear(),
                            "price", requestDTO.getPrice(),
                            "qty", requestDTO.getQtyOfSnowmobiles(),
                            "colors", requestDTO.getColors(),
                            "track_length", requestDTO.getTrackParameters(),
                            "horse_power", requestDTO.getHorsePower()
                    ),
                    snowmobileFullRowMapper
            );

            final SnowmobileSaveResponseDTO responseDTO = new SnowmobileSaveResponseDTO(new SnowmobileSaveResponseDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice(),
                    item.getQtyOfSnowmobiles(),
                    item.getColors(),
                    item.getTrackParameters(),
                    item.getHorsePower()
            ));

            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new SnowmobileNotFoundException(e);
        }
    }
    public void restoreById(long id) {
        final int affected = template.update(
                // language=PostgreSQL
                """
                    UPDATE snowmobiles SET removed = FALSE WHERE id = :id
                    """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new SnowmobileNotFoundException("snowmobile with id " + id + " not found");
        }
    }
}

