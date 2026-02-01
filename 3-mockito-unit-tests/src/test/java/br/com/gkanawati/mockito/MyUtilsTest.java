package br.com.gkanawati.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class MyUtilsTest {

  @Test
  void shouldMockStaticMethodWithParams() {
    // given
    try (MockedStatic<MyUtils> mockedStatic = mockStatic(MyUtils.class)) {
      mockedStatic
          .when(() -> MyUtils.getWelcomeMessage(eq("John Doe"), anyBoolean()))
          .thenReturn("TestMock");

      // when
      String result = MyUtils.getWelcomeMessage("John Doe", false);

      // then
      assertEquals("TestMock", result);
    }
  }

}