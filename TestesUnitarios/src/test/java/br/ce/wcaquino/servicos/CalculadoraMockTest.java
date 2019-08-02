package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {
	@Test
	public void mockTest() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		Mockito.when(calc.soma(Mockito.eq(1), Mockito.anyInt())).thenReturn(3);
		
		assertEquals(3, calc.soma(1, 5));
	}
}
