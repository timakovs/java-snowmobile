package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SnowmobileGetByIdResponceDTO {
    private Snowmobile snowmobile;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Snowmobile {
        private long id;
        private String vendors;
        private int modelYear;
        private int price;
        private int qtyOfSnowmobiles;
        private String [] colors;
        private Integer [] trackParameters;
        private int horsePower;
        private int specialPrice;
        private int premiumPrice;

    }
}
