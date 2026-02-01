package br.com.gkanawati.mockito.constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class CheckoutServiceTest {

  @Test
  void testMockObjectConstruction() {
    // given
    try (MockedConstruction<PaymentProcessor> mocked = mockConstruction(PaymentProcessor.class,
        (mock, context) -> {
          when(mock.chargeCustomer(anyString(), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        })) {
      CheckoutService checkoutService = new CheckoutService();

      // when
      BigDecimal result = checkoutService.purchaseProduct("Test Product", "Customer123");

      // then
      assertEquals(BigDecimal.TEN, result);
      assertEquals(1, mocked.constructed().size());
    }
  }
}