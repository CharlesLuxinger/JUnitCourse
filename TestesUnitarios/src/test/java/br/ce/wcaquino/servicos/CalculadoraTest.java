package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {
	private Calculadora calc;

	@Before
	public void setUp() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// Cenário
		int a = 5;
		int b = 3;
		int expected = a + b;

		// Ação
		int resultado = calc.soma(a, b);

		// Verificação
		assertEquals(expected, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		// Cenário
		int a = 5;
		int b = 3;
		int expected = a - b;

		// Ação
		int resultado = calc.subtrair(a, b);

		// Verificação
		assertEquals(expected, resultado);
	}

	@Test
	public void deveMultiplicarDoisValores() {
		// Cenário
		int a = 5;
		int b = 3;
		int expected = a * b;

		// Ação
		int resultado = calc.multiplica(a, b);

		// Verificação
		assertEquals(expected, resultado);
	}

	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// Cenário
		int a = 5;
		int b = 3;
		int expected = a / b;

		// Ação
		int resultado = calc.dividi(a, b);

		// Verificação
		assertEquals(expected, resultado);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancaExcessaoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// Cenário
		int a = 5;
		int b = 0;

		// Ação
		calc.dividi(a, b);
	}
}
