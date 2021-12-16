package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SnowmobileBasicModel {
    private long id;
    private String vendors;
    private int modelYear;
    private int price;
}
