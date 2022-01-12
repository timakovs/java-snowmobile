package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentGetByIdResponseDTO {
    private Rent rent;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Rent {


        private long id;
        private long idSnowmobile;
        private String vendors;
        private int qtyOfSnowmobilesForRent;
        private int qtyOfDayForRent;
        private String clientFullName;
        private long phoneNumber;
        private int totalPrice;
    }
}
