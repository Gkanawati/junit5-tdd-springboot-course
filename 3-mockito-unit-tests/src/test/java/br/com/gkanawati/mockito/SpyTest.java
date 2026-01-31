package br.com.gkanawati.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SpyTest {

  @Test
  void testWithMock() {
    // given
    List<String> mockArrayList = mock(ArrayList.class);
    when(mockArrayList.size()).thenReturn(5);

    mockArrayList.add("Test1");
    mockArrayList.add("Test2");

    // when & then
    assertEquals(5, mockArrayList.size());
  }

  @Test
  void testWithSpy() {
    // given

    // @Spy -> Utilizado para criar um espião de um objeto real.
    // Casos de uso: quando queremos testar um objeto real

    // Obs: Utilizar em projetos novos não é uma boa prática
    // pois pode levar a testes frágeis e difíceis de manter.

    // Utilizar em projetos legados, quando nao é tao simples utilizar mocks

    List<String> spyArrayList = spy(ArrayList.class);
    spyArrayList.add("Test1");
    spyArrayList.add("Test2");

    // when & then
    assertEquals(2, spyArrayList.size());
  }

  @Test
  void testWithSpyAndStubbedMethod() {
    // given
    List<String> spyArrayList = spy(ArrayList.class);
    spyArrayList.add("Test1");
    spyArrayList.add("Test2");

    when(spyArrayList.size()).thenReturn(5);

    // when & then
    assertEquals(5, spyArrayList.size());
  }

  @Test
  void testWithSpyVerifyBehavior() {
    // given
    List<String> spyArrayList = spy(ArrayList.class);

    // when
    spyArrayList.add("Test1");
    spyArrayList.add("Test2");

    // then
    verify(spyArrayList, times(2)).add(anyString());
    verify(spyArrayList, never()).remove(anyString());
  }

}
