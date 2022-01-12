package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;

@Testcontainers
@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class CRUDTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformSnowmoileCRUD() throws Exception {
        // getAll
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/snowmobiles/getAll")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "snowmobiles": [
                                               {
                                                 "id": 1,
                                                 "vendors": "SKI-DOO SUMMIT X",
                                                 "modelYear": 2021,
                                                 "price": 25000
                                               },
                                               {
                                                 "id": 2,
                                                 "vendors": "SKI-DOO SUMMIT EXPERT",
                                                 "modelYear": 2020,
                                                 "price": 23000
                                               },
                                               {
                                                 "id": 3,
                                                 "vendors": "SKI-DOO FREERIDE",
                                                 "modelYear": 2021,
                                                 "price": 20000
                                               },
                                               {
                                                 "id": 4,
                                                 "vendors": "POLARIS AXIS",
                                                 "modelYear": 2019,
                                                 "price": 23000
                                               },
                                               {
                                                 "id": 5,
                                                 "vendors": "POLARIS PRO RMK",
                                                 "modelYear": 2021,
                                                 "price": 25000
                                               },
                                               {
                                                 "id": 6,
                                                 "vendors": "LYNX BOONDOCKER",
                                                 "modelYear": 2021,
                                                 "price": 20000
                                               }
                                             ]
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/snowmobiles/getById")
                                .queryParam("id", String.valueOf(2))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "snowmobile": {
                                               "id": 2,
                                               "vendors": "SKI-DOO SUMMIT EXPERT",
                                               "modelYear": 2020,
                                               "price": 23000,
                                               "qtyOfDay": 3,
                                               "qtyOfSnowmobiles": 5,
                                               "colors": [
                                                 "white(белый)",
                                                 "red(красный)"
                                               ],
                                               "trackParameters": [
                                                 165,
                                                 16,
                                                 3
                                               ],
                                               "horsePower": 165,
                                               "specialPrice": 21000,
                                               "premiumPrice": 19000
                                             }
                                        }
                                        """
                        )
                );


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/snowmobiles/getById")
                                .queryParam("id", String.valueOf(55))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isNotFound()
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/snowmobiles/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                  "id": 0,
                                                             "vendors": "Yamaha",
                                                             "modelYear": 2020,
                                                             "price": 19000,
                                                             "qtyOfDay": 3,
                                                             "qtyOfSnowmobiles": 1,
                                                             "colors": [
                                                               "black(черный)",
                                                               "red(красный)"
                                                             ],
                                                             "trackParameters": [154,163,13],
                                                             "horsePower": 165,
                                                             "specialPrice": 17000,
                                                             "premiumPrice": 15000
                                                }
                                                """
                                )
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "snowmobile": {
                                               "id": 7,
                                               "vendors": "Yamaha",
                                               "modelYear": 2020,
                                               "price": 19000,
                                               "qtyOfDay": 3,
                                               "qtyOfSnowmobiles": 1,
                                               "colors": [
                                                 "black(черный)",
                                                 "red(красный)"
                                               ],
                                               "trackParameters": [
                                                 154,
                                                 163,
                                                 13
                                               ],
                                               "horsePower": 165,
                                               "specialPrice": 17000,
                                               "premiumPrice": 15000
                                             }
                                        }
                                        """
                        )
                );
    }
}
