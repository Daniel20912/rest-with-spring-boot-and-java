package com.danieloliveira.restwithspringbootandjava.integrationtests.controller.withyaml;

import com.danieloliveira.restwithspringbootandjava.configs.TestConfigs;
import com.danieloliveira.restwithspringbootandjava.integrationtests.controller.withyaml.mapper.YMLMapper;
import com.danieloliveira.restwithspringbootandjava.integrationtests.testcontainers.AbstractIntegrationTest;
import com.danieloliveira.restwithspringbootandjava.integrationtests.vo.AccountCredentialsVO;
import com.danieloliveira.restwithspringbootandjava.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static YMLMapper objectMapper;
    private static TokenVO tokenVO;

    @BeforeAll
    public static void setup() {
        objectMapper =new YMLMapper();
    }


    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        tokenVO = given()
                .config(
                    RestAssuredConfig.config()
                            .encoderConfig(EncoderConfig.encoderConfig()
                                            .encodeContentTypeAs(
                                                    TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT
                                            ))
                )
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .body(user, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class, objectMapper);

        Assertions.assertNotNull(tokenVO.getAccessToken());
    }

    @Test
    @Order(2)
    public void testRefresh() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var newTokenVO = given()
                .config(
                    RestAssuredConfig.config()
                            .encoderConfig(EncoderConfig.encoderConfig()
                                            .encodeContentTypeAs(
                                                    TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT
                                            ))
                )
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
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
