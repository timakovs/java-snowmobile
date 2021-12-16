package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//@AllArgsConstructor
@Data
public class SnowmobileFullModel {
    private long id;
    private String vendors;
    private int modelYear;
    private int price;
    private int qtyOfSnowmobiles;
    private String[] colors;
    private Integer[] trackParameters;
    private int horsePower;

    private int specialPrice;
    private int premiumPrice;

    public SnowmobileFullModel(long id, String vendors, int modelYear, int price, int qtyOfSnowmobiles, String[] colors, Integer[] trackParameters, int horsePower) {
        this.id = id;
        this.vendors = vendors;
        this.modelYear = modelYear;
        this.price = price;
        this.qtyOfSnowmobiles = qtyOfSnowmobiles;
        this.colors = colors;
        this.trackParameters = trackParameters;
        this.horsePower = horsePower;
    }
}
