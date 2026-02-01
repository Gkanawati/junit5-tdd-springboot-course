package br.com.gkanawati.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class OrderServiceTest {

  private final OrderService orderService = new OrderService();
  private final UUID defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");
  private final LocalDateTime defaultLocalDateTime = LocalDateTime.of(2026, 2, 1, 8, 0);

  @Test
  void shouldIncludeRandomOrderIdWhenNoParentOrderExists() {
    // Usar try-with-resources para garantir que o mock está ativo apenas dentro deste bloco
    // para evitar efeitos colaterais em outros testes

    // given
    try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
      mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);

      // when
      Order result = orderService.createOrder("Test Product", 2L, null);

      //  then
      assertEquals(defaultUuid.toString(), result.getId());
    }
  }

  @Test
  void shouldIncludeCurrentTimeWhenCreatingANewOrder() {
    // given
    try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
      mockedLocalDateTime.when(LocalDateTime::now).thenReturn(defaultLocalDateTime);

      // when
      Order result = orderService.createOrder("Test Product", 2L, "42");

      //  then
      assertEquals(defaultLocalDateTime, result.getCreationDate());
    }
  }
}
