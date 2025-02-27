package com.danieloliveira.restwithspringbootandjava.integrationtests.swagger;

import com.danieloliveira.restwithspringbootandjava.configs.TestConfigs;
import com.danieloliveira.restwithspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    // verifica se a documentação está sendo criada
    @Test
    public void shouldDisplaySwaggerUiPage() {
        var content =
                given()
                        .basePath("/swagger-ui/index.html")
                        .port(TestConfigs.SERVER_PORT)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        Assertions.assertTrue(content.contains("Swagger UI"));
    }

}
