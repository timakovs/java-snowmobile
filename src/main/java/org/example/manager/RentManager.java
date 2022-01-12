package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.dto.RentGetByIdResponseDTO;
import org.example.dto.RentRegisterRequestDTO;
import org.example.dto.RentRegisterResponseDTO;
import org.example.exception.RentNotFoundException;

import org.example.exception.RentRegistrationException;
import org.example.exception.SnowmobileNotFoundException;
import org.example.exception.SnowmobileQtyNotEnoughtException;
import org.example.model.RentModel;

import org.example.model.RentSnowmobileModel;
import org.example.rowmapper.RentRowMapper;
import org.example.rowmapper.RentSnowmobileRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class RentManager {
    private final RentRowMapper rentRowMapper;
    private final RentSnowmobileRowMapper rentSnowmobileRowMapper;
    private final NamedParameterJdbcTemplate template;
    private final int parametrForPremiumPrice = 5;

    public RentGetByIdResponseDTO getById(long id) {
        try {
            final RentModel item = template.queryForObject(
                    // language=PostgreSQL
                    """
                            SELECT id,id_snowmobile,vendors, qty_of_snowmobiles_for_rent, qty_of_day_for_rent,
                             client_full_name, phone_number, total_price  FROM rents
                            WHERE id=:id
                            """,
                    Map.of("id", id),
                    rentRowMapper
            );

            final RentGetByIdResponseDTO responceDTO = new RentGetByIdResponseDTO(new RentGetByIdResponseDTO.Rent(
                    item.getId(),
                    item.getIdSnowmobile(),
                    item.getVendors(),
                    item.getQtyOfSnowmobilesForRent(),
                    item.getQtyOfDayForRent(),
                    item.getClientFullName(),
                    item.getPhoneNumber(),
                    item.getTotalPrice()
            ));
            return responceDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new RentNotFoundException(e);
        }
    }

    @Transactional
    public RentRegisterResponseDTO register(RentRegisterRequestDTO requestDTO) {
        try {
            final RentSnowmobileModel item = template.queryForObject(
                    //language=PostgreSQL
                    """ 
                            SELECT id,vendors, qty_of_snowmobiles,qty_of_day, price, special_price, premium_price FROM snowmobiles
                            WHERE id= :id AND removed= FALSE
                            """,
                    Map.of("id", requestDTO.getIdSnowmobile()),
                    rentSnowmobileRowMapper
            );
            if (item.getQtyOfSnowmobiles() < requestDTO.getQtyOfSnowmobilesForRent()) {
                throw new SnowmobileQtyNotEnoughtException("snowmobile with id" + item.getId() + "has qty"
                        + item.getQtyOfSnowmobiles() + "but requested" + requestDTO.getQtyOfSnowmobilesForRent());
            }

            final int affected = template.update(
                    //language=PostgreSQL
                    """
                            UPDATE snowmobiles SET qty_of_snowmobiles = snowmobiles.qty_of_snowmobiles - :qtyOfSnowmobilesForRent
                            WHERE id = :idSnowmobile AND removed = FALSE
                            """,
                    Map.of(
                            "idSnowmobile", requestDTO.getIdSnowmobile(),
                            "qtyOfSnowmobilesForRent", requestDTO.getQtyOfSnowmobilesForRent()
                    ));
            if (affected == 0) {
                throw new RentRegistrationException("can't update qty with value " + requestDTO.getQtyOfSnowmobilesForRent() + " for snowmobile with id " + requestDTO.getIdSnowmobile());
            }

            final RentModel rent = template.queryForObject(
                    // language=PostgreSQL
                    """
                            INSERT INTO rents (id_snowmobile,vendors,qty_of_snowmobiles_for_rent, qty_of_day_for_rent,client_full_name,phone_number,total_price)
                            VALUES (:idSnowmobile,:vendors, :qtyOfSnowmobilesForRent, :qtyOfDayForRent,:clientFullName,:phoneNumber,:totalPrice)
                            RETURNING id, id_snowmobile,vendors, qty_of_snowmobiles_for_rent,qty_of_day_for_rent, client_full_name,phone_number,total_price
                            """,
                    Map.of(
                            "idSnowmobile", requestDTO.getIdSnowmobile(),
                            "vendors", item.getVendors(),
                            "qtyOfSnowmobilesForRent", requestDTO.getQtyOfSnowmobilesForRent(),
                            "qtyOfDayForRent", requestDTO.getQtyOfDayForRent(),
                            "clientFullName", requestDTO.getClientFullName(),
                            "phoneNumber", requestDTO.getPhoneNumber(),
                            "totalPrice", calculatePrice(requestDTO, item)

                    ),
                    rentRowMapper
            );
            return new RentRegisterResponseDTO(new RentRegisterResponseDTO.Rent(
                    rent.getId(),
                    rent.getIdSnowmobile(),
                    rent.getVendors(),
                    rent.getQtyOfSnowmobilesForRent(),
                    rent.getQtyOfDayForRent(),
                    rent.getClientFullName(),
                    rent.getPhoneNumber(),
                    rent.getTotalPrice()
            ));
        } catch (EmptyResultDataAccessException e) {
            throw new SnowmobileNotFoundException(e);
        }
    }

    private int calculatePrice(RentRegisterRequestDTO requestDTO, RentSnowmobileModel item) {
        int totalPrice = 0;
        if (item.getQtyOfDay() < requestDTO.getQtyOfDayForRent()) {
           return totalPrice = item.getSpecialPrice() * requestDTO.getQtyOfSnowmobilesForRent() * requestDTO.getQtyOfDayForRent();

        }
        if (parametrForPremiumPrice <= requestDTO.getQtyOfDayForRent()) {
           return totalPrice = item.getPremiumPrice() * requestDTO.getQtyOfSnowmobilesForRent() * requestDTO.getQtyOfDayForRent();

        }

        return totalPrice = item.getPrice() * requestDTO.getQtyOfSnowmobilesForRent() * requestDTO.getQtyOfDayForRent();
    }
}



