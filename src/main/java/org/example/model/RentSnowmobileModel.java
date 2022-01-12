package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentSnowmobileModel {

    private long id;
    private String vendors;
    private int price;
    private int qtyOfSnowmobiles;
    private int qtyOfDay;
    private int specialPrice;
    private int premiumPrice;
}
