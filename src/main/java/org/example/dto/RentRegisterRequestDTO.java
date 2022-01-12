package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentRegisterRequestDTO {

    private long idSnowmobile;
    private int qtyOfSnowmobilesForRent;
    private int qtyOfDayForRent;

    private String clientFullName;
    private long phoneNumber;


}
