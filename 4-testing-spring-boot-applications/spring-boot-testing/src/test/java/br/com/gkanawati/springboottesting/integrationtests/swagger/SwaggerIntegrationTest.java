package br.com.gkanawati.springboottesting.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.gkanawati.springboottesting.config.TestConfigs;
import br.com.gkanawati.springboottesting.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

  @Test
  @DisplayName("Should display Swagger UI page")
  void testShouldDisplaySwaggerUiPage() {
    // given - when - then
    String content = given().basePath("/swagger-ui/index.html")
        .port(TestConfigs.SERVER_PORT)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract().body().asString();

    assertTrue(content.contains("Swagger UI"));
  }

}
