package example;

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
class RentTest {
    @Container
    static DockerComposeContainer<?> compose = new DockerComposeContainer<>(
            Path.of("docker-compose.yml").toFile()
    );

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldPerformRent() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/rents/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        // language=JSON
                                        """
                                                {
                                                  "idSnowmobile": 1,
                                                    "qtyOfSnowmobilesForRent": 1,
                                                    "qtyOfDayForRent": 1,
                                                    "clientFullName": "Иванов Иван Иванович",
                                                    "phoneNumber": 89171111111
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
                                          "rent": {
                                              "id": 6,
                                              "idSnowmobile": 1,
                                              "vendors": "SKI-DOO SUMMIT X",
                                              "qtyOfSnowmobilesForRent": 1,
                                              "qtyOfDayForRent": 1,
                                              "clientFullName": "Иванов Иван Иванович",
                                              "phoneNumber": 89171111111,
                                              "totalPrice": 25000
                                          }
                                        }
                                        """
                        )
                );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/rents/getById")
                                .queryParam("id", String.valueOf(1))
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().json(
                                // language=JSON
                                """
                                        {
                                          "rent": {
                                              "id": 1,
                                              "idSnowmobile": 1,
                                              "vendors": "SKI-DOO SUMMIT X",
                                              "qtyOfSnowmobilesForRent": 3,
                                              "qtyOfDayForRent": 5,
                                              "clientFullName": "Иванов Иван Иванович",
                                              "phoneNumber": 89175555555,
                                              "totalPrice": 345000
                                          }
                                        }
                                        """
                        )
                );
    }
}
