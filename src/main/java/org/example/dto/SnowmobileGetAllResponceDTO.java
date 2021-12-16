package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SnowmobileGetAllResponceDTO {
    private List <Snowmobile> snowmobiles;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Snowmobile {
        private long id;
        private String vendors;
        private int modelYear;
        private int price;
    }
}
