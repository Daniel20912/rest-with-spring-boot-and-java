package com.danieloliveira.restwithspringbootandjava.integrationtests.controller.withjson;



import com.danieloliveira.restwithspringbootandjava.configs.TestConfigs;
import com.danieloliveira.restwithspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import com.danieloliveira.restwithspringbootandjava.integrationtests.vo.AccountCredentialsVO;
import com.danieloliveira.restwithspringbootandjava.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;

    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        tokenVO = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);

        Assertions.assertNotNull(tokenVO.getAccessToken());
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var newTokenVO = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("username", tokenVO.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);

        Assertions.assertNotNull(newTokenVO.getAccessToken());
        Assertions.assertNotNull(newTokenVO.getRefreshToken());
    }
}
