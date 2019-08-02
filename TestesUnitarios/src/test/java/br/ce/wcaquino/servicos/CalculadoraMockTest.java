package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void diferencaSpyMock() {
		//Quando não sabe o que fazer(Compormento Padrão) retorna o valor padrão da variável, no caso int = 0
		//Mockito.when(calcMock.soma(1, 3)).thenReturn(5);
		//Para que o Mock possa ter o mesmo comportamento do Spy de chamada do método
		Mockito.when(calcMock.soma(1, 3)).thenCallRealMethod();
		assertEquals(4, calcMock.soma(1, 3));
		
		//Quando não sabe o que fazer(Compormento Padrão) chama o método para buscar o retorno no caso 1+4 =5
		Mockito.when(calcSpy.soma(1, 3)).thenReturn(8);
		assertEquals(5, calcSpy.soma(1, 4));		
	}
	
	
	
	@Test
	public void mockTest() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);		
		
		Mockito.when(calc.soma(argCapt.capture(), Mockito.anyInt())).thenReturn(3);
		
		assertEquals(3, calc.soma(1, 5));
	}
}
