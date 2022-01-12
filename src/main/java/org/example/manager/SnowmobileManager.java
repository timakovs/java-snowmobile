package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.SnowmobileGetAllResponseDTO;
import org.example.dto.SnowmobileGetByIdResponseDTO;
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
    //private final int percentSpecialDiscount = 10;
    //private final int percentPremiumDiscount = 15;

    public SnowmobileGetAllResponseDTO getAll() {
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

        final SnowmobileGetAllResponseDTO responceDTO = new SnowmobileGetAllResponseDTO(new ArrayList<>(items.size()));
        for (SnowmobileBasicModel item : items) {
            responceDTO.getSnowmobiles().add(new SnowmobileGetAllResponseDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice()
            ));

        }
        return responceDTO;
    }

    public SnowmobileGetByIdResponseDTO getById(long id) {
        try {
            final SnowmobileFullModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id, vendors,model_year,price,qty_of_day, qty_of_snowmobiles, colors, track_parameters, horse_power,special_price,premium_price  FROM snowmobiles
                            WHERE id=:id AND removed=FALSE
                            """,
                    Map.of("id", id),
                    snowmobileFullRowMapper
            );
            //item.setSpecialPrice(item.getPrice() - (item.getPrice() * percentSpecialDiscount /100));

            //item.setPremiumPrice(item.getPrice()-(item.getPrice() * percentPremiumDiscount /100));

            final SnowmobileGetByIdResponseDTO responceDTO = new SnowmobileGetByIdResponseDTO(new SnowmobileGetByIdResponseDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice(),
                    item.getQtyOfDay(),
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
                        INSERT INTO snowmobiles (vendors,model_year, price,qty_of_day,
                         qty_of_snowmobiles, colors,track_parameters,horse_power,special_price,
                         premium_price) 
                        VALUES (:vendors, :modelYear, :price,:qtyOfDay, :qtyOfSnowmobiles, :colors,
                         :trackParameters, :horsePower, :specialPrice, :premiumPrice)
                        RETURNING id, vendors,model_year, price,qty_of_day, qty_of_snowmobiles,
                         colors,track_parameters, horse_power, special_price, premium_price
                        """,
                Map.of(
                        "vendors", requestDTO.getVendors(),
                        "modelYear", requestDTO.getModelYear(),
                        "price", requestDTO.getPrice(),
                        "qtyOfDay", requestDTO.getQtyOfDay(),
                        "qtyOfSnowmobiles", requestDTO.getQtyOfSnowmobiles(),
                        "colors", requestDTO.getColors(),
                        "trackParameters", requestDTO.getTrackParameters(),
                        "horsePower", requestDTO.getHorsePower(),
                        "specialPrice", requestDTO.getSpecialPrice(),
                        "premiumPrice", requestDTO.getPremiumPrice()
                ),
                snowmobileFullRowMapper
        );
        final SnowmobileSaveResponseDTO responseDTO = new SnowmobileSaveResponseDTO(new SnowmobileSaveResponseDTO.Snowmobile(
                item.getId(),
                item.getVendors(),
                item.getModelYear(),
                item.getPrice(),
                item.getQtyOfDay(),
                item.getQtyOfSnowmobiles(),
                item.getColors(),
                item.getTrackParameters(),
                item.getHorsePower(),
                item.getSpecialPrice(),
                item.getPremiumPrice()
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
                            UPDATE snowmobiles SET price = :price,qty_of_day= :qtyOfDay, 
                            qty_of_snowmobiles = :qtyOfSnowmobiles, 
                            colors= :colors, track_parameters= :trackParameters,
                            special_price= :specialPrice, premium_price= :premiumPrice
                            WHERE id = :id AND removed = FALSE
                            RETURNING id, vendors,model_year, price,qty_of_day, qty_of_snowmobiles, 
                            colors, track_parameters, horse_power, special_price, premium_price
                            """,
                    Map.of(
                            "id", requestDTO.getId(),
                            "price",requestDTO.getPrice(),
                            "qtyOfDay",requestDTO.getQtyOfDay(),
                            "qtyOfSnowmobiles",requestDTO.getQtyOfSnowmobiles(),
                            "colors",requestDTO.getColors(),
                            "trackParameters",requestDTO.getTrackParameters(),
                            "specialPrice", requestDTO.getSpecialPrice(),
                            "premiumPrice",requestDTO.getPremiumPrice()

                            ),
                    snowmobileFullRowMapper
            );

            final SnowmobileSaveResponseDTO responseDTO = new SnowmobileSaveResponseDTO(new SnowmobileSaveResponseDTO.Snowmobile(
                    item.getId(),
                    item.getVendors(),
                    item.getModelYear(),
                    item.getPrice(),
                    item.getQtyOfDay(),
                    item.getQtyOfSnowmobiles(),
                    item.getColors(),
                    item.getTrackParameters(),
                    item.getHorsePower(),
                    item.getSpecialPrice(),
                    item.getPremiumPrice()

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

