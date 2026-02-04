package br.com.gkanawati.springboottesting.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ActiveProfiles("test")
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

  static class Initializer implements
      ApplicationContextInitializer<ConfigurableApplicationContext> {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.1");

    private static void startContainers() {
      Startables.deepStart(Stream.of(postgreSQLContainer)).join();
    }

    private static Map<String, Object> createConnectionConfiguration() {
      return Map.of(
          "spring.datasource.url", postgreSQLContainer.getJdbcUrl(),
          "spring.datasource.username", postgreSQLContainer.getUsername(),
          "spring.datasource.password", postgreSQLContainer.getPassword()
      );
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
      startContainers();
      ConfigurableEnvironment environment = applicationContext.getEnvironment();
      MapPropertySource propertySource = new MapPropertySource("testcontainers",
          createConnectionConfiguration());
      environment.getPropertySources().addFirst(propertySource);
    }
  }

}
