package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CalculadoraMockTest {
	@Test
	public void mockTest() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);		
		
		Mockito.when(calc.soma(argCapt.capture(), Mockito.anyInt())).thenReturn(3);
		
		assertEquals(3, calc.soma(1, 5));
	}
}
