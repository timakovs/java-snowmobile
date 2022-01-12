package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SnowmobileFullModel {
    private long id;
    private String vendors;
    private int modelYear;
    private int price;
    private int qtyOfDay;
    private int qtyOfSnowmobiles;
    private String[] colors;
    private Integer[] trackParameters;
    private int horsePower;
    private int specialPrice;
    private int premiumPrice;


}
